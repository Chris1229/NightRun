package com.quncao.http;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.android.volley.error.VolleyError;
import com.quncao.core.http.HttpRequestManager;
import com.quncao.core.http.AbsHttpRequestProxy;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */

public class MyTest  extends AndroidTestCase {



    @LargeTest
    public void testHttp(){
        HttpRequestManager.getInstance().init(getContext());
        AbsHttpRequestProxy proxy = new AbsHttpRequestProxy(new HttpGetDialogConfigRequest(), new AbsHttpRequestProxy.RequestListener<DialogBean>() {
            @Override
            public void onSuccess(DialogBean response) {
                List homtpageList = response.getHomepage();
                assertNotNull(homtpageList);
            }

            @Override
            public void onFailed(VolleyError error) {

            }
        });
        proxy.excute();
        proxy.cancel();
        HttpRequestManager.getInstance().cancelAll(tag);
    }
}
