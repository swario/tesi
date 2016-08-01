package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.cristian.everysale.R;

import java.util.ArrayList;
import java.util.HashMap;

public class tabRecentOffers extends ListFragment {

    private ListView itemsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recent_listview,container,false);
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for(int i=0; i<10; i++){
            HashMap<String, String> map = new HashMap<>();
            map.put("icon", "@mipmap/ic_launcher");
            map.put("title", "Titolo");
            map.put("price", "Prezzo");
            map.put("city", "Città");
            map.put("rating", "Rating");
            data.add(map);
        }
        int resource = R.layout.listview_item_layout;
        String[] from = {"icon", "title", "price", "city", "rating"};
        int[] to = {R.id.item_icon, R.id.item_title, R.id.item_price, R.id.item_city, R.id.item_ratingBar};
        SimpleAdapter adapter= new SimpleAdapter(getContext(), data, resource, from, to);
        itemsListView = (ListView) getListView().findViewById(R.id.recent_listView);
        itemsListView.setAdapter(adapter);

        return v;
    }
}