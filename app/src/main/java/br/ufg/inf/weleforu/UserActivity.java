package br.ufg.inf.weleforu;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.weleforu.br.ufg.inf.web.WebTaskQuotes;
import br.ufg.inf.weleforu.model.Quote;
import br.ufg.inf.weleforu.model.SessionHandler;
import br.ufg.inf.weleforu.model.User;

public class UserActivity extends BaseActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    @Override
    protected void onStart(){
        super.onStart();

        SessionHandler sh = new SessionHandler();
        try {
            user = sh.getUser(this);
            getSupportActionBar().setTitle(user.getUsername());
            ImageView imageView =   findViewById(R.id.imageview_user);
            Picasso.with(this).load(user.getPhotoUrl()).into(imageView);
            EventBus.getDefault().register(this);
            WebTaskQuotes webtask = new WebTaskQuotes(this, user.getToken());
            webtask.execute();
        } catch (RuntimeException ex) {
            sh.removeSession(this);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onQuotes(List<Quote> quotes) {
        ListView listView = findViewById(R.id.quotes_listview);
        ArrayList<String> mensagens = new ArrayList<String>();
        for (Quote quote : quotes) {
            mensagens.add(quote.getQuote());
        }

        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_list_item_1, mensagens);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    public void logout(View v) {
        new SessionHandler().removeSession(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
