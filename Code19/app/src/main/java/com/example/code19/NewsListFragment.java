package com.example.code19;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.code19.News;
import com.example.code19.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends Fragment {

    private Context context = null;

    private String[] titles = null;
    private String[] authors = null;
    private String[] contents = null;
    private TypedArray images;

    private List<News> newsList = new ArrayList<>();

    private NewsAdapter newsAdapter = null;
    private ListView lvNewsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_news_list,
                container, false);

        lvNewsList = rootView.findViewById(R.id.lv_news_list);
        newsAdapter = new NewsAdapter(context,
                R.layout.list_item,
                newsList);
        lvNewsList.setAdapter(newsAdapter);

        return rootView;
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