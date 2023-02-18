package com.grp_one.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map.Entry;

import com.grp_one.SqlConnector;
import com.grp_one.controllers.Bot.ChatContextProvider;

public class UserApplicationHandler {

    private static final SqlConnector dbConn = new SqlConnector();
    private static HashMap<String, Object> userInfo = null;

    private static int sessionUID;
    private static Date dateOfTransaction;

    public static void submitInfo(HashMap<String, Object> submittedInfo) {
        userInfo = submittedInfo;
    }

    public static void setSessionUID(int UID) {
        sessionUID = UID;
    }

    public static int getSessionUID() {
        return sessionUID;
    }

    public static void clearInfo() {
        userInfo = null;
    }

    public static void uploadInfo() {
        if (userInfo == null)
            System.out.println("No information");
        else {
            dateOfTransaction = new Date(System.currentTimeMillis());
            for (Entry<String, Object> entry : userInfo.entrySet()) {
                System.out.println(entry.getKey() + " " + userInfo.get(entry.getKey()));
            }
            try {
                Connection conn = dbConn.dbConn();
                String locationData = "INSERT INTO admin.user_address VALUES(?,?,?,?,?,?,?,?)";
                String personalData = "INSERT INTO admin.user_personal_info VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                String applicationData = "INSERT INTO admin.application_status VALUES(?,?,?,?)";
                String deleteApplicationIfExists = "DELETE FROM admin.application_status WHERE admin.application_status.userID = ?";
                PreparedStatement stmt = conn.prepareStatement(deleteApplicationIfExists);
                stmt.setInt(1, sessionUID);
                stmt.executeUpdate();

                stmt = conn.prepareStatement(applicationData);
                stmt.setInt(1, sessionUID);
                stmt.setDate(2, dateOfTransaction);
                stmt.setString(3, CustomerInfo.PROCESS);
                stmt.setString(4,
                        (new StringBuilder(String.valueOf(sessionUID * System.currentTimeMillis())).reverse()
                                .toString()).substring(0, 9) + String.valueOf(dateOfTransaction));
                stmt.executeUpdate();

                stmt = conn.prepareStatement(personalData);
                stmt.setInt(1, sessionUID);
                Date date = Date.valueOf((LocalDate) userInfo.get(ChatContextProvider.BDAY));
                stmt.setDate(2, date);
                stmt.setString(3, (userInfo.get(ChatContextProvider.SEX)).toString());
                stmt.setString(4, (String) userInfo.get(ChatContextProvider.BTYPE));
                stmt.setString(5, (String) userInfo.get(ChatContextProvider.FILoALIEN));
                stmt.setString(6, (String) userInfo.get(ChatContextProvider.MARITAL));
                stmt.setString(7, (String) userInfo.get(ChatContextProvider.MNAME));
                stmt.setString(8, (String) userInfo.get(ChatContextProvider.LNAME));
                stmt.setString(9, (String) userInfo.get(ChatContextProvider.FNAME));
                stmt.setString(10, (String) userInfo.get(ChatContextProvider.CITY2));
                stmt.setString(11, (String) userInfo.get(ChatContextProvider.PROVINCE2));
                stmt.setString(12, (String) userInfo.get(ChatContextProvider.COUNTRY2));
                stmt.setString(13, (String) userInfo.get(ChatContextProvider.CONTACT));
                stmt.executeUpdate();
                stmt = conn.prepareStatement(locationData);
                stmt.setInt(1, sessionUID);
                stmt.setString(2, (String) userInfo.get(ChatContextProvider.ROOM));
                stmt.setString(3, (String) userInfo.get(ChatContextProvider.HOUSE));
                stmt.setString(4, (String) userInfo.get(ChatContextProvider.STRT));
                stmt.setString(5, (String) userInfo.get(ChatContextProvider.SUBDIV));
                stmt.setString(6, (String) userInfo.get(ChatContextProvider.CITY2));
                stmt.setString(7, (String) userInfo.get(ChatContextProvider.PROVINCE2));
                stmt.setString(8, (String) userInfo.get(ChatContextProvider.COUNTRY2));
                stmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
