package cz.uhk.fim.sportstracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.uhk.fim.sportstracker.Models.Activity;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private List<Activity> activities;

    public ActivityAdapter(List<Activity> activities){
        this.activities = activities;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity,null);
        ActivityViewHolder viewHolder = new ActivityViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity activity = activities.get(position);
        holder.setActivity(activity);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder{
        private TextView txtDate;
        private TextView txtDistance;


        public ActivityViewHolder(View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtDistance =  (TextView) itemView.findViewById(R.id.txtDistance);
        }


        public void setActivity(final Activity activity){
            txtDate.setText(activity.getDateString());
            txtDistance.setText(" " + Double.toString(activity.getDistance()));
        }
    }
}
