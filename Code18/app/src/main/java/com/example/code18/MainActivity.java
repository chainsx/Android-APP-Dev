package com.example.code18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String NEWS_ID = "news_id";
    private List<News> newsList = new ArrayList<>();
    private String[] titles;
    private TypedArray images;
    private String[] authors;
    private NewsAdapter newsAdapter = null;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.lv_news_list);
        initData();

        newsAdapter = new NewsAdapter(
                MainActivity.this,
                R.layout.list_item,
                newsList
        );
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(newsAdapter);
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
}