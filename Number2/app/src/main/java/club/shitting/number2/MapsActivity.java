package club.shitting.number2;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements View.OnClickListener, GoogleMap.OnMapLongClickListener {

    //Google's Variables
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GPSTracker gps;
    private double latitude = 0;
    private double longitude = 0;
    private ArrayList<Bathroom> bathroomList = null;
    private Marker customMarker = null;

    //Our Variables
    Button addLocation;
    Button searchButton;
    Button rateLocation;
    Button legendButton;
    EditText mainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Our Code Starts Here
        bathroomList = new ArrayList<Bathroom>();

        gps = new GPSTracker(MapsActivity.this);



        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        setUpMapIfNeeded();

        //Our Code Starts Here

        mMap.setOnMapLongClickListener(this);

        mainEditText = (EditText) findViewById(R.id.main_edittext);

        addLocation = (Button) findViewById(R.id.add_Location);
        addLocation.setOnClickListener(this);

        rateLocation = (Button) findViewById(R.id.rate_Location);
        rateLocation.setOnClickListener(this);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        legendButton = (Button) findViewById(R.id.legend_button);
        legendButton.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("GPS Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.sample2)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        try {
            updateMap();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {

        }
    }

    @Override
    public void onClick(View v)
    {

        if (v == addLocation) {
            Intent myIntent = new Intent(v.getContext(), AddActivity.class);
            startActivityForResult(myIntent, 0);
        }

        if(v == rateLocation)
        {Toast.makeText(this, "Rate", Toast.LENGTH_LONG).show();}

        if(v == legendButton)
        {
            Toast toast = new Toast(this);
            ImageView view = new ImageView(this);
            view.setImageResource(R.drawable.toast_img);
            view.setMaxWidth(600);
            view.setAdjustViewBounds(true);
            toast.setView(view);
            toast.setGravity(Gravity.TOP,0,200);
            toast.show();
        }

        if (v == searchButton)
        {Toast.makeText(this,"search",Toast.LENGTH_LONG).show();}
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (customMarker != null) {
            customMarker.remove();
        }
        customMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Custom Point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    public void updateMap() throws IOException {

        try {

            readFile();
        } catch (IOException e) {
            Toast.makeText(this,"Please find a place to connect",Toast.LENGTH_LONG);
        }

        MarkerOptions marker;
        Bathroom bathroom;
        for (int i = 0; i < bathroomList.size(); i++) {
            bathroom = bathroomList.get(i);
            marker = new MarkerOptions();
            marker.title(bathroom.getName());
            marker.position(bathroom.getLocation());

            switch(bathroom.getType()) {
                case Bathroom.FASTFOOD:     marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)); break;
                case Bathroom.RESTAURANT:   marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)); break;
                case Bathroom.STORE:        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)); break;
                case Bathroom.HOTEL:        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)); break;
                case Bathroom.SCHOOL_PARK:  marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)); break;
                case Bathroom.GAS:          marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)); break;
                case Bathroom.MISC:         marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)); break;
            }

            mMap.addMarker(marker.visible(true));
        }


    }

    public void readFile() throws IOException {

        Bathroom tempRoom;
        String name = "";
        double latitude = 0;
        double longitude = 0;
        int type = 0;
        int rating = 0;
        boolean alwaysOpen = false;

        String[] line;
        String data = "";
        InputStream is = this.getResources().openRawResource(R.drawable.shittyspots);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if (is != null) {
            try {
                while ((data = reader.readLine()) != null) {
                    line = data.split(",");
                    name = line[0];
                    latitude = Double.parseDouble(line[1]);
                    longitude = Double.parseDouble(line[2]);
                    type = Integer.parseInt(line[3]);
                    rating = Integer.parseInt(line[4]);
                    alwaysOpen = line[5].compareTo("true") == 0 ? true : false;

                    tempRoom = new Bathroom(name, alwaysOpen, rating, type, new LatLng(latitude,longitude));

                    bathroomList.add(tempRoom);

                }
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
