package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aula_btn = (Button) findViewById(R.id.aula);
        Button mail_btn = (Button) findViewById(R.id.mail);
        Button map_btn = (Button) findViewById(R.id.mapa);
        Button comida_btn = (Button) findViewById(R.id.comida);



        aula_btn.setOnClickListener(this);
        mail_btn.setOnClickListener(this);
        map_btn.setOnClickListener(this);
        comida_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aula:
                onAula();
                break;
            case R.id.mail:
                onMail();
                break;
            case R.id.mapa:
                onMap();
                break;
            case R.id.comida:
                onComida();
                break;
        }

    }
    public void onMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void onComida() {
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
    }

    public void onAula() {
        Uri uri_aula = Uri.parse("https://aula.usm.cl/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri_aula);
        startActivity(intent);
    }

    public void onMail() {
        Uri uri = Uri.parse("http://mail.sansano.usm.cl/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
=======
package sansanapp.aplicacionesm.usm.cl.sansanapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAul() {

        Button entry = (Button) findViewById(R.id.aula);

        entry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://aula.usm.cl/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    public void onMai() {

        Button entry = (Button) findViewById(R.id.aula);

        entry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://mail.sansano.usm.cl/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}

