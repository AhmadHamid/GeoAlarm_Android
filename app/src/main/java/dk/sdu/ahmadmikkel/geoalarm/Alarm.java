package dk.sdu.ahmadmikkel.geoalarm;

public class Alarm {
    private int hour;
    private int minute;
    private String label;
    private String location;

    //Test variable
    private String time;

    public Alarm() {
    }

    //Test contructor
    public Alarm(String time, String label) {
        this.time = time;
        this.label = label;
    }

    public Alarm(int hour, int minute, String label, String location) {
        this.hour = hour;
        this.minute = minute;
        this.label = label;
        this.location = location;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
