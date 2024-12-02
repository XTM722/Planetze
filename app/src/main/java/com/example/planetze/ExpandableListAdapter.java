package com.example.planetze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> mainMenuItems;
    private HashMap<String, List<String>> subMenuItems;

    public ExpandableListAdapter(Context context, List<String> mainMenuItems, HashMap<String, List<String>> subMenuItems) {
        this.context = context;
        this.mainMenuItems = mainMenuItems;
        this.subMenuItems = subMenuItems;
    }

    @Override
    public int getGroupCount() {
        return mainMenuItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return subMenuItems.get(mainMenuItems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainMenuItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subMenuItems.get(mainMenuItems.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        }
        TextView textViewGroup = convertView.findViewById(android.R.id.text1);
        textViewGroup.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childTitle = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_2, parent, false);
        }
        TextView textViewChild = convertView.findViewById(android.R.id.text2);
        textViewChild.setText(childTitle);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
