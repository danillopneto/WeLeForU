package br.ufg.inf.weleforu.model;

import android.content.Context;
import android.content.SharedPreferences;

import br.ufg.inf.weleforu.R;

/**
 * Created by aluno on 26/01/18.
 */

public class SessionHandler {
    private final String GROUP_USER = "GROUP_USER";
    private final String USER_NAME = "USER_NAME";
    private final String USER_EMAIL = "USER_EMAIL";
    private final String USER_TOKEN = "USER_TOKEN";
    private final String USER_PHOTO_URL = "USER_PHOTO_URL";

    public User getUser(Context context) {
        User user = new User();
        SharedPreferences sharedPreferences = getSharedPreference(context);
        String username = sharedPreferences.getString(USER_NAME, "");
        String email = sharedPreferences.getString(USER_EMAIL, "");
        String token = sharedPreferences.getString(USER_TOKEN, "");
        String photoUrl = sharedPreferences.getString(USER_PHOTO_URL, "");

        if( email.equals("") || token.equals("") ){
            throw new RuntimeException(context.getString(R.string.error_no_user));
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setToken(token);
        user.setPhotoUrl(photoUrl);

        return user;
    }

    public void removeSession(Context context) {
        getSharedPreference(context).edit().clear().commit();
    }

    public void saveSession(Context context, User user) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(USER_NAME, user.getUsername());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.putString(USER_TOKEN, user.getToken());
        editor.putString(USER_PHOTO_URL, user.getPhotoUrl());
        editor.commit();
    }

    private SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(GROUP_USER, context.MODE_PRIVATE);
    }
}
