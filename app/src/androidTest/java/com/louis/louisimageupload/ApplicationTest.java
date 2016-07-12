package com.louis.louisimageupload;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.louis.okhttp.OkhttpTest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        try {
            new OkhttpTest().doGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}