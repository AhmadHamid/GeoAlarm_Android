package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Alarm> alarmList;
    Context context;

    public RecyclerAdapter(Context ct, ArrayList<Alarm> alarmList) {
        context = ct;
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        /*holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);*/
        holder.myText1.setText(LocalTime.of(alarmList.get(position).getHour(), alarmList.get(position).getMinute()).toString());
        holder.myText2.setText(alarmList.get(position).getLabel());
        holder.onOffSwitch.setChecked(alarmList.get(position).getActivate());

        holder.myRowLayout.findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAlarmActivity.class);
                intent.putExtra("alarm", alarmList.get(position));
                intent.putExtra("pos", position);
                context.startActivity(intent);
            }
        });

        sortAlarmList();
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;
        ImageView myImage;
        ConstraintLayout myRowLayout;
        Switch onOffSwitch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.myText1);
            myText2 = itemView.findViewById(R.id.myText2);
            myImage = itemView.findViewById(R.id.myImageView);
            onOffSwitch = itemView.findViewById(R.id.onOffSwitch);
            myRowLayout = itemView.findViewById(R.id.myRowLayout);
        }
    }

    private void sortAlarmList() {
        Collections.sort(alarmList, new Comparator<Alarm>() {
            @Override
            public int compare(Alarm o1, Alarm o2) {
                if (o1.getHour() > o2.getHour()) {
                    return 1;
                } else if (o1.getHour() < o2.getHour()) {
                    return -1;
                } else {
                    if (o1.getMinute() > o2.getMinute()) {
                        return 1;
                    }
                }
                return -1;
            }
        });
    }
}
