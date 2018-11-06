package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class TabFragment extends Fragment {
    private static final String TAB = "Tab1Fragment";
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.casino_main,container,false);
        listView = (ExpandableListView)view.findViewById(R.id.lvExp);
        new DoIt().execute();
        return view;
    }

    public class DoIt extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                listDataHeader = new ArrayList<>();
                listHash = new HashMap<>();
                List<String> typeList = new ArrayList<>();

                Document doc = Jsoup.connect("https://www.usm.cl/comunidad/servicio-de-alimentacion/").get();
                // adding days to header
                Elements days = doc.select(".responsive-table tr:eq(0) p");
                for (Element day : days){
                    listDataHeader.add(day.text());
                }
                listDataHeader.remove(0);

                // add types to accordion list
                Elements types  = doc.select(".responsive-table td:eq(0)");
                for (Element type : types){
                    typeList.add(type.text());
                }

                // add menu items to lists
                List<String> menuList = new ArrayList<>();
                List<List> allMenuList = new ArrayList<>();
                for (int i = 1; i < 6 ; i++) {
                    Elements menuItems  = doc.select(".responsive-table td:eq("+i+")");
                    for (int j = 0; j < 5 ; j++) {
                        if ((!menuItems.get(j).text().isEmpty()) ) {
                            menuList.add(typeList.get(j));
                            menuList.add(menuItems.get(j).text());
                        }
                    }
                    allMenuList.add(menuList);
                    menuList = new ArrayList<>();
                }

                for (int i = 0; i < 5 ; i++) {
                    String header = allMenuList.get(i).get(1).toString();
                    allMenuList.get(i).remove(0);
                    allMenuList.get(i).remove(0);
                    listHash.put(header,allMenuList.get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listAdapter = new ExpandableListAdapter(getActivity().getApplicationContext(),listDataHeader,listHash);
            listView.setAdapter(listAdapter);
        }
    }
}
