package com.example.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Adapter.ProductsAdapter;
import com.example.myapplication.Model.favoursModel;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recyclerFeature;
    ImageView home_user_img;
    List<favoursModel> favoursList;
    LinearLayout layout_favourList;
    RecyclerView recyclerFavour;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        home_user_img = view.findViewById(R.id.home_user_img);
        layout_favourList = view.findViewById(R.id.layout_favourList);
        recyclerFavour = view.findViewById(R.id.recyclerFavour);

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        userModel user = sharedPreferencesHelper.getObject("userInfo", userModel.class);
        Glide.with(getContext())
                .load(user.getImage())
                .circleCrop()
                .into(home_user_img);

        ApiServices.apiServices.getListFavours(user.get_id()).enqueue(new Callback<ArrayList<productModel>>() {
            @Override
            public void onResponse(Call<ArrayList<productModel>> call, Response<ArrayList<productModel>> response) {
                if (response.code() == 200){
                    layout_favourList.setVisibility(View.VISIBLE);
                    ArrayList<productModel> favourProducts = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerFavour.setLayoutManager(linearLayoutManager);
                    ProductsAdapter productsAdapter = new ProductsAdapter(getContext(), favourProducts, user);
                    recyclerFavour.setAdapter(productsAdapter);
                    productsAdapter.notifyDataSetChanged();
                }
                else {
                    layout_favourList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<productModel>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
            }
        });

        return view;
    }

}