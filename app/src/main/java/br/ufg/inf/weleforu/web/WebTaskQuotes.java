package br.ufg.inf.weleforu.web;

/**
 * Created by danil on 1/27/2018.
 */

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufg.inf.weleforu.model.Quote;
import br.ufg.inf.weleforu.web.WebTaskBase;

public class WebTaskQuotes extends WebTaskBase {

    private static final String SERVICE_URL = "quotes/";
    private String token;
    private Quote quote;

    public WebTaskQuotes(Context context, String token) {
        super(context, SERVICE_URL + token);
        this.token = token;
    }

    @Override
    void handleResponse(String response) {
        Type listType = new TypeToken<ArrayList<Quote>>(){}.getType();
        List<Quote> quotes = new Gson().fromJson(response, listType);
        EventBus.getDefault().post(quotes);
    }

    @Override
    public String getRequestBody() {
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put("", token);

        JSONObject json = new JSONObject(requestMap);
        String jsonString = json.toString();

        return  jsonString;
    }
}