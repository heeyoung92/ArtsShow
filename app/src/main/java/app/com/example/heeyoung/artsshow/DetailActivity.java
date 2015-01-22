package app.com.example.heeyoung.artsshow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import app.com.example.heeyoung.artsshow.model.Image;
import app.com.example.heeyoung.artsshow.model.Product;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class DetailActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if ( savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ProductDetailFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if ( id == R.id.action_back ) {
               finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ProductDetailFragment extends Fragment
    {
        @InjectView(R.id.detail_arts_name) TextView artsName;
        @InjectView(R.id.detail_caption) TextView caption;
        @InjectView(R.id.detail_arts_img) Gallery gallery;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState)
        {
            Product product = getActivity().getIntent().getParcelableExtra("product");

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            ButterKnife.inject(this, rootView);

            artsName.setText(product.prd_title);
            caption.setText(product.prd_desc);
            gallery.setAdapter(new ProductDetailAdapter(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    product.images
            ));

            // 사진 선택
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), position + "번째 그림 선택", Toast.LENGTH_SHORT).show();
                }
            });

            return rootView;
        }

        @Override
        public void onDestroyView()
        {
            super.onDestroyView();
            ButterKnife.reset(this);
        }

        static class ProductDetailAdapter extends ArrayAdapter<Image>
        {
            public ProductDetailAdapter(Context context, int resource, Image[] objects)
            {
                super(context, resource, objects);
            }

            @SuppressWarnings("deprecation")
            public View getView(int position, View convertView, ViewGroup parent)
            {
                ImageView imageView;
                if ( convertView == null ) {
                    imageView = new ImageView(getContext());
                } else {
                    imageView = (ImageView)convertView;
                }
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
                imageView.setLayoutParams(new Gallery.LayoutParams(dm.widthPixels - 200, (dm.heightPixels / 2) - 150));  //이미지크기 화면크기에 반정도로!

                Image image = getItem(position);
                Glide.with(getContext()).load(image.url).into(imageView);
                return imageView;
            }
        }
    }
}

