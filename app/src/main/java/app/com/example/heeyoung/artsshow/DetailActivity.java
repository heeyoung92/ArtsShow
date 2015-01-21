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
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;


public class DetailActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_back) {
               finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment
    {
        private String mArtsID;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Intent intent = getActivity().getIntent();      // 작품ID 받음
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mArtsID = intent.getStringExtra(intent.EXTRA_TEXT);
            //    ((TextView)rootView.findViewById(R.id.artsId))
             //           .setText(mArtsID);
         //       ((TextView)rootView.findViewById(R.id.artsId)).setText(mArtsID);
            }

            //작품 갤러리 뷰
            @SuppressWarnings("deprecation")
            Gallery gal =(Gallery)rootView.findViewById(R.id.detail_arts_img);
            gal.setAdapter(new galleryAdapter(getActivity()));

            // 사진 선택
            gal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), position + "번째 그림 선택", Toast.LENGTH_SHORT).show();
                }
                public void onNothingSelected(AdapterView<?> parent){

                }
            });

            return rootView;
        }
    }
}

class galleryAdapter extends BaseAdapter {
    private Context context;

    //작품사진
    private int[] arts_picture_ids = {
            R.drawable.image,
            R.drawable.arts_add,
            R.drawable.image
    };

    public galleryAdapter(Context c){
        context = c;
    }
    public int getCount(){
        return arts_picture_ids.length;
    }

    public Object getItem(int position){
        return arts_picture_ids[position];
    }
    public long getItemId(int position){
        return position;
    }

    @SuppressWarnings("deprecation")
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(context);

        }else{
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(arts_picture_ids[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new Gallery.LayoutParams(136,88));
        return imageView;

    }
}

