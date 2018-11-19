package com.ihavenodomain.everysecondvalute.ui.mainList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ihavenodomain.everysecondvalute.R;

public class CurrencyViewHolder  extends RecyclerView.ViewHolder {
    private TextView tvAbbreviation;
    private EditText etCurrency;

    private boolean isBase;

    private MyAdapter.IMultiplierChangeCallback callback;

    CurrencyViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false));

        etCurrency = itemView.findViewById(R.id.et_currency);
        tvAbbreviation = itemView.findViewById(R.id.tv_abbreviation);
    }

    public void setCallback(MyAdapter.IMultiplierChangeCallback callback) {
        this.callback = callback;
    }

    void setName(String text) {
        tvAbbreviation.setText(text);
    }

    void setValue(String text) {
        // if (!etCurrency.getText().toString().equals(text))
        etCurrency.setText(text);
        etCurrency.setSelection(text.length());
    }

    boolean isBase() {
        return isBase;
    }

    void setIsBase(boolean base) {
        isBase = base;
        if (isBase) {
            etCurrency.addTextChangedListener(watcher);
        } else {
            etCurrency.removeTextChangedListener(watcher);
        }
    }

    boolean isEtInFocus() {
        return etCurrency.isFocused();
        // getShowSoftInputOnFocus
    }



    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (callback != null) {
                callback.onMultiplierTextChanged(s.toString());
            }
        }
    };
}
