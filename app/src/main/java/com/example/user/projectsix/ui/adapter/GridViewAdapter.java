package com.example.user.projectsix.ui.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.projectsix.Consts;
import com.example.user.projectsix.R;
import com.example.user.projectsix.model.GridItem;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<GridItem> mGridData = new ArrayList<>();


    public GridViewAdapter(Context mContext, ArrayList<GridItem> mGridData) {
        this.mContext = mContext;
        this.mGridData = mGridData;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }

    @Override
    public int getCount() {
        return mGridData.size();
    }

    @Override
    public Object getItem(int position) {
        return mGridData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            mInflater = LayoutInflater.from(parent.getContext());
            row = mInflater.inflate(R.layout.grid_item_layout, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.txtGridItemTitle);
            holder.imageView = (ImageView) row.findViewById(R.id.imgGridItem);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = mGridData.get(position);
        mGridData.get(position).setTitle(Consts.TITLE[position]);
        holder.titleTextView.setText(item.getTitle());
        holder.imageView.setImageBitmap(item.getImage());
        return row;
    }
}
