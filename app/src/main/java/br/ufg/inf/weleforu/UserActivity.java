package br.ufg.inf.weleforu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.ufg.inf.weleforu.adapter.QuotesAdapter;
import br.ufg.inf.weleforu.web.WebTaskQuotes;
import br.ufg.inf.weleforu.model.Quote;
import br.ufg.inf.weleforu.model.SessionHandler;
import br.ufg.inf.weleforu.model.User;

public class UserActivity extends BaseActivity {
    private User user;
    RecyclerView rvQuotes;
    QuotesAdapter adapter;

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
            rvQuotes = findViewById(R.id.quotes_listview);
            adapter = new QuotesAdapter(this);
            rvQuotes.setAdapter(adapter);
            rvQuotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
        adapter.setQuotesList(quotes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        new SessionHandler().removeSession(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
