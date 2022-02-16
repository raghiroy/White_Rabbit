package com.infrap.myapplicationtest.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.infrap.myapplicationtest.Activity.EmpDetailsActivity;
import com.infrap.myapplicationtest.ModelClass.Example;
import com.infrap.myapplicationtest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> implements Filterable {

    ArrayList<Example> employeeDetails;
    ArrayList<Example> list;
    private Context mcontext;

    public RecyclerViewAdapter(ArrayList<Example> employeeDetails,Context mcontext,RecyclerView recyclerView) {
        this.employeeDetails = employeeDetails;
        this.list= new ArrayList<>(employeeDetails);
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
       // final MarketListData item= marketItems.get(position);
        Example modal = employeeDetails.get(position);
     //   final ArrayList<Example> singledata =new ArrayList<>();

       // singledata.add(modal);
        if(modal!=null){
        holder.empName.setText(modal.getName());
Integer id= Math.toIntExact(modal.getId());
        if(modal.getCompany()!=null) {
            holder.empComapany.setText(modal.getCompany().getName());
        }
        String imgUrll = modal.getProfileImage();

        try {
            Picasso.get()
                    .load(imgUrll)
                    .placeholder(R.drawable.defaultimage)
                    .error(R.drawable.defaultimage)
                    .into(holder.profile_image);
        } catch (Exception e) {
            holder.profile_image.setImageResource(R.drawable.defaultimage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mcontext, EmpDetailsActivity.class);
                i.putExtra("image",id);
                Log.e("test",""+id);
                mcontext.startActivity(i);
            }
        });
    }
}
    @Override
    public int getItemCount() {
        return employeeDetails.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
Filter filter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        ArrayList<Example> filteredlist = new ArrayList<>();
        if (charSequence.toString().isEmpty()) {
            filteredlist.addAll(list);
        } else {
            String filterPattern = charSequence.toString().toLowerCase().trim();
            for (Example item : list) {
                if (item.getName().toLowerCase().contains(filterPattern) ) {
                    filteredlist.add(item);
                }
                else
                    if(item.getEmail().toLowerCase().contains(filterPattern)){
                        filteredlist.add(item);
                    }
            }

        }

        FilterResults results = new FilterResults();
        results.values = filteredlist;
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults results) {
        employeeDetails.clear();;
        employeeDetails.addAll((ArrayList) results.values);
        notifyDataSetChanged();

    }

};
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView empName,empComapany;
        ImageView profile_image;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            empName=itemView.findViewById(R.id.emp_name);
            empComapany=itemView.findViewById(R.id.emp_company);
            profile_image=itemView.findViewById(R.id.profile_image);
        }
    }
}
