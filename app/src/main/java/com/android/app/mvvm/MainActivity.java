package com.android.app.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
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
import android.widget.Toast;

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
        toggleLoader(true);
        RecyclerView recyclerView = findViewById(R.id.listview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new ListViewAdapter(mainViewModel.imagesVisible);
        recyclerView.setAdapter(adapter);

        mainViewModel.getShowLoader().observe(this, aBoolean -> {
            if (!aBoolean) toggleLoader(false);
        });

        subscriptions.add(mainViewModel.getTrendingReposList().subscribe(this::onResponse,this::onFailure));


        swipeRefreshLayout.setOnRefreshListener( () -> {
            mainViewModel.getTrendingReposList().subscribe(MainActivity.this::onResponse,MainActivity.this::onFailure);
            adapter.getItems().clear();
            adapter.notifyDataSetChanged();
        });

        //swipeRefreshLayout.post(()-> {swipeRefreshLayout.setRefreshing(true);});
    }

    @Override
    protected void onResume() {
        super.onResume();
        //toggleLoader(true);
    }

    private void onResponse(Item viewModel) {
        Log.d("Success : ", viewModel.itemDevName);
        adapter.add(viewModel);
        mainViewModel.toggleImageVisibility();
        toggleLoader(false);
    }

    private void onFailure(Throwable t) {
        Log.e("Network error: ", t.getMessage());
        Toast.makeText(this,"An error occured1 , Try Again!",Toast.LENGTH_SHORT).show();
        toggleLoader(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        toggleLoader(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
        toggleLoader(false);
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

    private void toggleLoader(boolean show){
        if (show){
            if (!shimmerFrameLayout.isAnimationStarted())
                shimmerFrameLayout.startShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
        }
        else {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
