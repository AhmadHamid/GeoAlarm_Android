package dk.sdu.ahmadmikkel.geoalarm;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //TODO: Ændr 17:5 til 17:05
        Log.d("MUGGEL", "onTimeSet: " + hourOfDay + " - " + minute);

        ((AddAlarmActivity) getActivity()).setTime(hourOfDay, minute);
    }
}
