package com.example.myapplication.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Model.products;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerProduct;
    private List<products> listProducts;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerProduct = view.findViewById(R.id.recyclerProduct);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerProduct.setLayoutManager(linearLayoutManager);

        listProducts = new ArrayList<>();
        getProducts();

        return view;
    }

    private void getProducts(){
        ApiServices.apiServices.getProducts().enqueue(new Callback<List<products>>() {
            @Override
            public void onResponse(Call<List<products>> call, Response<List<products>> response) {
                listProducts = (ArrayList<products>) response.body();
                if (!listProducts.isEmpty()){
                    Toast.makeText(getContext(), "Load dữ liệu thành công!" + listProducts.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<products>> call, Throwable t) {
                Log.e("TAG", "onFailure: ", t);
            }
        });
    }

}