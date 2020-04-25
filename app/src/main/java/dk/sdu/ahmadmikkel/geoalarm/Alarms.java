package dk.sdu.ahmadmikkel.geoalarm;

import java.util.ArrayList;

public class Alarms {
    private static Alarms instance = null;

    private ArrayList<Alarm> alarmList;

    private Alarms() {
        alarmList = new ArrayList<>();
    }

    public static Alarms getInstance() {
        if (instance == null) {
            instance = new Alarms();
        }
        return instance;
    }

    public boolean createAlarm() {
        //TODO: Lav Alarm og sæt i alarmList
        return false;
    }

    public ArrayList<Alarm> getAlarmList() {
        return alarmList;
    }
}