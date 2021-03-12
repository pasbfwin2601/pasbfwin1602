package com.bit.creciendojuntos.activities.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;

public class InfoUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_usuario);

        MyToolbar.show(this,"Información de la App",true);
    }
}