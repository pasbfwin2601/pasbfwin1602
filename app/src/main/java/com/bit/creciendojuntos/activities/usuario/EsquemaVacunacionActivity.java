package com.bit.creciendojuntos.activities.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;

public class EsquemaVacunacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esquema_vacunacion);
        MyToolbar.show(this,"Esquema de Vacunación",true);
    }
}