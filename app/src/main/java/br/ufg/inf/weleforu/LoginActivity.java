package br.ufg.inf.weleforu;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import br.ufg.inf.weleforu.br.ufg.inf.auth.FingerPrintActivity;
import br.ufg.inf.weleforu.br.ufg.inf.auth.FingerprintHandler;
import br.ufg.inf.weleforu.br.ufg.inf.web.WebTaskLogin;
import br.ufg.inf.weleforu.model.LoginManager;

public class LoginActivity extends FingerPrintActivity {
    EditText email;
    EditText password;
    private TextView textView;
    private View viewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        viewLogin = findViewById(R.id.containerLogin);
    }

    @Override
    protected void onResume() {
        prepareFingerprintAuth();
        super.onResume();
    }

    public void clear(View v) {
        email.setText("");
        password.setText("");
    }

    public void forgotPassword(View v) {
        if (areRequiredFilled(email)) {
            if (!LoginManager.loginIsValid(email.getText().toString())) {
                showLongTimeMessage(v, getString(R.string.invalid_login));
            } else {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                intent.putExtra(getString(R.string.login), email.getText().toString());
                startActivity(intent);
            }
        }
    }

    public void login(View v) {
        hideKeyboard(v);
        if (areRequiredFilled(email, password)){
            showDialog();

            WebTaskLogin taskLogin = new WebTaskLogin(this, email.getText().toString(), password.getText().toString());
            taskLogin.execute();
        }
    }

    public void register(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void prepareFingerprintAuth() {
        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Get an instance of KeyguardManager and FingerprintManager//
            keyguardManager =
                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            textView = findViewById(R.id.fingerprint_label);

            //Check whether the device has a fingerprint sensor//
            if (!fingerprintManager.isHardwareDetected()) {
                // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                textView.setText(getString(R.string.fingerprint_not_supported));
            }
            //Check whether the user has granted your app the USE_FINGERPRINT permission//
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // If your app doesn't have this permission, then display the following text//
                textView.setText(getString(R.string.turn_fingerprint_on));
            }

            //Check that the user has registered at least one fingerprint//
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                // If the user hasn’t configured any fingerprints, then display the following message//
                textView.setText(getString(R.string.fingerprint_not_set));
            }

            //Check that the lockscreen is secured//
            if (!keyguardManager.isKeyguardSecure()) {
                // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
                textView.setText(getString(R.string.fingerprint_security));
            } else {
                try {
                    generateKey();
                } catch (FingerprintException e) {
                    e.printStackTrace();
                }

                if (initCipher()) {
                    //If the cipher is initialized successfully, then create a CryptoObject instance//
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);

                    // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
                    // for starting the authentication process (via the startAuth method) and processing the authentication process events//
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }
            }
        }
    }
}
