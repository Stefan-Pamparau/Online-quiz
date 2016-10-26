package com.iquest.service.util;

import java.util.ArrayList;
import java.util.List;

public class ServiceUtil {

    public static <E> List<E> convertFromIterableToList(Iterable<E> source) {
        List<E> result = new ArrayList<E>();
        source.forEach(result::add);
        return result;
    }
}
