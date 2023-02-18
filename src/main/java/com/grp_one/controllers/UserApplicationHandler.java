package com.grp_one.controllers;

import java.util.HashMap;
import java.util.Map.Entry;

import com.grp_one.SqlConnector;

public class UserApplicationHandler {

    private static final SqlConnector dbConn = new SqlConnector();
    private static HashMap<String, Object> userInfo = null;

    public static void submitInfo(HashMap<String, Object> submittedInfo) {
        userInfo = submittedInfo;
    }

    public static void clearInfo() {
        userInfo = null;
    }

    public static void uploadInfo() {
        if (userInfo == null)
            System.out.println("No information");
        else {
            for (Entry<String, Object> entry : userInfo.entrySet()) {
                System.out.println(entry.getKey() + " " + userInfo.get(entry.getKey()));
            }
        }
    }
}
