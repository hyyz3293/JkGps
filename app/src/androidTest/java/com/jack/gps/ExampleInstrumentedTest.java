package com.jack.gps;

import android.content.Context;
import android.net.Uri;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //assertEquals("com.jack.gps", appContext.getPackageName());

        String str = "http://www.baidu.com:8080/wenku/jiatiao.html?id=123456&name=jack";
        Uri url = Uri.parse(str);
        System.out.println(url.getScheme());
        System.out.println(url.getHost());
        System.out.println(url.getPort());
        System.out.println(url.getPath());
        System.out.println(url.getQuery());
        System.out.println(url.getQuery());
    }
}
