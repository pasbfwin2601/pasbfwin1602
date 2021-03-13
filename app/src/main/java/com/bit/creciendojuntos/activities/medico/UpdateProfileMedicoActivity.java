package com.bit.creciendojuntos.activities.medico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.LoginActivity;
import com.bit.creciendojuntos.activities.usuario.UpdateProfileUsuarioActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Medico;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.bit.creciendojuntos.providers.ImagesProvider;
import com.bit.creciendojuntos.providers.MedicoProvider;

import com.bit.creciendojuntos.utils.CompressorBitmapImage;
import com.bit.creciendojuntos.utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;

public class UpdateProfileMedicoActivity extends AppCompatActivity {

    private Button mButtonProfile;
    private TextView mTextViewNombreMedico;
    private TextView mTextViewTelefonoMedico;
    private ImageView mImageViewMedico;

    private MedicoProvider mMedicoProvider;
    private AuthProvider mAuthProvider;
    private ImagesProvider mImageProvider;


    private File mImageFile;
    private File mImageFileGenerica;
    private String mImage;

    private final int GALLERY_REQUEST = 1;
    private ProgressDialog mProgressDialog;

    private String mNombre;
    private String mTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_medico);
        MyToolbar.show(this,"Actualizar Perfil Médico",true);

        mAuthProvider = new AuthProvider();
        mMedicoProvider = new MedicoProvider();
        mImageProvider = new ImagesProvider("medico_images");

        mButtonProfile = findViewById(R.id.btnUpdateMedico);
        mTextViewNombreMedico = findViewById(R.id.textUpdateNombreRegM);
        mTextViewTelefonoMedico = findViewById(R.id.textUpdateTelefonoRegM);
        mImageViewMedico = findViewById(R.id.imageViewProfile);

        mProgressDialog = new ProgressDialog(this);
        getMedicoInfo();
        mImageViewMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try{
                mImageFile = FileUtil.from(this,data.getData());
                //mImageViewProfile.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
                mImageViewMedico.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            } catch (Exception e){
                Log.d("Error","Mensaje: "+e.getMessage());
            }

        }
    }

    private void getMedicoInfo() {
        mMedicoProvider.getMedico(mAuthProvider.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombreMedico = dataSnapshot.child("nombre").getValue().toString();
                    String telefonoMedico = dataSnapshot.child("telefono").getValue().toString();
                     String image = "";
                    if (dataSnapshot.hasChild("image")){
                        image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(mImageViewMedico);
                        //Picasso.with(UpdateProfileMedicoActivity.this).load(image).into(mImageViewMedico);
                    } else {
                        saveSoloImage();
                        Toast.makeText(UpdateProfileMedicoActivity.this, "Para actualizar el perfil, debe cargar una nueva imagen personal", Toast.LENGTH_LONG).show();
                    }
                    mTextViewNombreMedico.setText(nombreMedico);
                    mTextViewTelefonoMedico.setText(telefonoMedico);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateProfile() {
        mNombre = mTextViewNombreMedico.getText().toString();
        mTelefono = mTextViewTelefonoMedico.getText().toString();
        if (!mNombre.equals("") && !mTelefono.equals("") && mImageFile != null) {
            mProgressDialog.setMessage("Espere un momento");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            saveImage();
        } else {
            Toast.makeText(UpdateProfileMedicoActivity.this, "Actualice el nombre, el telefono y la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage() {
            mImageProvider.saveImage(UpdateProfileMedicoActivity.this,mImageFile, mAuthProvider.getId()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String image = uri.toString();
                            Medico medico = new Medico();
                            medico.setNombre(mNombre);
                            medico.setTelefono(mTelefono);
                            medico.setImage(image);
                            medico.setId(mAuthProvider.getId());
                            mMedicoProvider.update(medico).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(UpdateProfileMedicoActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(UpdateProfileMedicoActivity.this, "Hubo un error al subir la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveSoloImage() {
        mImageProvider.saveImage(UpdateProfileMedicoActivity.this,mImageFile, mAuthProvider.getId()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String image = uri.toString();
                            Medico medico = new Medico();
                            medico.setImage(image);
                            medico.setId(mAuthProvider.getId());
                            mMedicoProvider.update(medico).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(UpdateProfileMedicoActivity.this, "La imagen se guardo correctamente", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(UpdateProfileMedicoActivity.this, "Hubo un error al guardar la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}