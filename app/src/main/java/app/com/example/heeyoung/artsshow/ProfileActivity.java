package app.com.example.heeyoung.artsshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.com.example.heeyoung.artsshow.model.Brand;
import app.com.example.heeyoung.artsshow.model.Product;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class ProfileActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if ( savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ProfileFragment())
                    .commit();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

       if ( id == android.R.id.home ) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ProfileFragment extends Fragment
    {
        @InjectView(R.id.p_artistView) ImageView artistView;
        @InjectView(R.id.p_artistName) TextView artistName;
        @InjectView(R.id.p_artistNation) TextView artistNation;
        @InjectView(R.id.p_artistUniv) TextView artistUniv;
        @InjectView(R.id.p_email) TextView email;
        @InjectView(R.id.p_phone) TextView phone;
        @InjectView(R.id.grid_arts) GridView gridArts;

        private ImageAdapter mImageAdapter;
        private int mOffset = 0;

        public ProfileFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState)
        {
            Brand brand = getActivity().getIntent().getParcelableExtra("brand");

            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            ButterKnife.inject(this, rootView);

            Glide.with(getActivity()).load(brand.brand_image).into(artistView);
            artistName.setText(brand.brand_name);
            artistNation.setText(brand.brand_country);
            artistUniv.setText(brand.brand_info);
            email.setText(brand.brand_info);
            phone.setText(brand.brand_info);

            mImageAdapter = new ImageAdapter(getActivity(), android.R.layout.simple_list_item_1);
            gridArts.setAdapter(mImageAdapter);
            gridArts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Product product = mImageAdapter.getItem(position);
                    Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .putExtra("product", product);
                    startActivity(intent);
                }
            });

            // 내 프로필일 경우 프로필 사진 클릭 시 편집화면
           /* if(작가ID == 내ID) {
                img_artist.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "내 프로필 수정", Toast.LENGTH_SHORT).show();
                        //profile 수정 Webview

                    }
                });
             } */
            updateProducts(brand.brand_id, mOffset);

            return rootView;
        }

        @Override
        public void onDestroyView()
        {
            super.onDestroyView();
            ButterKnife.reset(this);
        }

        private void updateProducts(String brandId, int offset)
        {
            FetchProductsTask.getBrandProducts(brandId, offset, mImageAdapter);
        }
    }

    static class ImageAdapter extends ArrayAdapter<Product>
    {
        public ImageAdapter(Context context, int resource)
        {
            super(context, resource);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            if ( convertView == null ) {
                imageView = new ImageView(getContext());
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setAdjustViewBounds(false);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
                imageView = (ImageView)convertView;
            }
            Product product = getItem(position);
            Glide.with(getContext()).load(product.images[0].url).into(imageView);
            return imageView;
        }
    }
}
