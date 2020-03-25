package id.rafi.healthcare.coronainformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.rafi.healthcare.coronainformation.covid_information.InformasiCovid;
import id.rafi.healthcare.coronainformation.data_dunia.DataDunia;
import id.rafi.healthcare.coronainformation.ringkasan_dunia.RingkasanDunia;
import id.rafi.healthcare.coronainformation.ringkasan_indonesia.RingkasanIndonesia;

public class FirstLayout extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_layout);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new DataDunia());
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.informasiCovid:
                selectedFragment = new InformasiCovid();
                break;
            case R.id.ringkasanDunia:
                selectedFragment = new RingkasanDunia();
                break;
            case R.id.dataDunia:
                selectedFragment = new DataDunia();
                break;
            case R.id.ringkasanIndonesia:
                selectedFragment = new RingkasanIndonesia();
                break;
        }

        return loadFragment(selectedFragment);
    }
}
