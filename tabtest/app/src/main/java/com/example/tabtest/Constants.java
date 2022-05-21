package com.example.tabtest;

public final class Constants {
    public Constants() {
    }

    private static final int NEWS_NUM = 10;
    private static String SERVER_URL = "http://api.tianapi.com/";
    private static String[] ALL_NEWS_PATH= {"generalnews/","综合新闻"};
    private static String[] GENERAL_NEWS_PATH1 = {"internet/","互联网资讯"};
    private static String[] GENERAL_NEWS_PATH2 = {"film/","影视资讯"};
    private static String[] GENERAL_NEWS_PATH3= {"esports/","电竞资讯"};
    private static String[] GENERAL_NEWS_PATH4 = {"bulletin/","每日简报"};

    private static String API_KEY = "618ebf3e6fa8721b946b0a68c06d4b0f";

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getServerUrl() {
        return SERVER_URL;
    }

    public static String[] getAllNewsPath() {
        return ALL_NEWS_PATH;
    }

    public static String[] getGeneralNewsPath1() {
        return GENERAL_NEWS_PATH1;
    }

    public static String[] getGeneralNewsPath2() {
        return GENERAL_NEWS_PATH2;
    }

    public static String[] getGeneralNewsPath3() {
        return GENERAL_NEWS_PATH3;
    }

    public static String[] getGeneralNewsPath4() {
        return GENERAL_NEWS_PATH4;
    }

    public static int getNewsNum() {
        return NEWS_NUM;
    }

    private static int NEWS_COL5 = 5;
    private static int NEWS_COL7 = 7;
    private static int NEWS_COL8 = 8;
    private static int NEWS_COL10 = 10;
    private static int NEWS_COL11 = 11;

    private static String NEWS_DETAIL_URL_KEY = "news_detail_url_key";

    public static String getNewsDetailUrlKey() {
        return NEWS_DETAIL_URL_KEY;
    }
}