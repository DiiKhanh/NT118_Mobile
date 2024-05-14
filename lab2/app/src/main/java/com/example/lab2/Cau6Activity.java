package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lab2.modelcau6.Hero;

import java.util.ArrayList;

public class Cau6Activity extends AppCompatActivity {
    private ArrayList<Hero> mHeros;
    private RecyclerView mRecyclerHero;
    private HeroAdapter mHeroAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cau6);
        mRecyclerHero = findViewById(R.id.recycleHero);
        mHeros = new ArrayList<>();
        creatHeroList();
        mHeroAdapter = new HeroAdapter(this, mHeros);
        mRecyclerHero.setAdapter(mHeroAdapter);
        mRecyclerHero.setLayoutManager(new LinearLayoutManager(this));
    }

    private void creatHeroList() {
        mHeros.add(new Hero("Thor", R.drawable.thor));
        mHeros.add(new Hero("ironMan", R.drawable.ironman));
        mHeros.add(new Hero("SpiderMan", R.drawable.spider));
        mHeros.add(new Hero("Captain America", R.drawable.captain));
        mHeros.add(new Hero("Thor", R.drawable.thor));
        mHeros.add(new Hero("ironMan", R.drawable.ironman));
        mHeros.add(new Hero("SpiderMan", R.drawable.spider));
        mHeros.add(new Hero("Captain America", R.drawable.captain));
        mHeros.add(new Hero("Thor", R.drawable.thor));
        mHeros.add(new Hero("ironMan", R.drawable.ironman));
        mHeros.add(new Hero("SpiderMan", R.drawable.spider));
        mHeros.add(new Hero("Captain America", R.drawable.captain));
    }

    @Override
    public void onBackPressed() {
        // Quay lại MainActivity khi nút back được nhấn
        super.onBackPressed();
    }
}