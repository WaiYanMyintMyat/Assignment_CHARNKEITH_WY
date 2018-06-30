package com.padc.charleskeith.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.padc.charleskeith.R;
import com.padc.charleskeith.adapters.NewProductsAdapter;
import com.padc.charleskeith.delegates.NewProductDelegate;

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

    private NewProductsAdapter adapter;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        rvNewItems.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new NewProductsAdapter(this);
        rvNewItems.setAdapter(adapter);

        ivDualView.setOnClickListener(v -> {
                    rvNewItems.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
                    vDualViewHighlighter.setVisibility(View.VISIBLE);
                    vSingleViewHighlighter.setVisibility(View.GONE);
                }
        );

        ivSingleView.setOnClickListener(v -> {
            rvNewItems.setLayoutManager(new GridLayoutManager(v.getContext(), 1));
            vSingleViewHighlighter.setVisibility(View.VISIBLE);
            vDualViewHighlighter.setVisibility(View.GONE);
        });
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
        Intent intent = new Intent(getApplicationContext(),ProductDetailActivity.class);
        startActivity(intent);
    }
}
