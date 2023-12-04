/*
 * Copyright (C) 2023 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.imsweb.seerapi.client.SeerApi;

public class HematoDataTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    public void testDataLastUpdated() throws IOException {
        SeerApi api = new SeerApi.Builder().connect();

        Date apiDate = api.disease().versions().execute().body().stream().filter(v -> "latest".equals(v.getName())).collect(Collectors.toList()).get(0).getLastModified();
        Date localDate = MphUtils.getInstance().getHematoDataLastUpdated();

        if (apiDate.after(localDate))
            Assert.fail("\n\nThe disease data included in this library is older than what is currently available on SEER*API:\n\n"
                    + "Last updated in the library:  " + localDate + "\nLast updated in SEER*API:     " + apiDate + "\n\n"
                    + "Please run the HematoDataLab class to update the embedded data.\n");
    }

}
