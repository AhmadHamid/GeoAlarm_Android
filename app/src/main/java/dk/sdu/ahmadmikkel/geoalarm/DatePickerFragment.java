package dk.sdu.ahmadmikkel.geoalarm;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalTime;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //TODO: is24HourFormat er hack. Kig på det.
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //TODO: Ændr 17:5 til 17:05
        Log.d("MUGGEL", "onTimeSet: " + hourOfDay + " - " + minute);

        LocalTime time = LocalTime.of(hourOfDay, minute);

        ((AddAlarmActivity) getActivity()).setTime(time);
    }
}
