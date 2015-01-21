package app.com.example.heeyoung.artsshow;

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
import android.widget.ListView;

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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

    public static class PlaceholderFragment extends Fragment
    {
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

            customAdapter productListAdapter = new customAdapter(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    productList);

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ListView productListView = (ListView)rootView.findViewById(R.id.artslist);
            productListView.setAdapter(productListAdapter);

            updateProducts();

            return rootView;
        }

        private void updateProducts()
        {
            FetchProductsTask productsTask = new FetchProductsTask();
            productsTask.execute("0");
        }

        private class FetchProductsTask extends AsyncTask<String, Void, String[]>
        {
            OkHttpClient client = new OkHttpClient();

            @Override
            protected String[] doInBackground(String... params)
            {
                try {

                    Request request = new Request.Builder()
                            .url("http://arts.9cells.com/products/111/111")
                            .build();
                    Response response = client.newCall(request).execute();
                    Log.d("From MainActivity", response.body().string());

                } catch (Exception e) {

                }

                return null;
            }

        }

    }
}
