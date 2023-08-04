package com.example.myapplication.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Adapter.InvoicesAdapter;
import com.example.myapplication.Adapter.ProductsAdapter;
import com.example.myapplication.Model.invoicesModel;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInvoicesFragment extends Fragment {

    private RecyclerView recyclerInvoice;
    private userModel user;
    private List<invoicesModel> invoicesList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_invoices, container, false);

        recyclerInvoice = view.findViewById(R.id.recyclerInvoice);

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        user = sharedPreferencesHelper.getObject("userInfo", userModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerInvoice.setLayoutManager(linearLayoutManager);

        ApiServices.apiServices.getInvoices(user.get_id()).enqueue(new Callback<List<invoicesModel>>() {
            @Override
            public void onResponse(Call<List<invoicesModel>> call, Response<List<invoicesModel>> response) {
                if (response.code() == 200){
                    invoicesList = response.body();
                    InvoicesAdapter invoicesAdapter = new InvoicesAdapter(getContext(), invoicesList, user);
                    recyclerInvoice.setAdapter(invoicesAdapter);
                    invoicesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<invoicesModel>> call, Throwable t) {
                Log.d("TAG", "Connection Error! " + t);
            }
        });

        return view;
    }
}