package dk.sdu.ahmadmikkel.geoalarm;

import android.location.Location;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RequiresApi;
import com.google.type.LatLng;

import java.time.LocalTime;

public class Alarm implements Parcelable {
    //private LocalTime time;
    private int hour;
    private int minute;

    private String label;
    private double longitude;
    private double latitude;
    private Boolean isActivate;

    public Alarm() {
    }

    //Test contructor

    public Alarm(String time, String label, double longitude, double latitude) {
        this.hour = 0;
        this.minute = 0;
        this.time = time;
        this.label = label;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isActivate = true;
    }

    public Alarm(int hour, int minute, String label, double longitude, double latitude) {
        this.hour = hour;
        this.minute = minute;
        this.label = label;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isActivate = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Alarm(Parcel in) {
        hour = in.readInt();
        minute = in.readInt();

        label = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
/*        byte tmpIsActivate = in.readByte();
        isActivate = tmpIsActivate == 0 ? null : tmpIsActivate == 1;*/
        isActivate = in.readBoolean();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Boolean getActivate() {
        return isActivate;
    }

    public void setActivate(Boolean activate) {
        isActivate = activate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hour);
        dest.writeInt(minute);

        dest.writeString(label);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);

/*        if (isActivate) {
            dest.writeByte((byte) 1);
        } else {
            dest.writeByte((byte) 0);
        }*/

        dest.writeBoolean(isActivate);
    }
}
