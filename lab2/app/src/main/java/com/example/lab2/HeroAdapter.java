package com.example.lab2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lab2.modelcau6.Hero;

import java.util.ArrayList;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroHolder> {
    private Context mContext;
    private ArrayList<Hero> mHeros;
    public HeroAdapter(Context mContext, ArrayList<Hero> mHeros){
        this.mContext = mContext;
        this.mHeros = mHeros;
    }

    class HeroHolder extends RecyclerView.ViewHolder {
        private ImageView mImageHero;
        private TextView mTextName;

        public HeroHolder (@NonNull View itemView) {
            super(itemView);
            mImageHero = itemView.findViewById(R.id.image_hero);
            mTextName = itemView.findViewById(R.id.text_name);
        }
    }

    @NonNull
    @Override
    public HeroHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.cau6_hero, parent, false);
        HeroHolder heroHolder = new HeroHolder(heroView);
        return heroHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HeroHolder holder, int position) {
        Hero hero = mHeros.get(position);
        Glide.with(mContext).load(hero.getImage()).into(holder.mImageHero);
        holder.mTextName.setText(hero.getName());
    }

    @Override
    public int getItemCount() {
        return mHeros.size();
    }

}