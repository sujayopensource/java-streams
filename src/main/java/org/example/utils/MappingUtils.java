package org.example.utils;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Function;

public class MappingUtils {

    public static <T, E> Function<Map.Entry<T, E>, Pair<T, E>> mapEntryToPair() {
        return stringTEntry -> Pair.of(stringTEntry.getKey(), stringTEntry.getValue());
    }
}
