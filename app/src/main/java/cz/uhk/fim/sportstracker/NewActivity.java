package cz.uhk.fim.sportstracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.uhk.fim.sportstracker.Models.Position;

public class NewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Position> positions;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @BindView(R.id.btnStartActivity)
    public Button btnStart;
    @BindView(R.id.btnStopActivity)
    public Button btnStop;
    @BindView(R.id.txtTimeValue)
    public TextView txtTimeValue;
    @BindView(R.id.txtDistanceValue)
    public TextView txtDistanceValue;
    @BindView(R.id.txtPaceValue)
    public TextView txtPaceValue;
    @BindView(R.id.txtDateValue)
    public TextView txtDateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        positions = new ArrayList<Position>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
                Position p = new Position(myCoordinates.latitude, myCoordinates.longitude, new Date());
                positions.add(p);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
                Log.e("POSITION", "Pos changed");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);


        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.i("LOCATION", "insideCode");
        txtTimeValue.setText(String.valueOf( loc.getLatitude()) + " " + String.valueOf( loc.getLongitude()));

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
    }

}
