package com.bit.creciendojuntos.retrofit;

import com.bit.creciendojuntos.models.FCMBody;
import com.bit.creciendojuntos.models.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {

    @Headers({
          "Content-Type:application/json",
          "Authorization:key=AAAAmq8RVjQ:APA91bGHv4DNF6jqZlhYDQTd8dIb0ZDUv2xCwGhqL2x9_-mPIV8UGG6LffsAdbMeRNJPpwK3SmsKvm2NJCpppZkKEjR7nLh7hCP78_jkAbsEIoHu3xc2Jc9pjGKNteVOapY5i-PhgKwx"
    })

    @POST("fcm/send")
    Call<FCMResponse> send(@Body FCMBody body);
}
