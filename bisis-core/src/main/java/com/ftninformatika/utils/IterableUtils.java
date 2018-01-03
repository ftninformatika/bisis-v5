package com.ftninformatika.utils;

import java.util.Collection;

/**
 * Created by Petar on 1/3/2018.
 */
public class IterableUtils {

    public static int size(Iterable<?> data) {
        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;
        for (Object i : data) {
            counter++;
        }
        return counter;
    }
}
