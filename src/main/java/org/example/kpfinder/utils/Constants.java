package org.example.kpfinder.utils;

import java.util.List;

public class Constants {

    public static String driverName = "webdriver.chrome.driver";

    public static String driverPath = "C:\\chromedriver-win64\\chromedriver.exe";

    public static String baseUrl = "https://www.google.com/";

    public static List<String> hrefs = List.of
            (
                    "//*[@id=\"kp-wp-tab-overview\"]/div[4]",
                    "//*[@id=\"kp-wp-tab-overview\"]/div[3]",
                    "//*[@id=\"rso\"]/div[1]",
                    "//*[@id=\"rso\"]/div[2]",
                    "//*[@id=\"rso\"]/div[3]",
                    "//*[@id=\"rso\"]/div[4]",
                    "//*[@id=\"rso\"]/div[5]",
                    "//*[@id=\"kp-wp-tab-overview\"]/div[2]",
                    "//*[@id=\"kp-wp-tab-overview\"]/div[4]",
                    "//*[@id=\"kp-wp-tab-overview\"]/div[5]",
                    "//*[@id=\"kp-wp-tab-overview\"]/div[6]",
                    "//*[@id=\"kp-wp-tab-overview\"]/div[7]",
                    "//*[@id=\"rso\"]/div[9]"
            );

}
