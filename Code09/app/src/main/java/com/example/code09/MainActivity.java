package com.example.code09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] titles = null;
    private String[] authors = null;
    private List<Map<String, String>> dataList = new ArrayList<>();
    private static final String NEWS_TITLE = "news_title";
    private static final String NEWS_AUTHOR = "news_author";
    private static final String NEWS_ID = "news_id";
    private List<News> newsList = new ArrayList<>();
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG", "Log调试");
        initData();
        ListView lvNewsList = findViewById(R.id.lv_news_list);

        MyDbOpenHelper myDbOpenHelper = new MyDbOpenHelper(MainActivity.this);
        db = myDbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(
                NewsContract.NewsEntry.TABLE_NAME,
                null, null, null, null, null, null);

        List<News> newsList = new ArrayList();
        int titleIndex = cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NAME_TITLE);
        int authorIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.COLUMN_NAME_AUTHOR);
        int imageIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.COLUMN_NAME_IMAGE);

        while (cursor.moveToNext()) {
            News news = new News();
            String title = cursor.getString(titleIndex);
            String author = cursor.getString(authorIndex);
            String image = cursor.getString(imageIndex);

            Bitmap bitmap = BitmapFactory.decodeStream(
                    getClass().getResourceAsStream("/" + image));

            news.setmTitle(title);
            news.setmAuthor(author);
            newsList.add(news);

            NewsAdapter newsAdapter = new NewsAdapter(
                    MainActivity.this,
                    R.layout.list_item,
                    newsList);
            lvNewsList.setAdapter(newsAdapter);
        }
    }

    private void initData() {
        int length;

        titles = getResources().getStringArray(R.array.titles);
        authors = getResources().getStringArray(R.array.authors);
        TypedArray images = getResources().obtainTypedArray(R.array.images);

        if (titles.length > authors.length) {
            length = authors.length;
        } else {
            length = titles.length;
        }

        for (int i = 0; i < length; i++) {
            News news = new News();
            news.setmTitle(titles[i]);
            news.setmAuthor(authors[i]);
            news.setmImageId(images.getResourceId(0, 0));
            newsList.add(news);
        }
    }
}