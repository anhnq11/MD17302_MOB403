package com.example.myapplication.ui.profile;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Adapter.InvoicesAdapter;
import com.example.myapplication.Adapter.ProductsAdapter;
import com.example.myapplication.Model.invoicesModel;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;
import com.example.myapplication.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInvoicesFragment extends Fragment {

    private RecyclerView recyclerInvoice;
    private userModel user;
    private List<invoicesModel> invoicesList;
    private ImageView my_invoices_back_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_invoices, container, false);

        recyclerInvoice = view.findViewById(R.id.recyclerInvoice);
        my_invoices_back_btn = view.findViewById(R.id.my_invoices_back_btn);

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

        my_invoices_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ProfileFragment());
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}