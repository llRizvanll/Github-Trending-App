package com.android.app.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.app.mvvm.data.Item;
import com.android.app.mvvm.viewmodels.MainViewModels;
import com.facebook.shimmer.ShimmerFrameLayout;

import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private ListViewAdapter adapter;
    private MainViewModels mainViewModel;
    private ShimmerFrameLayout shimmerFrameLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModels.class);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_content);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        mainViewModel.init();

        RecyclerView recyclerView = findViewById(R.id.listview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new ListViewAdapter(mainViewModel.imagesVisible);
        recyclerView.setAdapter(adapter);

        subscriptions.add(mainViewModel.getTrendingReposList().subscribe(this::onResponse,this::onFailure));
        shimmerFrameLayout.startShimmerAnimation();

        swipeRefreshLayout.setOnRefreshListener( () -> {
                mainViewModel.getTrendingReposList().subscribe(MainActivity.this::onResponse,MainActivity.this::onFailure);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    private void onResponse(Item viewModel) {
        Log.d("Success : ", viewModel.itemDevName);
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        mainViewModel.toggleImageVisibility();
        adapter.add(viewModel);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void onFailure(Throwable t) {
        Log.e("Network error: ", t.getMessage());
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
