/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.junit.Assert;
import org.junit.Test;

public class DefaultHematoDbUtilsProviderTest {

    private DefaultHematoDbUtilsProvider _provider = new DefaultHematoDbUtilsProvider();

    @Test
    public void testIsSamePrimary() {

        Assert.assertFalse(_provider.isSamePrimary(null, null, 2010, 2010));
        Assert.assertFalse(_provider.isSamePrimary("", "", 2010, 2010));
        Assert.assertFalse(_provider.isSamePrimary("TEST", "TEST", 2010, 2010));
        Assert.assertTrue(_provider.isSamePrimary("9861/3", "9861/3", 2000, 2010));
        Assert.assertTrue(_provider.isSamePrimary("9861/3", "9861/3", 2010, 2000));

        Assert.assertTrue(_provider.isSamePrimary("9870/3", "9590/3", 2016, 2011));
        Assert.assertTrue(_provider.isSamePrimary("9590/3", "9870/3", 2009, 2016));
        Assert.assertFalse(_provider.isSamePrimary("9590/3", "9870/3", 1990, 1998));

        Assert.assertFalse(_provider.isSamePrimary("9870/3", "9805/3", 2016, 2016));
        Assert.assertFalse(_provider.isSamePrimary("9870/3", "9805/3", 2010, 2010));
        Assert.assertTrue(_provider.isSamePrimary("9870/3", "9805/3", 2001, 2001));
        Assert.assertFalse(_provider.isSamePrimary("9671/3", "9823/3", 2018, 2009));
    }

    @Test
    public void testCanTransformTo() {
        Assert.assertFalse(_provider.canTransformTo(null, null, 2010, 2010));
        Assert.assertFalse(_provider.canTransformTo("", "", 2010, 2010));
        Assert.assertFalse(_provider.canTransformTo("TEST", "TEST", 2010, 2010));
        Assert.assertTrue(_provider.canTransformTo("9761/3", "9659/3", 2005, 2010));
        Assert.assertFalse(_provider.canTransformTo("9659/3", "9761/3", 2005, 2010));
        Assert.assertTrue(_provider.canTransformTo("9992/3", "9872/3", 2005, 2010));
        Assert.assertTrue(_provider.canTransformTo("9992/3", "9872/3", 2010, 2005));

    }
}
