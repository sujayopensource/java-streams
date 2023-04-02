package org.example.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Objects;

public class TextUtils {

    public static String getCollectionAsPrettyString(final Collection<?> collection) {
        return buildCollectionString(collection).toString();
    }

    public static String getCollectionAsPrettyList(final Collection<?> collection, final int indents) {
        return buildCollectionList(collection, indents).toString();
    }

    private static StringBuilder buildCollectionString(final Collection<?> collection) {
        StringBuilder result = new StringBuilder();
        if (CollectionUtils.isEmpty(collection)) {
            return result;
        }
        boolean firstElement = true;
        for (final Object element : collection) {
            if (Objects.isNull(element)) {
                continue;
            }
            addElement(result, element, firstElement);
            firstElement = false;
        }
        return result;
    }

    private static void addElement(final StringBuilder stringBuilder, final Object element, final boolean firstElement) {
        if (BooleanUtils.isFalse(firstElement)) {
            stringBuilder.append(" | ");
        }
        if (element instanceof Pair<?, ?> pair) {
            stringBuilder.append(StringUtils.capitalize(pair.toString("%s [%s]")));
        } else {
            stringBuilder.append(StringUtils.capitalize(element.toString()));
        }
    }

    private static StringBuilder buildCollectionList(final Collection<?> collection, final int indents) {
        StringBuilder result = new StringBuilder();
        if (CollectionUtils.isEmpty(collection)) {
            return result;
        }
        for (final Object element : collection) {
            if (Objects.isNull(element)) {
                continue;
            }
            result.append(System.lineSeparator());
            result.append("\t".repeat(Math.max(0, indents)));
            result.append(element);
        }
        return result;
    }
}
