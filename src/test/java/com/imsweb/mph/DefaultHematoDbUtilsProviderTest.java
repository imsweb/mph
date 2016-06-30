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

        Assert.assertFalse(_provider.isSamePrimary(null, null, 2000));
        Assert.assertFalse(_provider.isSamePrimary("", "", 2000));
        Assert.assertFalse(_provider.isSamePrimary("TEST", "TEST", 2000));
        Assert.assertTrue(_provider.isSamePrimary("9861/3", "9861/3", 2000));

        Assert.assertTrue(_provider.isSamePrimary("9870/3", "9590/3", 2016));
        Assert.assertTrue(_provider.isSamePrimary("9590/3", "9870/3", 2016));
        Assert.assertFalse(_provider.isSamePrimary("9590/3", "9870/3", 1990));

        Assert.assertFalse(_provider.isSamePrimary("9870/3", "9805/3", 2016));
        Assert.assertFalse(_provider.isSamePrimary("9870/3", "9805/3", 2010));
        Assert.assertTrue(_provider.isSamePrimary("9870/3", "9805/3", 2001));
    }

    @Test
    public void testIsAcuteTransformation() {
        Assert.assertFalse(_provider.isAcuteTransformation(null, null, 2010));
        Assert.assertFalse(_provider.isAcuteTransformation("", "", 2010));
        Assert.assertFalse(_provider.isAcuteTransformation("TEST", "TEST", 2010));
        Assert.assertTrue(_provider.isAcuteTransformation("9963/3", "9861/3", 2010));
        Assert.assertFalse(_provider.isAcuteTransformation("9963/3", "9861/3", 2000));
        Assert.assertFalse(_provider.isAcuteTransformation("9963/3", "9861/3", 2005));
        Assert.assertFalse(_provider.isAcuteTransformation("9861/3", "9963/3", 2010));

        Assert.assertTrue(_provider.isAcuteTransformation("9671/3", "9651/3", 2015));
        Assert.assertTrue(_provider.isAcuteTransformation("9671/3", "9832/3", 2016));
        Assert.assertFalse(_provider.isAcuteTransformation("9671/3", "9651/3", 2002));
        Assert.assertTrue(_provider.isAcuteTransformation("9671/3", "9832/3", 2002));
        Assert.assertFalse(_provider.isAcuteTransformation("9671/3", "9651/3", 2000));
        Assert.assertFalse(_provider.isAcuteTransformation("9671/3", "9832/3", 2000));
        Assert.assertFalse(_provider.isAcuteTransformation("9651/3", "9671/3", 2010));
        Assert.assertFalse(_provider.isAcuteTransformation("9861/3", "9832/3", 2010));
    }

    @Test
    public void testIsChronicTransformation() {
        Assert.assertFalse(_provider.isChronicTransformation(null, null, 2010));
        Assert.assertFalse(_provider.isChronicTransformation("", "", 2010));
        Assert.assertFalse(_provider.isChronicTransformation("TEST", "TEST", 2010));
        Assert.assertTrue(_provider.isChronicTransformation("9652/3", "9671/3", 2010));
        Assert.assertFalse(_provider.isChronicTransformation("9652/3", "9671/3", 2000));
        Assert.assertFalse(_provider.isChronicTransformation("9652/3", "9671/3", 2005));
        Assert.assertFalse(_provider.isChronicTransformation("9671/3", "9652/3", 2010));

        Assert.assertTrue(_provider.isChronicTransformation("9671/3", "9675/3", 2015));
        Assert.assertTrue(_provider.isChronicTransformation("9671/3", "9675/3", 2002));
        Assert.assertFalse(_provider.isChronicTransformation("9671/3", "9675/3", 2000));
        Assert.assertFalse(_provider.isChronicTransformation("9675/3", "9671/3", 2000));
        Assert.assertFalse(_provider.isChronicTransformation("9675/3", "9671/3", 2010));
        Assert.assertFalse(_provider.isChronicTransformation("9675/3", "9671/3", 2001));
    }
}
