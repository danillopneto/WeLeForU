package br.ufg.inf.weleforu.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import br.ufg.inf.weleforu.App;
import br.ufg.inf.weleforu.R;

/**
 * Created by danil on 1/14/2018.
 */

public final class LoginManager {

    private static HashMap<String, String> _validAccounts;

    public static boolean loginIsValid(String email) {
        return !email.equals("")
                && getValidAccounts().get(email) != null;
    }

    public static ResultOfExecution accountIsValid(String email, String password) {
        boolean success = false;
        String message;
        if (email.equals("")) {
            message = App.getLocalMessage(R.string.empty_email);
        } else if (password.equals("")) {
            message = App.getLocalMessage(R.string.empty_password);
        } else if (!getValidAccounts().containsKey(email)) {
            message = App.getLocalMessage(R.string.invalid_account);
        } else if (!getValidAccounts().get(email).equals(password)) {
            message = App.getLocalMessage(R.string.invalid_account);
        } else {
            success = true;
            message = App.getLocalMessage(R.string.valid_login);
        }

        return new ResultOfExecution(success, message);
    }

    public static ResultOfExecution addValidAccount(String email, String password) {
        if (getValidAccounts().containsKey(email)) {
            return new ResultOfExecution(false, App.getLocalMessage(R.string.account_already_exists));
        }

        getValidAccounts().put(email, password);
        return new ResultOfExecution(true, App.getLocalMessage(R.string.account_created));
    }

    private static HashMap<String, String> getValidAccounts() {
        if (_validAccounts == null) {
            _validAccounts = new HashMap<>();
            _validAccounts.put("teste", "teste");
            _validAccounts.put("danillopneto", "1234");
            _validAccounts.put("danillopneto@outlook.com", "1234");
        }

        return _validAccounts;
    }

    public static ResultOfExecution updateValidAccount(String email, String password) {
        if (loginIsValid(email)) {
            getValidAccounts().put(email, password);
            return new ResultOfExecution(true, App.getLocalMessage(R.string.password_updated));
        } else{
            return new ResultOfExecution(false, App.getLocalMessage(R.string.invalid_login));
        }
    }
}
