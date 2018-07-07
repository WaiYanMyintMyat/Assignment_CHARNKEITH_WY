package com.padc.charleskeith.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padc.charleskeith.R;
import com.padc.charleskeith.adapters.NewProductsAdapter;
import com.padc.charleskeith.data.models.NewProductModel;
import com.padc.charleskeith.delegates.NewProductDelegate;
import com.padc.charleskeith.events.ApiErrorEvent;
import com.padc.charleskeith.events.SuccessForceRefreshGetNewProductEvent;
import com.padc.charleskeith.events.SuccessGetNewProductEvent;
import com.padc.charleskeith.viewpods.EmptyViewPod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,NewProductDelegate {

    @BindView(R.id.rv_new_items)
    RecyclerView rvNewItems;

    @BindView(R.id.iv_single_view)
    ImageView ivSingleView;

    @BindView(R.id.iv_dual_view)
    ImageView ivDualView;

    @BindView(R.id.v_single_highlighter)
    View vSingleViewHighlighter;

    @BindView(R.id.v_dual_highlighter)
    View vDualViewHighlighter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_item_count)
    TextView tvItemCount;

    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    @BindView(R.id.vp_empty)
    EmptyViewPod vpEmpty;

    private NewProductsAdapter adapter;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
        tvItemCount.setText("20 Items");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer_opener);

        adapter = new NewProductsAdapter(this);

        rvNewItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isListEndReached=false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("NewsListActivity","OnScrollListener:onScrollStateChanged"+newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()
                        == recyclerView.getAdapter().getItemCount() - 1
                        && !isListEndReached){
                    isListEndReached=true;
                    NewProductModel.getObjInstance().loadNewProductList();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //dx delta x
                //dy delta y
                super.onScrolled(recyclerView, dx, dy);
                Log.d("NewsListActivity","OnScrollListener:onScrolled - dx : "+dx+", dy : "+dy);
                int visibleItemCount=recyclerView.getLayoutManager().getChildCount();
                int totalItemCount=recyclerView.getLayoutManager().getItemCount();//3
                int pastVisibleItems= ((LinearLayoutManager)recyclerView.getLayoutManager())
                        .findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) < totalItemCount) {
                    isListEndReached = false;
                }
            }
        });
        rvNewItems.setAdapter(adapter);
        rvNewItems.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        ivDualView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tvNew.getLayoutParams();
//                params.setMargins(0, 0, 26, 0); //substitute parameters for left, top, right, bottom
//                tvNew.setLayoutParams(params);
                rvNewItems.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                vDualViewHighlighter.setVisibility(View.VISIBLE);
                vSingleViewHighlighter.setVisibility(View.GONE);
            }
        });

        ivSingleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvNewItems.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                vSingleViewHighlighter.setVisibility(View.VISIBLE);
                vDualViewHighlighter.setVisibility(View.GONE);
            }
        });

        srl.setRefreshing(true);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewProductModel.getObjInstance().forceRefreshNewProductList();
            }
        });
        NewProductModel.getObjInstance().loadNewProductList();

        vpEmpty.setEmptyData(R.drawable.empty_data_placeholder,getString(R.string.empty_msg));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTapProduct() {
        Intent intent = new Intent(MainActivity.this,ProductDetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//UI mhar display ya mhar mo lo Main Thread use tar...
    public void onSuccessGetNewProduct(SuccessGetNewProductEvent event){
        Log.d("onSuccessGetNews","onSuccessGetNews:"+event.getNewProductList().size());
        adapter.appendNewProductList(event.getNewProductList());
        srl.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//UI mhar display ya mhar mo lo Main Thread use tar...
    public void onSuccessForceResultGetNewProduct(SuccessForceRefreshGetNewProductEvent event){
        Log.d("onSuccessGetNews","onSuccessGetNews:"+event.getmNewProductList().size());
        adapter.setNewProductList(event.getmNewProductList());
        srl.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//UI mhar display ya mhar mo lo Main Thread use tar...
    public void onFailGetNewProduct(ApiErrorEvent event){
        srl.setRefreshing(false);

        if(!event.getErrorMessage().equalsIgnoreCase("success")){
            vpEmpty.setVisibility(View.VISIBLE);
            Snackbar.make(srl,event.getErrorMessage(),Snackbar.LENGTH_INDEFINITE).show();//Snack bar show error use indefinite
        }
    }
}
