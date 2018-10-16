package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String,List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }


    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int j) {
        // i = Group item, j = ChildItem
        return listHashMap.get(listDataHeader.get(i)).get(j);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int j) {
        return j;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.casino_list_group,null);
        }
        TextView lbListHeader = (TextView)view.findViewById(R.id.lblListHeader);
        lbListHeader.setTypeface(null,Typeface.BOLD);
        lbListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int j, boolean isLastChild, View view, ViewGroup viewGroup) {
        String childText = (String)getChild(i,j);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.casino_list_item,null);
        }
        TextView txtListChild = (TextView)view.findViewById(R.id.lblListItem);
        if (childText.equals("Almuerzos") || childText.equals("Vegetariano") || childText.equals("Dietas") || childText.equals("Cenas")){
            txtListChild.setTypeface(null,Typeface.BOLD);
        } else {
            txtListChild.setTypeface(null,Typeface.NORMAL);
        }
        txtListChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int j) {
        return true;
    }
}
