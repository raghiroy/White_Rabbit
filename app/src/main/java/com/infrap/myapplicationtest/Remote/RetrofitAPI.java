package com.infrap.myapplicationtest.Remote;

import com.infrap.myapplicationtest.ModelClass.Example;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {
    @GET("v2/5d565297300000680030a986")
    Call<ArrayList<Example>> getAllEmployee();

}
