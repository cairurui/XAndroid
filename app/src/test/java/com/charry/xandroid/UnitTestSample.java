package com.charry.xandroid;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import android.content.Context;
import android.content.SharedPreferences;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestSample {

    private static final String FAKE_STRING = "HELLO WORLD";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {

//        String appname = mMockContext. getResources(). getString(R.string.app_name);
//        System.out.println(""+appname);
//        Assert.assertNotNull(appname);


        // Given a mocked Context injected into the object under test...
       String stringOngoingStubbing = String.valueOf(when(mMockContext.getString(R.string.app_name))
                .thenReturn(FAKE_STRING));

        System.out.println(stringOngoingStubbing);


//        ClassUnderTest myObjectUnderTest = new ClassUnderTest(mMockContext);
//
//        // ...when the string is returned from the object under test...
//        String result = myObjectUnderTest.getHelloWorldString();
//
//        // ...then the result should be the expected one.
//        assertThat(result, is(FAKE_STRING));
    }
}