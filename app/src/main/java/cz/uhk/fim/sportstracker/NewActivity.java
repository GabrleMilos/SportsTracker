package cz.uhk.fim.sportstracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.speech.tts.TextToSpeech;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.uhk.fim.sportstracker.Database.DatabaseHelper;
import cz.uhk.fim.sportstracker.Models.Activity;
import cz.uhk.fim.sportstracker.Models.Position;

public class NewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextToSpeech mTTS;
    private Activity activity;
    private List<Position> positions;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean isActive;
    private DatabaseHelper databaseHelper;
    //    overall data
    private double averagePace;
    private double averageDistance;

    private int userId;

    @BindView(R.id.btnStartActivity)
    public Button btnStart;
    @BindView(R.id.btnStopActivity)
    public Button btnStop;
    @BindView(R.id.btnSaveActivity)
    public Button btnSave;


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
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    mTTS.setPitch(1);
                    mTTS.setSpeechRate(1);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not supported");
                    }else{
                        Log.i("TTS", "Initialization successfull");
                    }
                }else{
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        positions = new ArrayList<Position>();
        activity = new Activity(positions, new Date());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        databaseHelper = new DatabaseHelper(this);
        isActive = false;
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        List<Activity> activities = databaseHelper.getUserActivities(userId);
        if(!activities.isEmpty()){
            double sumPace = 0;
            double sumDistance = 0;
            for (Activity a: activities) {
                sumDistance += a.getTotalDistance();
                sumPace += a.getTotalPaceInMinutes();
            }
            averagePace = sumPace/ activities.size();
            averageDistance = sumDistance / activities.size();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void speakTheTextQueueAdd(String str){
        mTTS.speak(str, TextToSpeech.QUEUE_ADD, null);
    }

    public void speakTheTextQueueFlush(String str){
        mTTS.speak(str, TextToSpeech.QUEUE_FLUSH, null);
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


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakTheTextQueueFlush("starting activity");
                isActive = true;
                btnSave.setVisibility(View.INVISIBLE);
                btnStop.setVisibility(View.VISIBLE);
                btnStart.setTextColor(getResources().getColor(R.color.colorGreen));
                btnStop.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused));
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActive = false;
                btnStop.setTextColor(getResources().getColor(R.color.colorRed));
                btnStart.setTextColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_focused));
                if (positions.size() >= 2) {
                    btnSave.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                long id = databaseHelper.insertActivity(activity, userId);
                returnIntent.putExtra("activityId", id);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (isActive) {
                    LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
                    Position p = new Position(myCoordinates.latitude, myCoordinates.longitude, new Date());
                    positions.add(p);
                    activity.setPositionList(positions);
                    if (positions.size() >= 2) {

                        Position startPos = positions.get(positions.size() - 2);
                        Position endPos = positions.get(positions.size() - 1);
                        LatLng start = new LatLng(startPos.getLat(), startPos.getLng());
                        LatLng end = new LatLng(endPos.getLat(), endPos.getLng());
                        mMap.addPolyline(new PolylineOptions()
                                .add(start, end)
                                .width(10)
                                .color(R.color.colorBlack)
                        );
                    }
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(p.getLat(), p.getLng()), 15.0f));
                    txtDateValue.setText(activity.getDateString());
                    txtDistanceValue.setText(activity.getTotalDistanceString() + " km");
                    txtPaceValue.setText(activity.getTotalPaceInMinutesString() + " min/km");
                    txtTimeValue.setText(activity.getTotalTimeString());
                    double dst = activity.getTotalDistance();
                    if(dst % 1 <=  0.3 && dst > 0){
                        readAcivityFeedback();
                    }
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 500, 10, locationListener);
    }

    private void readAcivityFeedback() {
        DecimalFormat df2 = new DecimalFormat(".##");
        String distanceText = "";
        if(activity.getTotalDistance() < averageDistance){
            distanceText = "You have " + df2.format(averageDistance - activity.getTotalDistance()) + " kilometers left to achieve your average distance" ;
        }
        else  if(activity.getTotalDistance() > averageDistance) {
            distanceText =    "You have reached your average distance " + df2.format(activity.getTotalDistance() - averageDistance) + " kilometers ago" ;
        } else  {
            distanceText =    "You have reached your average distance, which is " + df2.format(averageDistance )+ " kilometers" ;
        }
        speakTheTextQueueAdd(distanceText);


        String paceText = "";
        if(activity.getTotalPaceInMinutes() < averagePace){
            paceText = "Your current pace is " + df2.format(averagePace - activity.getTotalPaceInMinutes()) + " minutes per kilometer faster than your average pace";
        }else if (activity.getTotalPaceInMinutes() > averagePace){
            paceText = "Your current pace is " + df2.format(activity.getTotalPaceInMinutes() - averagePace) + " minutes per kilometer slower than your average pace";
        }else{
            paceText = "Your current pace is the same as your average pace, which is " + df2.format(averagePace )+ " minutes per kilometer" ;
        }
        speakTheTextQueueAdd(paceText);

    }

}
