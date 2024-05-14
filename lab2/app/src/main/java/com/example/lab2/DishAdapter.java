package com.example.lab2;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lab2.modelcau5.Dish;

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish>{
    private Activity context;

    public DishAdapter(Activity context, int layoutID, List<Dish> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cau5_item_dish, null, false);
        }

        // Get item
        Dish dish = getItem(position);

        // Get view
        TextView tvName = (TextView) convertView.findViewById(R.id.item_dish_tv_name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.item_dish_iv_thumbnail);
        ImageView ivIsPromotion = (ImageView) convertView.findViewById(R.id.item_dish_iv_is_promotion);

        RelativeLayout llParent = (RelativeLayout) convertView.findViewById(R.id.item_dish_rl_parent);

        // Set fullname
        if (dish.getDishName() != null) {
            tvName.setText(dish.getDishName());
        }
        else tvName.setText("");

        // If this is a promotion -> show icon star. Otherwise, don't show it.
        if (dish.isPromotion())
        {
            ivIsPromotion.setVisibility(View.VISIBLE);
        }
        else
        {
            ivIsPromotion.setVisibility(View.GONE);
        }
        if (dish.getThumbnail() != null) {
            imageView.setImageResource(dish.getThumbnail().getImg());}

        // Phải có dòng này chữ mới chạy
        tvName.setSelected(true);
        return convertView;
    }
}