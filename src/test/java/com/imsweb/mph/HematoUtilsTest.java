/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import org.junit.Assert;
import org.junit.Test;

public class HematoUtilsTest {

    private final HematoDataProvider _provider = new DefaultHematoDataProvider();


    @Test
    public void testIsSamePrimary() {

        Assert.assertFalse(HematoUtils.isSamePrimary(_provider, null, null, 2010, 2010));
        Assert.assertFalse(HematoUtils.isSamePrimary(_provider, "", "", 2010, 2010));
        Assert.assertFalse(HematoUtils.isSamePrimary(_provider, "TEST", "TEST", 2010, 2010));
        Assert.assertTrue(HematoUtils.isSamePrimary(_provider, "9861/3", "9861/3", 2010, 2010));
        Assert.assertTrue(HematoUtils.isSamePrimary(_provider, "9861/3", "9861/3", 2010, 2000));

        Assert.assertTrue(HematoUtils.isSamePrimary(_provider, "9870/3", "9590/3", 2016, 2011));
        Assert.assertTrue(HematoUtils.isSamePrimary(_provider, "9590/3", "9870/3", 2009, 2016));
        Assert.assertFalse(HematoUtils.isSamePrimary(_provider, "9590/3", "9870/3", 1990, 1998));

        Assert.assertFalse(HematoUtils.isSamePrimary(_provider, "9870/3", "9805/3", 2016, 2016));
        Assert.assertFalse(HematoUtils.isSamePrimary(_provider, "9870/3", "9805/3", 2010, 2010));
        Assert.assertTrue(HematoUtils.isSamePrimary(_provider, "9870/3", "9805/3", 2001, 2001));
        Assert.assertFalse(HematoUtils.isSamePrimary(_provider, "9671/3", "9823/3", 2018, 2009));

        Assert.assertTrue(HematoUtils.isSamePrimary(_provider, "9738/3", "9590/3", 2019, 2022));
        Assert.assertFalse(HematoUtils.isSamePrimary(_provider, "9738/3", "9590/3", 2021, 2022));
    }

    @Test
    public void testIsChronicToAcute() {

        Assert.assertFalse(HematoUtils.isChronicToAcuteTransformation(_provider, null, null, 2010, 2010));
        Assert.assertFalse(HematoUtils.isChronicToAcuteTransformation(_provider, "", "", 2010, 2010));
        Assert.assertFalse(HematoUtils.isChronicToAcuteTransformation(_provider, "TEST", "TEST", 2010, 2010));
        Assert.assertTrue(HematoUtils.isChronicToAcuteTransformation(_provider, "9761/3", "9659/3", 2010, 2010));
        Assert.assertFalse(HematoUtils.isChronicToAcuteTransformation(_provider, "9659/3", "9761/3", 2010, 2010));
        Assert.assertTrue(HematoUtils.isChronicToAcuteTransformation(_provider, "9992/3", "9872/3", 2010, 2010));
        Assert.assertFalse(HematoUtils.isChronicToAcuteTransformation(_provider, "9992/3", "9872/3", 2005, 2010));
        Assert.assertTrue(HematoUtils.isChronicToAcuteTransformation(_provider, "9989/3", "9861/3", 2010, 2010));

        Assert.assertTrue(HematoUtils.isChronicToAcuteTransformation(_provider, "9993/3", "9867/3", 2021, 2021));
        Assert.assertFalse(HematoUtils.isChronicToAcuteTransformation(_provider, "9867/3", "9993/3", 2021, 2021));
        Assert.assertFalse(HematoUtils.isChronicToAcuteTransformation(_provider, "9993/3", "9867/3", 2020, 2021));
    }

    @Test
    public void testIsAcuteToChronic() {

        Assert.assertFalse(HematoUtils.isAcuteToChronicTransformation(_provider, null, null, 2010, 2010));
        Assert.assertFalse(HematoUtils.isAcuteToChronicTransformation(_provider, "", "", 2010, 2010));
        Assert.assertFalse(HematoUtils.isAcuteToChronicTransformation(_provider, "TEST", "TEST", 2010, 2010));
        Assert.assertTrue(HematoUtils.isAcuteToChronicTransformation(_provider, "9653/3", "9671/3", 2010, 2010));
        Assert.assertFalse(HematoUtils.isAcuteToChronicTransformation(_provider, "9671/3", "9653/3", 2010, 2010));

        Assert.assertFalse(HematoUtils.isAcuteToChronicTransformation(_provider, "9993/3", "9867/3", 2021, 2021));
        Assert.assertTrue(HematoUtils.isAcuteToChronicTransformation(_provider, "9867/3", "9993/3", 2021, 2021));
        Assert.assertFalse(HematoUtils.isAcuteToChronicTransformation(_provider, "9993/3", "9867/3", 2020, 2021));
    }
}
