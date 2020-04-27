package dk.sdu.ahmadmikkel.geoalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Alarm> alarmList;
    int[] images;
    Context context;

    public RecyclerAdapter(Context ct, ArrayList<Alarm> alarmList, int[] img) {
        context = ct;
        this.alarmList = alarmList;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        /*holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);*/
        holder.myText1.setText(alarmList.get(position).getTime());
        holder.myText2.setText(alarmList.get(position).getLabel());
        holder.myImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.myText1);
            myText2 = itemView.findViewById(R.id.myText2);
            myImage = itemView.findViewById(R.id.myImageView);
        }
    }
}
