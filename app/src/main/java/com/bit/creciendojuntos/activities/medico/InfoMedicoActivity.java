package com.bit.creciendojuntos.activities.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.includes.MyToolbar;

public class InfoMedicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_medico);

        MyToolbar.show(this,"Información de la App",true);
    }
}