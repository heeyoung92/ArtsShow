package app.com.example.heeyoung.artsshow;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import app.com.example.heeyoung.artsshow.model.Product;

public class FetchProductsTask extends AsyncTask<String, Void, String>
{
    OkHttpClient mClient;
    String mURLString;
    ArrayAdapter mAdapter;

    public static void getBrandProducts(String brandId, int offset, ArrayAdapter adapter)
    {
        FetchProductsTask.getProducts("brand/" + brandId, offset, adapter);
    }

    public static void getProducts(String type, int offset, ArrayAdapter adapter)
    {
        new FetchProductsTask(type, offset, adapter).execute();
    }

    public FetchProductsTask(String type, int offset, ArrayAdapter adapter)
    {
        mClient = new OkHttpClient();
        mURLString = "http://arts.9cells.com/api1/products/" + type
                + "/offset/" + new Integer(offset).toString();
        mAdapter = adapter;
    }

    @Override
    protected String doInBackground(String... params)
    {
        String result = "";

        try {
            Request request = new Request.Builder().url(mURLString).build();
            Response response = mClient.newCall(request).execute();
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

            mAdapter.clear();
            for ( Product product : products ) {
                mAdapter.add(product);
            }
        }
    }
}
