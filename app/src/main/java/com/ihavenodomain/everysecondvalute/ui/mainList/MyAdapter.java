package com.ihavenodomain.everysecondvalute.ui.mainList;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ihavenodomain.everysecondvalute.model.CurrencyInfo;
import com.ihavenodomain.everysecondvalute.model.CurrencyRate;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {
    private boolean onBind;

    private CurrencyInfo info = new CurrencyInfo();
    private List<CurrencyRate> items = new ArrayList<>();

    private double multiplier = 1;
    private String multiplierString = String.valueOf(multiplier);

    private IMultiplierChangeCallback callback = text -> {
        updateMultiplier(text);

        if (!onBind) {
            notifyItemRangeChanged(1, items.size());
        }
    };

    public void setInfo(CurrencyInfo info) {
        CurrencyRate baseRate = new CurrencyRate(info.getBase(), multiplier);

        if (this.info.getBase() == null) {
            this.info.setBase(info.getBase());
            items.add(baseRate);
        } else if (!this.info.getBase().equals(info.getBase())) {
            items.set(0, baseRate);
        }

        this.info.setRates(info.getRates());

        items.subList(1, items.size()).clear();
        items.addAll(info.getRatesList());

        notifyIfBind();
    }

    private void notifyIfBind() {
        if (!onBind) {
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new CurrencyViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int i) {
        onBind = true;
        CurrencyRate model = items.get(i);

        holder.setIsBase(i == 0);
        holder.setCallback(callback);
        holder.setName(model.getName());
        if (i != 0) {
            holder.setValue(String.valueOf(model.getValue() * multiplier));
        } else if (!holder.isEtInFocus()) {
            holder.setValue(multiplierString);
        }
        onBind = false;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        if (items.get(position).getName().equals("")) return 0;
        return super.getItemId(position);
    }

    /**
     * @param text text value from EditText {@link CurrencyViewHolder#etCurrency}
     */
    private void updateMultiplier(String text) {
        try {
            multiplier = Double.valueOf(text);
            multiplierString = text;
        } catch (NumberFormatException ex) {
            multiplier = 0D;
            multiplierString = "";
        }
    }

    public interface IMultiplierChangeCallback {
        void onMultiplierTextChanged(String text);
    }
}
