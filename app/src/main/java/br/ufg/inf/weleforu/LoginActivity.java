package br.ufg.inf.weleforu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.ufg.inf.weleforu.model.LoginManager;
import br.ufg.inf.weleforu.model.ResultOfExecution;

public class LoginActivity extends BaseActivity {
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void clear(View v) {
        email.setText("");
        password.setText("");
    }

    public void login(View v) {
        if (areRequiredFilled(email, password)){
            ResultOfExecution result = LoginManager.accountIsValid(email.getText().toString(), password.getText().toString());
            if (!result.isSuccess()) {
                Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(getString(R.string.user_id), email.getText().toString());
                startActivity(intent);
            }
        }
    }

    public void forgotPassword(View v) {
        if (areRequiredFilled(email)) {
            if (!LoginManager.loginIsValid(email.getText().toString())) {
                email.setError(getString(R.string.invalid_login));
            } else {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                intent.putExtra(getString(R.string.login), email.getText().toString());
                startActivity(intent);
            }
        }
    }

    public void register(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
