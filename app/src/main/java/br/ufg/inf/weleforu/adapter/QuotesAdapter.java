package br.ufg.inf.weleforu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.weleforu.R;
import br.ufg.inf.weleforu.model.Quote;

/**
 * Created by danil on 1/27/2018.
 */

public class QuotesAdapter extends RecyclerView.Adapter<QuotesViewHolder> {
    private final Context context;
    private List<Quote> quotesList;

    public QuotesAdapter(Context context) {
        this.context = context;
        quotesList = new ArrayList<>();
    }

    public void setQuotesList(List<Quote> contactsList) {
        this.quotesList = contactsList;
    }

    @Override
    public QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_quote, parent, false);

        QuotesViewHolder viewHolder = new QuotesViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuotesViewHolder viewHolder, int position) {
        Quote quote = quotesList.get(position);

        TextView dateTextView = viewHolder.dateTextView;
        dateTextView.setText(quote.getDate());
        TextView quoteTextView = viewHolder.quoteTextView;
        quoteTextView.setText(quote.getQuote());
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }
}
