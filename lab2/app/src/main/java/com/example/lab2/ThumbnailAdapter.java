package com.example.lab2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class ThumbnailAdapter extends ArrayAdapter<Thumbnail> {
    private LayoutInflater layoutInflater;

    public ThumbnailAdapter(@NonNull Context context, int resource, @NonNull  List<Thumbnail> objects)
    {
        super(context, resource, objects);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView,@NonNull ViewGroup parent)
    {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cau5_dropdown_item, null, true);
        }

        // Get item
        Thumbnail thumbnail = getItem(position);
        // Get view
        TextView tvThumbnailName = (TextView) convertView.findViewById(R.id.item_selected_thumbnail_tv_name);
        ConstraintLayout llParent = (ConstraintLayout) convertView.findViewById(R.id.item_selected_thumbnail_cl_parent);

        // Set fullname
        if (thumbnail.getName() != null) {
            tvThumbnailName.setText(thumbnail.getName());
        }
        else tvThumbnailName.setText("");
        // If this is a promotion -> show icon star. Otherwise, don't show it.

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cau5_thumbnail, parent, false);
        }

        // Get item
        Thumbnail thumbnail = getItem(position);

        // Get view
        TextView tvThumbnailName = (TextView) convertView.findViewById(R.id.item_thumbnail_tv_thumbnail_name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_thumbnail_iv_thumbnail);

        ConstraintLayout llParent = (ConstraintLayout) convertView.findViewById(R.id.item_thumbnail_cl_parent);

        // Set image
        imageView.setImageResource(thumbnail.getImg());
        // Set fullname
        if (thumbnail.getName() != null) {
            tvThumbnailName.setText(thumbnail.getName());
        }
        else tvThumbnailName.setText("");
        return convertView;
    }
}