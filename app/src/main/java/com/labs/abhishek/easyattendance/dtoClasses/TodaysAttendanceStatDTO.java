package com.labs.abhishek.easyattendance.dtoClasses;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anand on 4/9/16.
 */
public class TodaysAttendanceStatDTO {

    private Map<Integer, Integer> rollVsAttendanceValueMap;

    private int[] presentVsAbsentArray;

    private String response = "/";

    private Map<String, String> membersNameVsAttendanceValueMap;

    public Map<String, String> getMembersNameVsAttendanceValueMap() {
        return membersNameVsAttendanceValueMap;
    }

    public void setMembersNameVsAttendanceValueMap(Map<String, String> membersNameVsAttendanceValueMap) {
        this.membersNameVsAttendanceValueMap = membersNameVsAttendanceValueMap;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public TodaysAttendanceStatDTO() {
        rollVsAttendanceValueMap = new HashMap<Integer, Integer>();
        presentVsAbsentArray = new int[2];
        membersNameVsAttendanceValueMap = new HashMap<String, String>();
    }

    public Map<Integer, Integer> getRollVsAttendanceValueMap() {
        return rollVsAttendanceValueMap;
    }

    public void setRollVsAttendanceValueMap(Map<Integer, Integer> rollVsAttendanceValueMap) {
        this.rollVsAttendanceValueMap = rollVsAttendanceValueMap;
    }

    public int[] getPresentVsAbsentArray() {
        return presentVsAbsentArray;
    }

    public void setPresentVsAbsentArray(int[] presentVsAbsentArray) {
        this.presentVsAbsentArray = presentVsAbsentArray;
    }
}
