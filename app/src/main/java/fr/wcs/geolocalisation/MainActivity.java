package fr.wcs.geolocalisation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        checkPermission();
    }

    LocationManager mLocationManager = null;

    @SuppressLint("MissingPermission")
    public void initLocation() {
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                location.getLongitude();
                location.getLatitude();

                String position = "Your position is" + location.getLatitude() + location.getLongitude();

                // effectuer une action ici !

                Toast.makeText(MainActivity.this, position, Toast.LENGTH_SHORT).show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {

            }
        };
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void checkPermission() {

        // vérification de l'autorisation d'accéder à la position GPS

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // l'autorisation n'est pas acceptée

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // l'autorisation a été refusée précédemment, on peut prévenir l'utilisateur ici

            } else {

                // l'autorisation n'a jamais été réclamée, on la demande à l'utilisateur

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        10);
            }
        } else {

            // autorisation déjà acceptée, on peut faire une action ici
            initLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {

                // cas de notre demande d'autorisation

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // TODO : l'autorisation a été donnée, nous pouvons agir
                    initLocation();

                } else {

                    // l'autorisation a été refusée :(

                }
                return;
            }
        }
    }

}
