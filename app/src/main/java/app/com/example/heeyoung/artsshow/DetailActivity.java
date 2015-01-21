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

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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

            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mArtsID = intent.getStringExtra(intent.EXTRA_TEXT);
         //       ((TextView)rootView.findViewById(R.id.artsId)).setText(mArtsID);
            }

            updateProduct();

            return rootView;
        }

        private void updateProduct()
        {
            FetchProductTask productsTask = new FetchProductTask();
            productsTask.execute("0");
        }

        private class FetchProductTask extends AsyncTask<String, Void, String[]>
        {
            OkHttpClient client = new OkHttpClient();

            @Override
            protected String[] doInBackground(String... params)
            {
                try {

                    Request request = new Request.Builder()
                            .url("http://arts.9cells.com/products/132435")
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
