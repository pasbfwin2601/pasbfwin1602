package com.bit.creciendojuntos.providers;

import com.bit.creciendojuntos.models.FCMBody;
import com.bit.creciendojuntos.models.FCMResponse;
import com.bit.creciendojuntos.retrofit.IFCMApi;
import com.bit.creciendojuntos.retrofit.RetrofitClient;

import retrofit2.Call;

public class NotificationProvider {

    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {
    }

    public Call<FCMResponse> sendNotification(FCMBody body){
        return RetrofitClient.getClient(url).create(IFCMApi.class).send(body);
    }
}
