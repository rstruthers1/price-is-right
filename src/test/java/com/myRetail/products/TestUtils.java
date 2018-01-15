package com.myRetail.products;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class TestUtils {
    public static String readResourceAsString(String fileName) {
        URL resourceUrl = Resources.getResource(fileName);
        File myFile = null;
        try {
            myFile = new File(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String content = null;
        try {
            content = FileUtils.readFileToString(myFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String productJsonWithTitle(String title) {
        String jsonTemplate = TestUtils.readResourceAsString("product-example.json");
        return String.format(jsonTemplate, title);
    }
}
