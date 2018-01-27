package br.ufg.inf.weleforu.br.ufg.inf.web;

/**
 * Created by danil on 1/27/2018.
 */

import android.content.Context;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import br.ufg.inf.weleforu.R;
import br.ufg.inf.weleforu.model.User;

public class WebTaskLogin extends WebTaskBase {

    private static final String SERVICE_URL = "login";
    private String email;
    private String password;

    private final String FIELD_USERNAME = "username";
    private final String FIELD_PASSWORD = "password";

    public WebTaskLogin(Context context, String email, String password) {
        super(context, SERVICE_URL);
        this.email = email;
        this.password = password;
    }

    @Override
    void handleResponse(String response) {
        try {
            String username = new JSONObject(response).getString("username");
            String token = new JSONObject(response).getString("token");
            String photoUrl = new JSONObject(response).getString("photoUrl");

            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setToken(token);
            user.setPhotoUrl(photoUrl);

            EventBus.getDefault().post(user);
        } catch (JSONException e) {
            EventBus.getDefault().post(new Error(getContext().getString(R.string.error_field_email)));
        }
    }

    @Override
    public String getRequestBody() {
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put(FIELD_USERNAME, email);
        requestMap.put(FIELD_PASSWORD, password);

        JSONObject json = new JSONObject(requestMap);
        String jsonString = json.toString();

        return  jsonString;
    }
}