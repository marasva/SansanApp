package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class TabActivityReservationBiblioteca extends AppCompatActivity {
    private static final String TAG = "ActivityReservationCamp";
    private SectionsPageAdapter mSectionsPageAdapter;
    ViewPager mViewPager;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(TabActivityReservationBiblioteca.this, LoginActivity.class));
                    finish();
                }
            }
        };
        setContentView(R.layout.fragment_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Log.d(TAG,"onCreate: Starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        //change the name of this
        mViewPager = (ViewPager) findViewById(R.id.container);
        //added this for force caching to prevent lagging, but doesn't seem to work :)
        //  mViewPager.setOffscreenPageLimit(2);
        setupViewPager(mViewPager);

        // and this as well of course
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    public void selectFragment(int position){
        mViewPager.setCurrentItem(position, true);
        // true is to animate the transaction
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new LNewReservationTabFragment(),"Nueva reserva");
        adapter.addFragment(new LHistoryReservationTabFragment(),"Mostra tus reservas");
        viewPager.setAdapter(adapter);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
