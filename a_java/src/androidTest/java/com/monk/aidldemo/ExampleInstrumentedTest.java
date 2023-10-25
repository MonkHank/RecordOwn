package com.monk.aidldemo;

import android.net.Uri;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

/**
 * @author monk
 * @since 2018-12-27
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.monk.aidldemo", appContext.getPackageName());
    }

    @Test
    public void testUriApi() {
        Uri uri1 = Uri.parse("http://www.baidu.com").buildUpon().path("favicom.ico").build();
        System.out.println("url.toString()= "+uri1.toString());
    }
}
