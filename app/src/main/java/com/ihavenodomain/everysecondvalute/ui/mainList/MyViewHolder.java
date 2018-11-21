package com.ihavenodomain.everysecondvalute.ui.mainList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ihavenodomain.everysecondvalute.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView tvAbbreviation;
    public EditText etCurrency;

    MyViewHolder(@NonNull ViewGroup parent, MyAdapter.ItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false));
        initView();
        itemView.setOnClickListener(v -> clickListener.onItemClick(getAdapterPosition()));
    }

    public MyViewHolder(@NonNull ViewGroup parent, TextWatcher watcher) {
        super(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false));
        initView();
        etCurrency.addTextChangedListener(watcher);
    }

    private void initView() {
        etCurrency = itemView.findViewById(R.id.et_currency);
        tvAbbreviation = itemView.findViewById(R.id.tv_abbreviation);
    }

    void setName(String text) {
        tvAbbreviation.setText(text);
    }

    void setValue(String text) {
        etCurrency.setText(text);
        etCurrency.setSelection(text.length());
    }
}
