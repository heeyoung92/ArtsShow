package app.com.example.heeyoung.artsshow;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.tabs) PagerSlidingTabStrip tabs;
    @InjectView(R.id.pager) ViewPager pager;

    private MainTabAdapter adapter;
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
        adapter = new MainTabAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int)TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                4,
                getResources().getDisplayMetrics()
        );
        pager.setPageMargin(pageMargin);
        changeColor(getResources().getColor(R.color.green));

        if ( !Glide.isSetup() ) {
            // Glide 캐시 설정 https://github.com/bumptech/glide/wiki/Configuration
            // Disk 100MB, Mem 20MB
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

        if ( id == R.id.action_settings ) {
            return true;
        }

        if ( id == R.id.action_profile ) {
            //나의 프로필 보기
            Toast.makeText(MainActivity.this, "로그인 정보가 필요합니다.", Toast.LENGTH_SHORT).show();

            /*
            Intent intent = new Intent(this, ProfileActivity.class);
            //+ 나의 ID 넘겨주기
            startActivity(intent); */
            return true;
        }

        if ( id == R.id.action_add ) {
            //작품추가 화면 띄우기

         //   Toast.makeText(MainActivity.this, " 준비중입니다.", Toast.LENGTH_SHORT).show();

              Intent intent = new Intent(this, ArtsRegisterActivity.class);
             startActivity(intent);
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
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        changeColor(currentColor);
    }

    class MainTabAdapter extends FragmentPagerAdapter
    {
        private final String[] TITLES = {
                " 최신순 ", " 인기순 "
        };

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position)
        {
            ProductListFragment fragment = new ProductListFragment();
            Bundle args = new Bundle();
            if ( position == 0 ) {
                args.putString("type", "recent");
            } else if ( position == 1 ) {
                args.putString("type", "popular");
            }
            fragment.setArguments(args);
            return fragment;
        }

        public void setPrimaryItem(ViewGroup container, int position, Object object)
        {
            super.setPrimaryItem(container, position, object);

            int color = -1;
            switch ( position ) {
                case 0:
                    color = R.color.green;
                    break;
                case 1:
                    color = R.color.red;
                    break;
                default:
                    color = R.color.green;
                    break;
            }

            changeColor(getResources().getColor(color));
        }
    }

    public static class ProductListFragment extends Fragment
    {
        @InjectView(R.id.artslist) ListView productListView;

        private ArrayAdapter<Product> mProductListAdapter;
        private int mOffset = 0;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState)
        {
            Bundle args = getArguments();

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.inject(this, rootView);

            mProductListAdapter = new ProductListAdapter(getActivity(), android.R.layout.simple_list_item_1);
            productListView.setAdapter(mProductListAdapter);
            updateProducts(args.getString("type"));

            return rootView;
        }

        @Override
        public void onDestroyView()
        {
            super.onDestroyView();
            ButterKnife.reset(this);
        }

        private void updateProducts(String type)
        {
            FetchProductsTask productsTask = new FetchProductsTask();
            productsTask.execute(type, new Integer(mOffset).toString());
        }

        @Override
        public void onResume()
        {
            super.onResume();
            Bundle args = getArguments();
            updateProducts(args.getString("type"));
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
                            .url("http://arts.9cells.com/api1/products/" + params[0] + "/offset/" + params[1])
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

        static class ProductListAdapter extends ArrayAdapter<Product>
        {
            int mCheck = 0; //(임시)좋아요 체크 여부 -> DB저장 필요

            public ProductListAdapter(Context context, int resource)
            {
                super(context, resource);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                final Context context = parent.getContext();
                ViewHolder viewholder;
                if ( convertView != null ) {
                    viewholder = (ViewHolder)convertView.getTag();
                } else {
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.custom_item, parent, false);
                    viewholder = new ViewHolder(convertView);
                    convertView.setTag(viewholder);
                }

                final Product product = getItem(position);
                final ViewHolder holder = viewholder;

                Glide.with(context).load(product.brand.brand_image).into(holder.m_artist_img);
                holder.m_artist_name.setText(product.brand.brand_name);
                holder.m_artist_na.setText(product.brand.brand_country);
                holder.m_artist_inf.setText(product.brand.brand_info);
                holder.m_time.setText(product.updated_at);
                Glide.with(context).load(product.images[0].url).into(holder.m_arts_img);
                holder.m_arts_text.setText(product.prd_title);
                holder.m_like.setText(String.valueOf(product.prd_num_likes));

                // like 버튼 클릭 리스너
                holder.m_Btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ( mCheck == 0 ) {
                            // 클릭 시 해당 아이템 좋아요 +1
                            product.prd_num_likes++;
                            holder.m_Btn.setBackgroundResource(R.drawable.click);
                            mCheck = 1;
                        } else {
                            product.prd_num_likes--;
                            holder.m_Btn.setBackgroundResource(R.drawable.notclick);
                            mCheck = 0;
                        }

                        holder.m_like.setText(String.valueOf(product.prd_num_likes));

                        // + LIKE 수 DB에 저장

                        //Toast.makeText(context, "+1", Toast.LENGTH_SHORT).show();
                    }
                });

                // 작품 사진 클릭 리스너
                holder.m_arts_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "작품 디테일 호출 : " + product.prd_id, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, DetailActivity.class)
                                .putExtra("product", product);
                        context.startActivity(intent);
                    }
                });

                // 프로필 사진 클릭 리스너
                holder.m_artist_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ProfileActivity.class)
                                .putExtra("brand", product.brand);
                        context.startActivity(intent);
                    }
                });

                return convertView;
            }

            static class ViewHolder
            {
                @InjectView(R.id.artistName)
                TextView m_artist_name;
                @InjectView(R.id.button_like)
                ImageButton m_Btn;
                @InjectView(R.id.artistView)
                ImageView m_artist_img;
                @InjectView(R.id.time) TextView m_time;
                @InjectView(R.id.artistNation) TextView m_artist_na;
                @InjectView(R.id.artistUniv) TextView m_artist_inf;
                @InjectView(R.id.arts_image) ImageView m_arts_img;
                @InjectView(R.id.like_num) TextView m_like;
                @InjectView(R.id.arts_name) TextView m_arts_text;

                public ViewHolder(View view)
                {
                    ButterKnife.inject(this, view);
                }
            }
        }
    }
}
