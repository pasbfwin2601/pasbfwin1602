package com.bit.creciendojuntos.providers;

import android.content.Context;

import com.bit.creciendojuntos.utils.CompressorBitmapImage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ImagesProvider {

    private StorageReference mStorage;

    public ImagesProvider(String ref) {
        mStorage = FirebaseStorage.getInstance().getReference().child(ref);
    }

    public UploadTask saveImage(Context context, File image, String idUser) {
        byte [] imageBytes = CompressorBitmapImage.getImage(context, image.getPath(), 500, 500);
        final StorageReference storage = mStorage.child(idUser + ".jpg");
        mStorage = storage;
        UploadTask uploadTask = storage.putBytes(imageBytes);
        return uploadTask;
    }

    public StorageReference getStorage(){
        return mStorage;
    }
}
