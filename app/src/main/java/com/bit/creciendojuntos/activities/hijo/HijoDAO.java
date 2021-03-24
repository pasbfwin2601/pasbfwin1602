package com.bit.creciendojuntos.activities.hijo;



import android.util.Log;

import com.bit.creciendojuntos.utils.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HijoDAO {
    private static HijoDAO hijoDAO;
    private FirebaseDatabase database;
    private DatabaseReference referenceHijos;


    public static HijoDAO getInstance(){
        if (hijoDAO==null) hijoDAO = new HijoDAO();
        return hijoDAO;
    }

    public HijoDAO() {
        database = FirebaseDatabase.getInstance();
        referenceHijos = database.getReference().child("Users");;
    }

    public static String getKeyHijo(){
        return FirebaseAuth.getInstance().getUid();
    }

    public void crearHijo(){
        String pruebita = getKeyHijo().toString();
        Log.d("prueba",pruebita);
        referenceHijos.child("hijos").child("hijo1").child("nombre").setValue("jose");
        referenceHijos.child("hijo1").child("documento").setValue("cedulajose");
        referenceHijos.child(getKeyHijo()).child("hijos").child("hijo1").child("MedicinaGeneral").child("peso").setValue("84kg");
        referenceHijos.child(getKeyHijo()).child("hijos").child("hijo1").child("MedicinaGeneral").child("altura").setValue("2,35mt");
        referenceHijos.child(getKeyHijo()).child("hijos").child("hijo1").child("Vacunas").child("antitetetanica").setValue("24/12/2022");
        referenceHijos.child(getKeyHijo()).child("hijos").child("hijo1").child("Odonlogo").child("Limpieza  Caries").setValue("29/05/2021");
        referenceHijos.child(getKeyHijo()).child("hijos").child("hijo1").child("Oculista").child("OI").setValue("1,25");
        referenceHijos.child(getKeyHijo()).child("hijos").child("hijo2").child("nombre").setValue("pablo");
        referenceHijos.child(getKeyHijo()).child("hijos").child("hijo2").child("documento").setValue("cedulapablo");
    }
}
