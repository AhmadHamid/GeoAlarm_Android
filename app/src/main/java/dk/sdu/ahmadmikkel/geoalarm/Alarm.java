package dk.sdu.ahmadmikkel.geoalarm;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Alarm implements Parcelable {
    private int hour;
    private int minute;
    private String label;
    private String location = "nullString";
    private Boolean isActivate = false;

    //Test variable
    private String time;

    public Alarm() {
    }

    //Test contructor
    public Alarm(String time, String label) {
        this.hour = 0;
        this.minute = 0;
        this.time = time;
        this.label = label;
    }

    public Alarm(int hour, int minute, String label, String location) {
        this.hour = hour;
        this.minute = minute;
        this.label = label;
        this.location = location;
    }

    protected Alarm(Parcel in) {
        hour = in.readInt();
        minute = in.readInt();
        label = in.readString();
        location = in.readString();
/*        byte tmpIsActivate = in.readByte();
        isActivate = tmpIsActivate == 0 ? null : tmpIsActivate == 1;*/
        time = in.readString();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

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

    public Boolean getActivate() {
        return isActivate;
    }

    public void setActivate(Boolean activate) {
        isActivate = activate;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeString(label);
        dest.writeString(location);

/*        if (isActivate) {
            dest.writeByte((byte) 1);
        } else {
            dest.writeByte((byte) 0);
        }*/

        dest.writeString(time);

    }
}
