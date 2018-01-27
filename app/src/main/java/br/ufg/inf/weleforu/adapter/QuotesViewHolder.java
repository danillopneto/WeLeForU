package br.ufg.inf.weleforu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.ufg.inf.weleforu.R;

/**
 * Created by danil on 1/27/2018.
 */

public class QuotesViewHolder  extends RecyclerView.ViewHolder {
    public TextView dateTextView;
    public TextView quoteTextView;

    public QuotesViewHolder(View itemView) {
        super(itemView);

        dateTextView = itemView.findViewById(R.id.label_quote_date);
        quoteTextView = itemView.findViewById(R.id.label_quote_message);
    }
}
