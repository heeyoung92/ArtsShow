package app.com.example.heeyoung.artsshow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import app.com.example.heeyoung.artsshow.model.Product;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity
{
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.pager)
    ViewPager pager;

    private MyPagerAdapter adapter;
    private Drawable oldBackground = null;
    private int currentColor;
    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        changeColor(getResources().getColor(R.color.green));

        tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                //Toast.makeText(MainActivity.this, "Tab reselected: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        // Glide 캐시 설정 https://github.com/bumptech/glide/wiki/Configuration
        // Disk 100MB, Mem 20MB
        if (!Glide.isSetup()) {
            Glide.setup(new GlideBuilder(this)
                    .setDiskCache(DiskLruCacheWrapper.get(Glide.getPhotoCacheDir(this), 100000000))
                    .setMemoryCache(new LruResourceCache(20000000))
            );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_profile) {
            //나의 프로필 보기
            Intent intent = new Intent(this, ProfileActivity.class);
            //+ 나의 ID 넘겨주기
            startActivity(intent);
            return true;
        } else if (id == R.id.action_add) {
            //작품추가 화면 띄우기
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColor(int newColor)
    {
        tabs.setBackgroundColor(newColor);
        mTintManager.setTintColor(newColor);
        // change ActionBar color just if an ActionBar is available
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        if (oldBackground == null) {
            getSupportActionBar().setBackgroundDrawable(ld);
        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
            getSupportActionBar().setBackgroundDrawable(td);
            td.startTransition(200);
        }

        oldBackground = ld;
        currentColor = newColor;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeColor(currentColor);
    }

    public static class ProductListFragment extends Fragment
    {
        private ArrayAdapter<Product> mProductListAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            mProductListAdapter = new ProductListAdapter(getActivity(), android.R.layout.simple_list_item_1);
            ListView productListView = (ListView)rootView.findViewById(R.id.artslist);
            productListView.setAdapter(mProductListAdapter);
            updateProducts();

            return rootView;
        }

        private void updateProducts()
        {
            FetchProductsTask productsTask = new FetchProductsTask();
            productsTask.execute("0");
        }

        private class FetchProductsTask extends AsyncTask<String, Void, String>
        {
            OkHttpClient client = new OkHttpClient();

            @Override
            protected String doInBackground(String... params)
            {
                String result = "";

                try {
                    Request request = new Request.Builder()
                            .url("http://arts.9cells.com/api1/products/recent/")
                            .build();
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }

                return result;
            }

            @Override
            protected void onPostExecute(String jsonString)
            {
                super.onPostExecute(jsonString);

                if ( jsonString.length() > 0 ) {
                    Gson gson = new GsonBuilder().create();
                    Product[] products = gson.fromJson(jsonString, Product[].class);

                    mProductListAdapter.clear();
                    for(Product product : products) {
                        mProductListAdapter.add(product);
                    }
                }
            }
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {
                "최신순", "인기순"
        };

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];

        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return new ProductListFragment();
        }

        public void setPrimaryItem(ViewGroup container, int position, Object object)
        {
            super.setPrimaryItem(container, position, object);

            if ( position == 0 ) {
                changeColor(getResources().getColor(R.color.green));
            } else if ( position == 1 ) {
                changeColor(Color.parseColor("#FFC74B46"));
            }
        }
    }
}
