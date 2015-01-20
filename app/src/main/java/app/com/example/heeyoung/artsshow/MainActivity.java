package app.com.example.heeyoung.artsshow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new PlaceholderFragment())
                    .commit();

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment {

        private ArrayList<ListItem> Array_Data;

        private ListView m_artsList;
        private customAdapter m_Adapter;
        private ListItem data;

        public PlaceholderFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Array_Data = new ArrayList<ListItem>();
            data = new ListItem(0,R.drawable.ic_launcher, "전희영",
                    "Korea","홍익대학교 도예유리","1시간 전",R.drawable.ic_launcher,2,"작품명1");
            Array_Data.add(data);


            data = new ListItem(1,R.drawable.ic_action_add, "구준호",
                    "Korea","홍익대학교 회화과","3시간 전",R.drawable.ic_launcher,5,"작품명2");
            Array_Data.add(data);


            data = new ListItem(2, R.drawable.ic_action_add, "소고기",
                    "Korea","홍익대학교 패션디자인","5시간 전",R.drawable.ic_launcher,3,"작품명4");
            Array_Data.add(data);


            m_Adapter = new customAdapter(getActivity(), android.R.layout.simple_list_item_1, Array_Data);

            m_artsList = (ListView) rootView.findViewById(R.id.artslist);

            m_artsList.setAdapter(m_Adapter);


            return rootView;
        }
    }
}
