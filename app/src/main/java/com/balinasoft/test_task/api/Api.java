package com.balinasoft.test_task.api;


import com.balinasoft.test_task.model.RegistrationBody;
import com.balinasoft.test_task.model.RegistrationResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface Api {
    @POST("/api/account/signup")
    Call<RegistrationResponse> registerUser(@Body RegistrationBody registrationBody);
}
