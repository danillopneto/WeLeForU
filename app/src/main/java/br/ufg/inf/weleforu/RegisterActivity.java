package br.ufg.inf.weleforu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufg.inf.weleforu.model.ResultOfExecution;
import br.ufg.inf.weleforu.model.LoginManager;

public class RegisterActivity extends BaseActivity {
    EditText email;
    EditText password;
    EditText checkPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkPassword = findViewById(R.id.checkPassword);
    }

    public void register(View v) {
        if (areRequiredFilled(email, password, checkPassword)) {
            if (password.getText().toString().equals(checkPassword.getText().toString())) {
                ResultOfExecution result = LoginManager.addValidAccount(
                        email.getText().toString(), password.getText().toString());
                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.isSuccess()) {
                    finish();
                }
            } else {
                checkPassword.setError(getString(R.string.passwords_doesnt_match));
            }
        }
    }
}
