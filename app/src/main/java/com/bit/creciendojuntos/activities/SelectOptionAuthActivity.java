package com.bit.creciendojuntos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.medico.RegisterMedicoActivity;
import com.bit.creciendojuntos.activities.usuario.RegisterActivity;
import com.bit.creciendojuntos.includes.MyToolbar;

public class SelectOptionAuthActivity extends AppCompatActivity {

    private Button mButtonLogin;
    private Button mButtonRegister;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        MyToolbar.show(this,"Seleccionar opción",true);

        mButtonLogin = (Button) findViewById(R.id.btnLogin);
        mButtonRegister = (Button) findViewById(R.id.btnRegister);


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
        mPref = getApplicationContext().getSharedPreferences("typeUser",MODE_PRIVATE);
    }

    private void goToRegister() {
        String typeUser = mPref.getString("user","");
        if (typeUser.equals("usuario")) {
            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterMedicoActivity.class);
            startActivity(intent);
        }

    }

    private void goToLogin() {
        Intent intent = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}