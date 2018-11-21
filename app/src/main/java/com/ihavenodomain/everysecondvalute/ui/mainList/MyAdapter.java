package com.ihavenodomain.everysecondvalute.ui.mainList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import com.ihavenodomain.everysecondvalute.model.CurrencyInfo;
import com.ihavenodomain.everysecondvalute.model.CurrencyRate;
import com.ihavenodomain.everysecondvalute.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private static final int TYPE_BASE = 0;
    private static final int TYPE_GENERIC = 1;

    private boolean onBind;

    private CurrencyInfo info = new CurrencyInfo();
    private List<CurrencyRate> items = new ArrayList<>();

    private double multiplier = 1;
    private String multiplierString = String.valueOf(multiplier);

    private MyViewHolder staticHolder;
    private boolean needRecreate = false;

    private MainActivity.NewDataNeededCallback newDataNeededCallback;

    public MyAdapter(MainActivity.NewDataNeededCallback newDataNeededCallback) {
        this.newDataNeededCallback = newDataNeededCallback;
    }

    public void setInfo(CurrencyInfo info) {
        CurrencyRate baseRate = new CurrencyRate(info.getBase(), multiplier, true);

        if (this.info.getBase() == null) {
            items.add(baseRate);
        } else if (!this.info.getBase().equals(info.getBase())) {
            items.set(0, baseRate);
        }

        this.info.setBase(info.getBase());
        this.info.setRates(info.getRates());

        items.subList(1, items.size()).clear();
        items.addAll(info.getRatesList());

        if (!firstBinded || needRecreate) {
            notifyIfBind();
        } else {
            notifyRangeIfBind();
        }
    }

    private void notifyIfBind() {
        if (!onBind) {
            notifyDataSetChanged();
        }
    }

    private void notifyRangeIfBind() {
        if (!onBind) {
            notifyItemRangeChanged(1, items.size());
        }
    }

    private MyViewHolder getStaticViewHolder(@NonNull ViewGroup parent) {
        if (staticHolder == null || needRecreate) {
            staticHolder = new MyViewHolder(parent, watcher);
            staticHolder.setIsRecyclable(false);
        }
        return staticHolder;
    }

    @Override
    public int getItemViewType(int position) {
        CurrencyRate rate = items.get(position);
        if (rate.isBase()) {
            return TYPE_BASE;
        } else {
            return TYPE_GENERIC;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_BASE) {
            return getStaticViewHolder(parent);
        } else {
            return new MyViewHolder(parent, itemClicker);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        onBind = true;
        CurrencyRate item = items.get(i);
        boolean base = item.isBase();

        holder.setName(item.getName());
        if (i != 0) {
            holder.setValue(String.valueOf(item.getValue() * multiplier));
        } else if (!firstBinded || needRecreate) {
            holder.setValue(multiplierString);
            firstBinded = true;
            needRecreate = false;
        }
        onBind = false;
    }

    private boolean firstBinded = false;

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
     * @param text text value from EditText {@link MyViewHolder#etCurrency}
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

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            updateMultiplier(s.toString());
            notifyRangeIfBind();
        }
    };

    private ItemClickListener itemClicker = new ItemClickListener() {
        @Override
        public void onItemClick(int position) {
            needRecreate = true;
            String baseNew = items.get(position).getName();
            String newMultiplier = String.valueOf(items.get(position).getValue());

            updateMultiplier(newMultiplier);
            newDataNeededCallback.requestNewRates(baseNew);
        }
    };

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
