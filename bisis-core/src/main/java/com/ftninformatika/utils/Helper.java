package com.ftninformatika.utils;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author badf00d21  15.8.19.
 */
public class Helper {

    public static <T> Optional<T> resolve(Supplier<T> resolver) {
        try {
            T result = resolver.get();
            return Optional.ofNullable(result);
        }
        catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
