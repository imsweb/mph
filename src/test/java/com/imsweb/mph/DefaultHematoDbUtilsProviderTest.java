/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.junit.Assert;
import org.junit.Test;

public class DefaultHematoDbUtilsProviderTest {

    @Test
    public void testIsSamePrimary() {
        DefaultHematoDbUtilsProvider provider = DefaultHematoDbUtilsProvider.getInstance();
        Assert.assertFalse(provider.isSamePrimary(null, null, 2000));
        Assert.assertFalse(provider.isSamePrimary("", "", 2000));
        Assert.assertFalse(provider.isSamePrimary("TEST", "TEST", 2000));
        Assert.assertTrue(provider.isSamePrimary("9861/3", "9861/3", 2000));

        Assert.assertTrue(provider.isSamePrimary("9870/3", "9590/3", 2016));
        Assert.assertTrue(provider.isSamePrimary("9590/3", "9870/3", 2016));
        Assert.assertFalse(provider.isSamePrimary("9590/3", "9870/3", 1990));

        Assert.assertFalse(provider.isSamePrimary("9870/3", "9805/3", 2016));
        Assert.assertFalse(provider.isSamePrimary("9870/3", "9805/3", 2010));
        Assert.assertTrue(provider.isSamePrimary("9870/3", "9805/3", 2001));
    }
}
