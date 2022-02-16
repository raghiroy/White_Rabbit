package com.infrap.myapplicationtest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.infrap.myapplicationtest.ModelClass.Example;
import com.infrap.myapplicationtest.R;
import com.infrap.myapplicationtest.Remote.RetrofitAPI;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpDetailsActivity extends AppCompatActivity {
ImageView profile_image;
Activity activity;
    private ArrayList<Example> employeeDetails;

    int request_code;
TextView name,address,email_id,company,userName,phoneNumber,webSite,street,city,zipcode,suite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_details);

       Intent i = getIntent();
        request_code=i.getIntExtra("image",0);
        Log.e("test1",""+request_code);
        name=findViewById(R.id.emp_name);
        address=findViewById(R.id.emp_address);
        email_id=findViewById(R.id.emp_email);
        company=findViewById(R.id.emp_company);
        userName=findViewById(R.id.emp_userName);
        phoneNumber=findViewById(R.id.emp_phn);
        webSite=findViewById(R.id.emp_website);

        city=findViewById(R.id.city);
        zipcode=findViewById(R.id.zipcode);
        suite=findViewById(R.id.suite);
        profile_image=findViewById(R.id.profile_image);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getAllEmpDetails();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    private void getAllEmpDetails(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // on below line we are calling a method to get all the courses from API.
        Call<ArrayList<Example>> call = retrofitAPI.getAllEmployee();
        call.enqueue(new Callback<ArrayList<Example>>() {

            @Override
            public void onResponse(Call<ArrayList<Example>> call, Response<ArrayList<Example>> response) {
                if (response.isSuccessful()) {
                    employeeDetails = response.body();
                    for(int i=0;i<employeeDetails.size();i++){
                        if(employeeDetails.get(i).getId()==request_code){
                            Log.e("test",""+i);
                            name.setText(employeeDetails.get(i).getName());
                            userName.setText(employeeDetails.get(i).getUsername());
                            email_id.setText(employeeDetails.get(i).getEmail());
                            address.setText(employeeDetails.get(i).getAddress().getStreet());
                            suite.setText(employeeDetails.get(i).getAddress().getSuite());
                            city.setText(employeeDetails.get(i).getAddress().getCity());
                            zipcode.setText(employeeDetails.get(i).getAddress().getZipcode());

                            if(employeeDetails.get(i).getPhone()!=null  ){
                                phoneNumber.setText(employeeDetails.get(i).getPhone());

                            }else{
                                phoneNumber.setText("Not Provided");

                            }
                            if(employeeDetails.get(i).getWebsite()!=null) {
                                webSite.setText(employeeDetails.get(i).getWebsite());}
                            if(employeeDetails.get(i).getCompany()!=null) {
                                company.setText(employeeDetails.get(i).getCompany().getName());}
                            if(employeeDetails.get(i).getProfileImage()!=null) {
                                String imgUrll = employeeDetails.get(i).getProfileImage();
                                try {
                                    Picasso.get()
                                            .load(imgUrll)
                                            .placeholder(R.drawable.defaultimage)
                                            .error(R.drawable.defaultimage)
                                            .into(profile_image);
                                } catch (Exception e) {
                                    // profile_image.setImageResource(R.drawable.defaultimage);
                                }
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Example>> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}