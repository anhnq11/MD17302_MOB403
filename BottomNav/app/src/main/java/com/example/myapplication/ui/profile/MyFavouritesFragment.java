package com.example.myapplication.ui.profile;

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
import com.example.myapplication.Adapter.FavourProductsAdapter;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFavouritesFragment extends Fragment {

    private ImageView my_favour_back_btn;
    RecyclerView recycler_favour_product;
    userModel user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_favourites, container, false);

        my_favour_back_btn = view.findViewById(R.id.my_favour_back_btn);
        recycler_favour_product = view.findViewById(R.id.recycler_favour_product);

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        user = sharedPreferencesHelper.getObject("userInfo", userModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_favour_product.setLayoutManager(linearLayoutManager);

        ApiServices.apiServices.getListFavours(user.get_id()).enqueue(new Callback<ArrayList<productModel>>() {
            @Override
            public void onResponse(Call<ArrayList<productModel>> call, Response<ArrayList<productModel>> response) {
                if (response.code() == 200){
                    ArrayList<productModel> listProducts = response.body();
                    FavourProductsAdapter favourProductsAdapter = new FavourProductsAdapter(getContext(), listProducts, user);
                    recycler_favour_product.setAdapter(favourProductsAdapter);
                    favourProductsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<productModel>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
            }
        });

        my_favour_back_btn.setOnClickListener(new View.OnClickListener() {
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