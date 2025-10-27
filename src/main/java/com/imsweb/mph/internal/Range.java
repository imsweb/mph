/*
 * Copyright (C) 2025 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

public class Range {

    private final Integer minimum;

    private final Integer maximum;

    public Range(Integer minimum, Integer maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public static Range of(final Integer fromInclusive, final Integer toInclusive) {
        return new Range(fromInclusive, toInclusive);
    }

    public static Range is(final Integer value) {
        return new Range(value, value);
    }

    public boolean contains(final Integer element) {
        if (element == null)
            return false;

        return Integer.compare(element, minimum) > -1 && Integer.compare(element, maximum) < 1;
    }
}
