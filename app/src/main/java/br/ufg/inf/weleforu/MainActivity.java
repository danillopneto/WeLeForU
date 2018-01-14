package br.ufg.inf.weleforu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String email = getIntent().getStringExtra(getString(R.string.user_id));
        TextView user = findViewById(R.id.user);
        user.setText(email);
    }
}
