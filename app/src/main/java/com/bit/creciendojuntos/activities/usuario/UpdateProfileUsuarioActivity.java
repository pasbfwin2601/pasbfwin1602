package com.bit.creciendojuntos.activities.usuario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bit.creciendojuntos.R;
import com.bit.creciendojuntos.activities.LoginActivity;
import com.bit.creciendojuntos.includes.MyToolbar;
import com.bit.creciendojuntos.models.Usuario;
import com.bit.creciendojuntos.providers.AuthProvider;
import com.bit.creciendojuntos.providers.ImagesProvider;
import com.bit.creciendojuntos.providers.UsuarioProvider;

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
import java.io.IOException;
import java.util.Date;

public class UpdateProfileUsuarioActivity extends AppCompatActivity {

    private Button mButtonProfile;
    private TextView mTextViewNombreUsuario;
    private TextView mTextViewTelefonoUsuario;
    private TextView mTextViewDireccionUsuario;
    private ImageView mImageViewUsuario;

    private UsuarioProvider mUsuarioProvider;
    private AuthProvider mAuthProvider;
    private ImagesProvider mImageProvider;

    private File mImageFile;
    File mPhotoFile;
    private String mImage;

    private final int GALLERY_REQUEST = 1;
    private final int PHOTO_REQUEST_CODE = 2;;
    String mAbsolutePhotoPath;
    String mPhotoPath;
    File mPhotofFile;


    private ProgressDialog mProgressDialog;

    private String mNombre;
    private String mDomicilio;
    private String mTelefono;
    AlertDialog.Builder mBuilderSelector;
    CharSequence options[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_usuario);
        MyToolbar.show(this,"Actualizar Perfil Usuario",true);

        mAuthProvider = new AuthProvider();
        mUsuarioProvider = new UsuarioProvider();
        mImageProvider = new ImagesProvider("usuario_images");

        mButtonProfile = findViewById(R.id.btnUpdateUsuario);
        mTextViewNombreUsuario = findViewById(R.id.textUpdateNombreRegU);
        mTextViewDireccionUsuario = findViewById(R.id.textInputUpdateDireccionU);
        mTextViewTelefonoUsuario = findViewById(R.id.textUpdateTelefonoRegU);
        mImageViewUsuario = findViewById(R.id.imageViewProfileUsuario);

        mBuilderSelector = new AlertDialog.Builder(this);
        mBuilderSelector.setTitle("Selecciona una opcion");
        options = new CharSequence[] {"Imagen de galeria", "Tomar foto"};


        mProgressDialog = new ProgressDialog(this);
        getUsuarioInfo();

        mImageViewUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOptionImage(GALLERY_REQUEST);

            }
        });

        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    private void selectOptionImage(final int requestCode) {
        mBuilderSelector.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if  (i==0) {
                    openGallery(GALLERY_REQUEST);
                } else if (i==1) {
                    takePhoto(PHOTO_REQUEST_CODE);
                }
            }
        });
        mBuilderSelector.show();
    }

    private void takePhoto(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createPhotoFile(requestCode);
            } catch (Exception e){
                Toast.makeText(this, "Hubo un error con el archivo" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
               Uri photoUri = FileProvider.getUriForFile(UpdateProfileUsuarioActivity.this, "com.bit.creciendojuntos",photoFile);
               takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
               startActivityForResult(takePictureIntent, requestCode);
            }
        }

    }

    private File createPhotoFile(int requestCode) throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = File.createTempFile(
                new Date() + "_photo",
                ".jpg",
                storageDir
        );
        if (requestCode == PHOTO_REQUEST_CODE){
            mPhotoPath = "file:" + photoFile.getAbsolutePath();
            mAbsolutePhotoPath = photoFile.getAbsolutePath();
        }
        return photoFile;
    }

    private void openGallery(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try{
                mPhotofFile = null;
                mImageFile = FileUtil.from(this,data.getData());
                mImageViewUsuario.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            } catch (Exception e){
                Log.d("Error","Mensaje: "+e.getMessage());
                Toast.makeText(this, "Se produjo un error", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK){
            mImageFile = null;
            mPhotoFile = new File(mAbsolutePhotoPath);
            Picasso.get().load(mPhotoPath).into(mImageViewUsuario);
        }
    }

    private void getUsuarioInfo() {
        mUsuarioProvider.getUsuario(mAuthProvider.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombreUsuario = dataSnapshot.child("nombre").getValue().toString();
                    String direccionUsuario = dataSnapshot.child("domicilio").getValue().toString();
                    String telefonoUsuario = dataSnapshot.child("telefono").getValue().toString();
                    String image = "";
                    if (dataSnapshot.hasChild("image")){
                        image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(mImageViewUsuario);
                    } else {
                        Toast.makeText(UpdateProfileUsuarioActivity.this, "Para actualizar el perfil, debe cargar una nueva imagen personal", Toast.LENGTH_LONG).show();
                    }
                    mTextViewNombreUsuario.setText(nombreUsuario);
                    mTextViewDireccionUsuario.setText(direccionUsuario);
                    mTextViewTelefonoUsuario.setText(telefonoUsuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateProfile() {
        mNombre = mTextViewNombreUsuario.getText().toString();
        mDomicilio = mTextViewDireccionUsuario.getText().toString();
        mTelefono = mTextViewTelefonoUsuario.getText().toString();
        if (!mNombre.equals("") && !mDomicilio.equals("") && !mTelefono.equals("")) {
            if (mImageFile != null && mPhotoFile == null ) {
                mProgressDialog.setMessage("Espere un momento");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                saveImage(mImageFile);
            } else if (mPhotoFile != null && mImageFile == null ) {
                mProgressDialog.setMessage("Espere un momento");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                saveImage(mPhotoFile);
            } else {
                Toast.makeText(com.bit.creciendojuntos.activities.usuario.UpdateProfileUsuarioActivity.this, "Debe seleccionar una imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(com.bit.creciendojuntos.activities.usuario.UpdateProfileUsuarioActivity.this, "Actualice el nombre, el telefono, la direccion y la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage(File imgSubir) {
       mImageProvider.saveImage(UpdateProfileUsuarioActivity.this,imgSubir, mAuthProvider.getId()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String url = uri.toString();
                            Usuario usuario = new Usuario();
                            usuario.setNombre(mNombre);
                            usuario.setDomicilio(mDomicilio);
                            usuario.setTelefono(mTelefono);
                            usuario.setImage(url);
                            usuario.setId(mAuthProvider.getId());

                            mUsuarioProvider.update(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(UpdateProfileUsuarioActivity.this, "La informacion se actualizo correctamente", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(com.bit.creciendojuntos.activities.usuario.UpdateProfileUsuarioActivity.this, "Hubo un error al subir la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}