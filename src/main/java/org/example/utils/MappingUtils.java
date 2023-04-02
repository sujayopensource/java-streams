package org.example.utils;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Function;

public class MappingUtils {

    public static <T> Function<Map.Entry<String, T>, Pair<String, T>> mapEntryToPair() {
        return stringTEntry -> Pair.of(stringTEntry.getKey(), stringTEntry.getValue());
    }

    public static <E extends Enum, T> Function<Map.Entry<E, T>, Pair<E, T>> mapEnumToPair() {
        return stringTEntry -> Pair.of(stringTEntry.getKey(), stringTEntry.getValue());
    }
}
