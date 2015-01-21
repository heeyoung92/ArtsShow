package app.com.example.heeyoung.artsshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import app.com.example.heeyoung.artsshow.model.Brand;


public class ProfileActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_back) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

            Intent intent = getActivity().getIntent();
            Brand brand = intent.getParcelableExtra("brand");

            Glide.with(getActivity()).load(brand.brand_image)
                    .into((ImageView)rootView.findViewById(R.id.p_artistView));
            ((TextView)rootView.findViewById(R.id.p_artistName)).setText(brand.brand_name);
            ((TextView)rootView.findViewById(R.id.p_artistNation)).setText(brand.brand_country);
            ((TextView)rootView.findViewById(R.id.p_artistUniv)).setText(brand.brand_info);
            ((TextView)rootView.findViewById(R.id.p_email)).setText(brand.brand_info);
            ((TextView)rootView.findViewById(R.id.p_phone)).setText(brand.brand_info);

            GridView grid = (GridView)rootView.findViewById(R.id.grid_arts);

            ImageAdapter adapter = new ImageAdapter(getActivity());
            grid.setAdapter(adapter);

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), position +"번째 그림 선택", Toast.LENGTH_SHORT).show();

                    //작품 ID값을 통해 작품 디테일Activity 호출
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                  //         .putExtra(Intent.EXTRA_TEXT, 작품ID);

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

            return rootView;
        }
    }
}

class ImageAdapter extends BaseAdapter {
    private Context context;

    //작품사진 (임시로 리소스이미지 번걸아가면서 출력//이미지크면에러)
    int[] arts_picture = {
            R.drawable.image,
            R.drawable.arts_add
    };

    public ImageAdapter(Context c){
        context = c;
    }
    public int getCount(){
        return 100; //100개의 행렬 준비
    }

    public Object getItem(int position){
        return arts_picture[position % 2];
    }
    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(80, 60));
            imageView.setAdjustViewBounds(false);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(arts_picture[position % 2]);
        return imageView;

    }
}

