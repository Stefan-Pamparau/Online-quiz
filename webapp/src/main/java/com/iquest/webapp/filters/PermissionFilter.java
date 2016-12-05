package com.iquest.webapp.filters;

import com.iquest.model.user.Client;
import com.iquest.model.user.User;
import com.iquest.model.user.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter which checks a current users permissions.
 *
 * @author Stefan Pamparau
 */
public class PermissionFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(PermissionFilter.class);
    private static final String LOGGED_USER_KEY = "loggedUser";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession(false);
        String path = removeMatrixVariables(servletRequest.getRequestURI().substring(servletRequest.getContextPath().length()));

        boolean loggedIn = session != null && session.getAttribute(LOGGED_USER_KEY) != null;
        boolean loginRequest = path.equals("/login");
        boolean registerRequest = path.equals("/register");

        if (registerRequest || loginRequest) {
            logger.info("Request is of register or login type");
            chain.doFilter(request, response);
        } else if (loggedIn) {
            if (path.contains("client") || path.contains("admin")) {
                logger.info("Request requires admin privileges");
                User user = (User) session.getAttribute(LOGGED_USER_KEY);
                if (user.getUserType() != UserType.ADMIN) {
                    logger.info("Logged user does not have necessary permissions");
                    servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    logger.info("Logger user has necessary permissions");
                    Client client = (Client) user;
                    if (client.getConfirmed()) {
                        chain.doFilter(request, response);
                    } else {
                        logger.info("Client is not confirmed");
                    }
                }
            } else {
                logger.info("Request does not require admin privileges");
                chain.doFilter(request, response);
            }
        } else {
            servletResponse.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * Removes matrix variables from the path.
     *
     * @param path - path to be processed
     * @return - a path without matrix variables
     */
    private String removeMatrixVariables(String path) {
        logger.debug("Removing matrix variables from the request path");
        if (path.contains(";")) {
            return path.substring(0, path.indexOf(";"));
        }
        return path;
    }
}
