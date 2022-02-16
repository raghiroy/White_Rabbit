package com.infrap.myapplicationtest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.infrap.myapplicationtest.Adapter.RecyclerViewAdapter;
import com.infrap.myapplicationtest.ModelClass.Company;
import com.infrap.myapplicationtest.ModelClass.Example;
import com.infrap.myapplicationtest.R;
import com.infrap.myapplicationtest.Remote.RetrofitAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView emp_recyclerView;
    private ArrayList<Example> employeeDetails;
    private  SearchView searchView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emp_recyclerView = findViewById(R.id.emp_recyclerView);
        searchView=findViewById(R.id.search_bar);
        employeeDetails = new ArrayList<>();
        getAllEmployee();


    }

    private void getAllEmployee() {
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
                // inside on response method we are checking
                // if the response is success or not.
                if (response.isSuccessful()) {


                    // below line is to add our data from api to our array list.
                    employeeDetails = response.body();

                    // below line we are running a loop to add data to our adapter class.

                        recyclerViewAdapter = new RecyclerViewAdapter(employeeDetails, MainActivity.this,emp_recyclerView);

                        // below line is to set layout manager for our recycler view.
                        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);

                        // setting layout manager for our recycler view.
                        emp_recyclerView.setLayoutManager(manager);

                        // below line is to set adapter to our recycler view.
                        emp_recyclerView.setAdapter(recyclerViewAdapter);


                }
            }

            @Override
            public void onFailure(Call<ArrayList<Example>> call, Throwable t) {
                // in the method of on failure we are displaying a
                // toast message for fail to get data.
Log.e("test",t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem mSearch = menu.findItem(R.id.app_bar_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("newText1",query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("newText",newText);
                recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}