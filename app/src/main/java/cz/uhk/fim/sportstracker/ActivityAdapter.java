package cz.uhk.fim.sportstracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
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
        private Button btnDelete;
        private Button btnDetail;
        private int id;


        public ActivityViewHolder(final View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtDistance =  (TextView) itemView.findViewById(R.id.txtDistance);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            btnDetail = (Button) itemView.findViewById(R.id.btnDetail);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: add delete function !!!
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext() ,DetailActivity.class);
                    intent.putExtra("id", id);
                    itemView.getContext().startActivity(intent);
                }
            });
        }


        public void setActivity(final Activity activity){
            txtDate.setText(activity.getDateString());
            DecimalFormat df = new DecimalFormat("####0.00");
            txtDistance.setText(" " + df.format(activity.getTotalDistance()) + " km");
            id = activity.getId();

        }

    }
}
