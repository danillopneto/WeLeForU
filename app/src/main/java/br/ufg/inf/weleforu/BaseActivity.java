package br.ufg.inf.weleforu;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class BaseActivity extends AppCompatActivity {
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showDialog(){
        pd = new ProgressDialog(this);
        pd.setTitle(R.string.logginin);
        pd.show();
    }

    protected void showLongTimeMessage(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_LONG).show();
    }

    protected void hideDialog(){
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    protected void hideKeyboard(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    protected boolean areRequiredFilled(EditText... editTexts){
        boolean focusDefined = false;
        boolean filled = true;
        for (EditText editText : editTexts) {
            if (editText.getText().toString().equals("")) {
                editText.setError(getString(R.string.required_field));
                if (!focusDefined) {
                    editText.requestFocus();
                    focusDefined = true;
                }

                filled = false;
            }
        }

        return filled;
    }
}
