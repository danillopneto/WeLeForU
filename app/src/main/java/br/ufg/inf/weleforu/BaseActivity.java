package br.ufg.inf.weleforu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
