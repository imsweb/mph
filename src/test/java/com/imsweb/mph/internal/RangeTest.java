/*
 * Copyright (C) 2025 Information Management Services, Inc.
 */
package com.imsweb.mph.internal;

import org.junit.Assert;
import org.junit.Test;

public class RangeTest {

    @Test
    public void testRange() {
        Range range = Range.of(1, 3);
        Assert.assertFalse(range.contains(0));
        Assert.assertTrue(range.contains(1));
        Assert.assertTrue(range.contains(2));
        Assert.assertTrue(range.contains(3));
        Assert.assertFalse(range.contains(5));

        Assert.assertFalse(range.contains(null));

        range = Range.is(2);
        Assert.assertFalse(range.contains(1));
        Assert.assertTrue(range.contains(2));
        Assert.assertFalse(range.contains(3));
    }
    
}
