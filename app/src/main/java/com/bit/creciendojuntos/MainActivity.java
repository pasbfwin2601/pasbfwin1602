package com.bit.creciendojuntos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bit.creciendojuntos.includes.MyToolbar;

public class MainActivity extends AppCompatActivity {

    private Button mButtonMedico;
    private Button mButtonUsuario;

    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();

        MyToolbar.show(this,"Menú de Inicio",false);

        mButtonMedico = (Button) findViewById(R.id.btnMedico);
        mButtonUsuario = (Button) findViewById(R.id.btnUsuario);
        
        mButtonMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "medico");
                editor.apply();
                goToSelectAuth();
            }
        });

        mButtonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "usuario");
                editor.apply();
                goToSelectAuth();
            }
        });

    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        startActivity(intent);
    }
}