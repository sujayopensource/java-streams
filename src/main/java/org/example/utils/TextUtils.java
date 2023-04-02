package org.example.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextUtils {

    public static String getListAsPrettyString(List<?> list) {
        if (list == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder result = new StringBuilder();
        boolean firstElement = true;
        for (final Object element : list) {
            assert element != null;
            if (firstElement) {
                result.append(StringUtils.capitalize(element.toString()));
                firstElement = false;
            } else {
                result.append(" | ".concat(StringUtils.capitalize(element.toString())));
            }
        }
        return result.toString();
    }

    public static String getListAsPrettyList(List<?> list, final int indents) {
        if (list == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder result = new StringBuilder();
        for (final Object element : list) {
            assert element != null;
            result.append(System.lineSeparator());
            result.append("\t".repeat(Math.max(0, indents)));
            result.append(element);
        }
        return result.toString();
    }

    public static String getSetAsPrettyString(Set<?> set) {
        if (set == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder result = new StringBuilder();
        boolean firstElement = true;
        for (final Object element : set) {
            assert element != null;
            if (firstElement) {
                result.append(StringUtils.capitalize(element.toString()));
                firstElement = false;
            } else {
                result.append(" | ".concat(StringUtils.capitalize(element.toString())));
            }
        }
        return result.toString();
    }

    public static String getMapAsPrettyString(Map<String, ?> map) {
        if (MapUtils.isEmpty(map)) {
            return StringUtils.EMPTY;
        }
        StringBuilder result = new StringBuilder();
        boolean firstElement = true;
        for (final Map.Entry<String, ?> element : map.entrySet()) {
            assert element != null;
            if (firstElement) {
                firstElement = false;
            } else {
                result.append(" | ");
            }
            result.append(StringUtils.capitalize(element.getKey()))
                    .append(" [" + element.getValue() + "]");
        }
        return result.toString();
    }
}
