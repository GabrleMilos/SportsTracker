package cz.uhk.fim.sportstracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.uhk.fim.sportstracker.Database.DatabaseHelper;
import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.Position;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper databaseHelper;
    private Activity activity;

    @BindView(R.id.txtTimeValue)
    public TextView txtTimeValue;
    @BindView(R.id.txtDateValue)
    public TextView txtDateValue;
    @BindView(R.id.txtPaceValue)
    public TextView txtPaceValue;
    @BindView(R.id.txtDistanceValue)
    public TextView txtDistanceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        databaseHelper = new DatabaseHelper(this);
        int id = intent.getIntExtra("id", 0);
        if (id == 0) {
            finish();
        }
        activity = databaseHelper.getActivity(id);
        txtDateValue.setText(activity.getDateString());
        txtDistanceValue.setText(activity.getTotalDistanceString() + " km");
        txtPaceValue.setText(activity.getTotalPaceInMinutesString() + " min/km");
        txtTimeValue.setText(activity.getTotalTimeString());
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = (new LatLng(0, 0));
//        for (Position p : positionList) {
//            latLng = new LatLng(p.getLat(), p.getLng());
//            mMap.addMarker(new MarkerOptions().position(latLng).title(p.getDate().toString()));
//        }
        double pace = activity.getTotalPaceInMinutes();

        for (int i = 0; i < activity.getPositionList().size() - 1; i++) {
            Position start = activity.getPositionList().get(i);
            Position end = activity.getPositionList().get(i + 1);
            if (i == 0) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(start.getLat(), start.getLng())).title(getString(R.string.markerStart) + " - " + start.getDateString()));
            }
            if (i == activity.getPositionList().size() - 2) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(end.getLat(), end.getLng())).title(getString(R.string.markerEnd) + " - " + end.getDateString()));
            }

            double localPace = activity.getPaceBetween(i);
            int color = Color.YELLOW;
            if (localPace > pace * 1.1) {
                color = Color.RED;
            }
            if (localPace < pace * 0.9) {
                color = Color.GREEN;
            }
            mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(start.getLat(), start.getLng()), new LatLng(end.getLat(), end.getLng()))
                    .width(10)
                    .color(color)
            );
            latLng = new LatLng(end.getLat(), end.getLng());
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 15.0f));
    }
}
