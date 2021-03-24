package com.bit.creciendojuntos.activities.hijo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;

public class ConsultarEspecialidadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_especialidades);
        MyToolbar.show(this,"Consultar Especialidades",true);
    }
}