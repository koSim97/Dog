package com.example.dog;

public class WeatherConstants {

    // Broadcast
    public static final String ACTION_COMMON_GPS_REQ = "com.ds.weather.gps_req";
    public static final String ACTION_COMMON_WEATHER_REQ = "com.ds.weather.weather_req";

    // Intent weather info
    public static final String ACTION_COMMON_WEATHER_SEND = "com.ds.weather.weather_info";
    public static final String EXTRA_COMMON_WEATHER_COUNTRY = "weather_country";
    public static final String EXTRA_COMMON_WEATHER_CITY = "weather_city";
    public static final String EXTRA_COMMON_WEATHER_ID = "weather_id";
    public static final String EXTRA_COMMON_WEATHER_ID_DESCRIPTION = "weather_description";
    public static final String EXTRA_COMMON_WEATHER_TEMP = "weather_temp";
    public static final String EXTRA_COMMON_WEATHER_HUMIDITY = "weather_humidity";
    public static final String EXTRA_COMMON_WEATHER_PRESSURE = "weather_pressure";
    public static final String EXTRA_COMMON_WEATHER_SPEED = "weather_speed";

    // Intent AQI info
    public static final String ACTION_COMMON_AQI_SEND = "com.ds.weather.aqi_info";
    public static final String EXTRA_COMMON_AQI_ID = "weather_aqi_id";
    public static final String EXTRA_COMMON_AQI_VAL = "weather_aqi_val";
    //  0 - 50 Good
    //  51 - 100 Moderate
    //  101 - 150 Unheath for
    //  151 - Bad

    // AQI ICON
    public static final int AQI_ICON_GOOD = 0;
    public static final int AQI_ICON_MODERATE = 1;
    public static final int AQI_ICON_BAD = 2;
    public static final int AQI_ICON_TOO_BAD = 3;

    // Weather ICON
    public static final int WEATHER_ICON_STORM = 0;                 // 200 ~ 232
    public static final int WEATHER_ICON_FOG = 1;                    // 300 ~ 321 , 701, 711, 731, 741, 761
    public static final int WEATHER_ICON_RAIN = 2;                   // 500, 501, 520, 521
    public static final int WEATHER_ICON_HAVY_RAIN = 3;             // 502, 503, 504, 522, 531
    public static final int WEATHER_ICON_SNOW = 4;                  // 600, 601, 602, 611, 612, 613
    public static final int WEATHER_ICON_SNOW_RAIN = 5;             // 511, 615, 616, 620, 621, 622
    public static final int WEATHER_ICON_SUNNY = 6;                     // 800(day)
    public static final int WEATHER_ICON_MOON = 7;                      // 800(night)
    public static final int WEATHER_ICON_CLOUD = 8;                     // 801 ~ 804



    // icon THUNDERSTORM
    public static final int WEATHER_CODE_THUNDERSTORM_WITH_LIGHT_RAIN = 200;
    public static final int WEATHER_CODE_THUNDERSTORM_WITH_RAIN = 201;
    public static final int WEATHER_CODE_THUNDERSTORM_WITH_HEAVY_RAIN = 202;
    public static final int WEATHER_CODE_THUNDERSTORM_LIGHT = 210;
    public static final int WEATHER_CODE_THUNDERSTORM = 211;
    public static final int WEATHER_CODE_THUNDERSTORM_HEAVY = 212;
    public static final int WEATHER_CODE_THUNDERSTORM_RAGGED = 221;
    public static final int WEATHER_CODE_THUNDERSTORM_WITH_LIGHT_DRIZZLE = 230;
    public static final int WEATHER_CODE_THUNDERSTORM_WITH_DRIZZLE = 231;
    public static final int WEATHER_CODE_THUNDERSTORM_WITH_HEAVY_DRIZZLE = 232;


    // icon DRIZZLE
    public static final int WEATHER_CODE_DRIZZLE_WITH_LIGHT_INTENSITY = 300;
    public static final int WEATHER_CODE_DRIZZLE = 301;
    public static final int WEATHER_CODE_DRIZZLE_WITH_HEAVY_INTENSITY = 302;
    public static final int WEATHER_CODE_DRIZZLE_WITH_LIGHT_INTENSITY_RAIN = 310;
    public static final int WEATHER_CODE_DRIZZLE_WITH_RAIN = 311;
    public static final int WEATHER_CODE_DRIZZLE_WITH_HEAVY_INTENSITY_RAIN = 312;
    public static final int WEATHER_CODE_DRIZZLE_WITH_SHOWER_RAIN = 313;
    public static final int WEATHER_CODE_DRIZZLE_WITH_HEAVY_SHOWER_RAIN = 314;
    public static final int WEATHER_CODE_DRIZZLE_SHOWER = 321;


    // icon RAIN
    public static final int WEATHER_CODE_RAIN_LIGHT = 500;
    public static final int WEATHER_CODE_RAIN_MODERATE = 501;
    public static final int WEATHER_CODE_RAIN_HEAVY = 502;
    public static final int WEATHER_CODE_RAIN_VERY_HEAVY = 503;
    public static final int WEATHER_CODE_RAIN_EXTREAM = 504;
    public static final int WEATHER_CODE_RAIN_WITH_FREEZING = 511;
    public static final int WEATHER_CODE_RAIN_LIGHT_SHOWER = 520;
    public static final int WEATHER_CODE_RAIN_SHOWER = 521;
    public static final int WEATHER_CODE_RAIN_HEAVY_SHOWER = 522;
    public static final int WEATHER_CODE_RAIN_RAGGED_SHOWER = 531;


    // icon snow
    public static final int WEATHER_CODE_SNOW_LIGHT = 600;
    public static final int WEATHER_CODE_SNOW = 601;
    public static final int WEATHER_CODE_SNOW_HEAVY = 602;
    public static final int WEATHER_CODE_SNOW_SLEET = 611;
    public static final int WEATHER_CODE_SNOW_LIGHT_SHOWER_SLEET = 612;
    public static final int WEATHER_CODE_SNOW_SHOWER_SLEET = 613;
    public static final int WEATHER_CODE_SNOW_WITH_LIGHT_RAIN = 615;
    public static final int WEATHER_CODE_SNOW_WITH_RAIN = 616;
    public static final int WEATHER_CODE_SNOW_LIGHT_SHOWER = 620;
    public static final int WEATHER_CODE_SNOW_SHOWER = 621;
    public static final int WEATHER_CODE_SNOW_HEAVY_SHOWER = 622;


    // icon ATMOSPHERE
    public static final int WEATHER_CODE_MIST = 701;
    public static final int WEATHER_CODE_SMOKE = 711;
    public static final int WEATHER_CODE_HAZE = 721;
    public static final int WEATHER_CODE_SAND_DUST = 731;
    public static final int WEATHER_CODE_FOG = 741;
    public static final int WEATHER_CODE_SAND = 751;
    public static final int WEATHER_CODE_DUST = 761;
    public static final int WEATHER_CODE_ASH = 762;
    public static final int WEATHER_CODE_SQUALL = 771;
    public static final int WEATHER_CODE_TORNADO = 781;

    // icon CLEAR
    public static final int WEATHER_CODE_CLEAR = 800;


    // icon CLOUDS
    // 11~25%
    public static final int WEATHER_CODE_CLOUDS_FEW = 801;
    // 25~50%
    public static final int WEATHER_CODE_CLOUDS_SCATTERED = 802;
    // 51~84%
    public static final int WEATHER_CODE_CLOUDS_BROKEN = 803;
    // 85~100%
    public static final int WEATHER_CODE_CLOUDS_OVERCAST = 804;
}
