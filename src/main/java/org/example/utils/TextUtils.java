package org.example.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

public class TextUtils {

    @SuppressWarnings("unchecked")
    public static String getListAsPrettyString(List<?> list) {
        if (list == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder result = new StringBuilder();
        boolean firstElement = true;
        for (Object element : list) {
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

    @SuppressWarnings("unchecked")
    public static String getListAsPrettyList(List<?> list, final int indents) {
        if (list == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder result = new StringBuilder();
        for (Object element : list) {
            assert element != null;
            result.append(System.lineSeparator());
            for (int i = 0; i < indents; i++) {
                result.append("\t");
            }
            result.append(element);
        }
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public static String getSetAsPrettyString(Set<?> set) {
        if (set == null) {
            return StringUtils.EMPTY;
        }
        StringBuilder result = new StringBuilder();
        boolean firstElement = true;
        for (Object element : set) {
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
}
