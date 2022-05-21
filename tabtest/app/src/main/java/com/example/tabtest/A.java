package com.example.tabtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link D#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMDER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView lvNewsList;
    private NewsAdapter adapter;
    private int mCurrentColIndex = 0;
    private int[] mCols = new int[]{5,
            7, 8,
            10, 11};
    private View rootView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context = null;
    private NewsAdapter newsAdapter = null;
    private int page=1;
    private List<News> newsData;
    private RefreshLayout swipe;
    public A() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment D.
     */
    // TODO: Rename and change types and number of parameters
    public static D newInstance(String param1, String param2) {
        D fragment = new D();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();

        rootView = inflater.inflate(R.layout.fragment_b,
                container, false);

        initView();

        initData();

        return rootView;
    }

    private okhttp3.Callback callback = new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
//            Log.e(TAG, "Failed to connect server!");
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response)
                throws IOException {
            if (response.isSuccessful()) {
                final String body = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Type jsonType =
                                new TypeToken<BaseResponse<List<News>>>() {}.getType();
                        BaseResponse<List<News>> newsListResponse =
                                gson.fromJson(body, jsonType);
                        for (News news:newsListResponse.getData()) {
                            adapter.add(news);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            } else {
            }
        }
    };

    private void refreshData(final int page) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                NewsRequest requestObj = new NewsRequest();

                requestObj.setCol(mCols[mCurrentColIndex]);
                requestObj.setNum(Constants.getNewsNum());
                requestObj.setPage(page);
                String urlParams = requestObj.toString();

                Request request = new Request.Builder()
                        .url(Constants.getServerUrl()+Constants.getAllNewsPath()[0] + urlParams)
                        .get().build();
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(callback);
                } catch (NetworkOnMainThreadException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        lvNewsList = rootView.findViewById(R.id.lv_news_list);

        lvNewsList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int i, long l) {

                        Intent intent = new Intent(context,
                                Detail.class);

                        News news = adapter.getItem(i);
                        intent.putExtra("url",
                                news.getContentUrl());

                        startActivity(intent);
                    }
                });
        swipe = rootView.findViewById(R.id.swipe);

        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        page=1;
                        initData();
                        swipe.setRefreshing(false);
                    }
                });

        swipe.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page=page+1;
                refreshData(page);
                swipe.setLoading(false);
            }
        });
    }

    private void initData() {
        refreshData(page);

        newsData = new ArrayList<>();

        adapter = new NewsAdapter(context,
                R.layout.list_item, newsData);
        lvNewsList.setAdapter(adapter);
    }
}