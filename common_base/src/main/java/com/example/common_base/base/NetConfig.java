package com.example.common_base.base;

public class NetConfig {
    public static String BaseUrl;
    public static int API_TYPE = 1;//1:正式服务器 2：外测服务器 3：内测服务器
    public static String BASE_URL2 = "http://sport-data.dqdgame.com/";

    static {
        if (API_TYPE == 1) BaseUrl = "https://bkbapi.dqdgame.com/";
        else if (API_TYPE == 2) BaseUrl = "";
        else BaseUrl = "";
    }
}
