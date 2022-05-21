package com.example.code06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] titles = null;
    private String[] authors = null;
    private List<Map<String, String>> dataList = new ArrayList<>();
    private static final String NEWS_TITLE = "news_title";
    private static final String NEWS_AUTHOR = "news_author";
    private static final String NEWS_ID = "news_id";
    private List<News> newsList = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private TypedArray images;
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG", "Log调试");
        initData();
        newsAdapter = new NewsAdapter(MainActivity.this, R.layout.list_item, newsList);

        ListView lvNewsList = findViewById(R.id.lv_news_list);
        lvNewsList.setAdapter(newsAdapter);

        swipe = findViewById(R.id.swipe);

        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshData();
                    }
                });
    }

    private void initData() {
        int length;

        titles = getResources().getStringArray(R.array.titles);
        authors = getResources().getStringArray(R.array.authors);
        images = getResources().obtainTypedArray(R.array.images);

        if (titles.length > authors.length) {
            length = authors.length;
        } else {
            length = titles.length;
        }

        for (int i = 0; i < length; i++) {
            News news = new News();
            news.setmTitle(titles[i]);
            news.setmAuthor(authors[i]);
            news.setmImageId(images.getResourceId(i, 0));
            newsList.add(news);
        }
    }
    private void refreshData() {
        Random random = new Random();
        int index = random.nextInt(19);

        News news = new News();

        news.setmTitle(titles[index]);
        news.setmAuthor(authors[index]);
        news.setmImageId(images.getResourceId(index, 0));

        newsAdapter.add(news);
        newsAdapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
    }
}