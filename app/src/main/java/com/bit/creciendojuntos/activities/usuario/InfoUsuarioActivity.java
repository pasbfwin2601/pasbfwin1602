package com.bit.creciendojuntos.activities.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoUsuarioActivity extends AppCompatActivity {

    FloatingActionButton mfabInstagram;
    FloatingActionButton mfabTwitter;
    FloatingActionButton mfabFacebook;
    String urlInstagram;
    String urlTwitter;
    String urlFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_usuario);

        MyToolbar.show(this,"Información de la App",true);
        mfabFacebook = findViewById(R.id.fabFacebook);
        mfabTwitter = findViewById(R.id.fabTwitter);
        mfabInstagram = findViewById(R.id.fabInstagram);

        urlInstagram ="https://m.instagram.com/creciendojuntos5421/";
        urlTwitter ="https://m.twitter.com/Crecien09210416";
        urlFacebook ="https://m.facebook.com/creciendo.juntos.7545";

        mfabFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriFacebook = Uri.parse(urlFacebook);
                Intent intent = new Intent(Intent.ACTION_VIEW, uriFacebook);
                startActivity(intent);
            }
        });

        mfabTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriTwitter = Uri.parse(urlTwitter);
                Intent intent = new Intent(Intent.ACTION_VIEW, uriTwitter);
                startActivity(intent);
            }
        });

        mfabInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriInstagrama = Uri.parse(urlInstagram);
                Intent intent = new Intent(Intent.ACTION_VIEW, uriInstagrama);
                startActivity(intent);
            }
        });

    }
}