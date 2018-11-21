package com.ihavenodomain.everysecondvalute.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ihavenodomain.everysecondvalute.R;
import com.ihavenodomain.everysecondvalute.ui.mainList.MyAdapter;

public class MainActivity extends AppCompatActivity {
    private MyAdapter adapter;
    private RecyclerView rvItems;
    private TextView tvError;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        tvError = findViewById(R.id.tv_error);

        rvItems = findViewById(R.id.rv_items);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(manager);

        adapter = new MyAdapter(base -> viewModel.startCurrencyLoadingSequence(base));

        rvItems.setAdapter(adapter);
        rvItems.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }

    private void bind() {
        viewModel.getCurrencyInfo().observe(this, currencyInfo -> {
            if (currencyInfo == null || currencyInfo.getRates() == null) {
                tvError.setVisibility(View.VISIBLE);
                rvItems.setVisibility(View.GONE);
            } else {
                tvError.setVisibility(View.GONE);
                rvItems.setVisibility(View.VISIBLE);

                adapter.setInfo(currencyInfo);
            }
        });
    }

    private void unbind() {
        viewModel.endCurrencyLoadingSequence();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
    }

    public interface NewDataNeededCallback {
        void requestNewRates(String base);
    }
}