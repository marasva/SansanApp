package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class TabActivityReservationBiblioteca extends AppCompatActivity {
    private static final String TAG = "ActivityReservationCamp";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Log.d(TAG, "onCreate: Starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        //change the name of this
        mViewPager = (ViewPager) findViewById(R.id.container);
        //added this for force caching to prevent lagging, but doesn't seem to work :)
        mViewPager.setOffscreenPageLimit(2);
        setupViewPager(mViewPager);

        // and this as well of course
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new CNewReservationTabFragment(), "Nueva reserva");
        adapter.addFragment(new CHistoryReservationTabFragment(), "Mostrar tus reservas");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
