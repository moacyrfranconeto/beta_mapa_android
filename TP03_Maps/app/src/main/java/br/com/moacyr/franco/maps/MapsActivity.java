package br.com.moacyr.franco.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressWarnings("MissingPermission")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_FINE_LOCATION = 1;
    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.nome = getIntent().getStringExtra("NOME");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (writePermission != PackageManager.PERMISSION_GRANTED) {
                requerPermissao();
                return;
            }
        }
        navegaMinhaLocalizacao();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navegaMinhaLocalizacao();
            } else {
                Toast.makeText(getApplicationContext(), "Sem permissão para acessar a localização", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navegaMinhaLocalizacao(){
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng myPosition = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(myPosition).title(this.nome.toUpperCase() + ": Sua posição"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 12));
        }
    }

    private void requerPermissao(){
        ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_LOCATION,
                REQUEST_FINE_LOCATION
        );
    }
}
