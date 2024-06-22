package com.dipak.slotbookingapplication;

import java.util.ArrayList;

public class SlotModel {

    String time;

    ArrayList<String> timeList;

    public SlotModel(String time, ArrayList<String> timeList) {
        this.time = time;
        this.timeList = timeList;
    }

    public String getTime() {
        return time;
    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }
}
