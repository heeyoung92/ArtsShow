package app.com.example.heeyoung.artsshow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new PlaceholderFragment())
                    .commit();
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
     /*   if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == R.id.action_profile) {
            //나의 프로필 보기
            Intent intent = new Intent(this, ProfileActivity.class);
            //+ 나의 ID 넘겨주기
            startActivity(intent);
            return true;
        }
        if(id==R.id.action_add){
            //작품추가 화면 띄우기
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment
    {
        private ArrayAdapter<ListItem> mProductListAdapter;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState)
        {
            ArrayList<ListItem> productList = new ArrayList<ListItem>();
            productList.add(new ListItem(0, R.drawable.ic_launcher, "전희영",
                    "Korea", "홍익대학교 도예유리", "1시간 전", R.drawable.ic_launcher, 2, "작품명1"));
            productList.add(new ListItem(1, R.drawable.ic_action_add, "구준호",
                    "Korea", "홍익대학교 회화과", "3시간 전", R.drawable.ic_launcher, 5, "작품명2"));
            productList.add(new ListItem(2, R.drawable.ic_action_add, "소고기",
                    "Korea", "홍익대학교 패션디자인", "5시간 전", R.drawable.ic_launcher, 3, "작품명4"));

            mProductListAdapter = new customAdapter(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    productList);

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ListView productListView = (ListView)rootView.findViewById(R.id.artslist);
            productListView.setAdapter(mProductListAdapter);

     //       updateProducts();

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
                            .url("http://arts.9cells.com/products/111/111")
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
                    ListItem[] items = gson.fromJson(jsonString, ListItem[].class);

                    mProductListAdapter.clear();
                    for(ListItem item : items) {
                        mProductListAdapter.add(item);
                    }
                }
            }
        }
    }
}
