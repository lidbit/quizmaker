package com.adouglas.quizmaker;
import com.loopj.android.http.*;
import java.util.Map;

/**
 * Created by andrei on 05-Nov-16.
 */

public class QuizmakerRestClient {
    private static final String BASE_URL = "http://192.168.2.8/NancyAPI/";

    private static String _token = "";

    public static void setToken(String token)
    {
        _token = token;
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Authorization","Token " + _token);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void Auth(String url, Map<String, String> params, AsyncHttpResponseHandler handler) {

        RequestParams requestParams = new RequestParams();
        client.addHeader("User-Agent", "Nancy Browser");
        client.addHeader("Accept", "application/json");
        requestParams.put("UserName", params.get("UserName"));
        requestParams.put("Password", params.get("Password"));

        client.post(getAbsoluteUrl(url), requestParams, handler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Authorization","Token " + _token);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static boolean isTokenValid() {
        if(!_token.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}
