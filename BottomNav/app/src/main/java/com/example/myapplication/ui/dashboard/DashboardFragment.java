package com.example.myapplication.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Adapter.ProductsAdapter;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerProduct;
    private List<productModel> listProducts;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerProduct = view.findViewById(R.id.recyclerProduct);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerProduct.setLayoutManager(gridLayoutManager);

        listProducts = new ArrayList<>();
        getProducts();

        return view;
    }

    private void getProducts() {
        ApiServices.apiServices.getProducts().enqueue(new Callback<List<productModel>>() {
            @Override
            public void onResponse(Call<List<productModel>> call, Response<List<productModel>> response) {
                if (!response.body().isEmpty()) {
                    listProducts = (ArrayList<productModel>) response.body();
                    ProductsAdapter productsAdapter = new ProductsAdapter(getContext(), (ArrayList<productModel>) listProducts);
                    recyclerProduct.setAdapter(productsAdapter);
                    productsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<productModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: ", t);
            }
        });
    }

}