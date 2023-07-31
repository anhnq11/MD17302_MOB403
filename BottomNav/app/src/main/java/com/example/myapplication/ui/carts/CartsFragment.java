package com.example.myapplication.ui.carts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Adapter.CartsAdapter;
import com.example.myapplication.Adapter.ProductsAdapter;
import com.example.myapplication.Model.cartModel;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartsFragment extends Fragment {
    userModel user;
    private List<cartModel> cartList;
    RecyclerView recyclerCart;
    TextView cart_notifi;
    RelativeLayout bottom_layout;
    RadioButton cart_ship, cart_fast_ship;
    private int shipFee = 20000;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carts, container, false);

        recyclerCart = view.findViewById(R.id.recyclerCart);
        cart_notifi = view.findViewById(R.id.cart_notifi);
        bottom_layout = view.findViewById(R.id.bottom_layout);
        cart_ship = view.findViewById(R.id.cart_ship);
        cart_fast_ship = view.findViewById(R.id.cart_fast_ship);

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        user = sharedPreferencesHelper.getObject("userInfo", userModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerCart.setLayoutManager(linearLayoutManager);

        cartList = new ArrayList<>();
        ApiServices.apiServices.getCart(user.get_id()).enqueue(new Callback<List<cartModel>>() {
            @Override
            public void onResponse(Call<List<cartModel>> call, Response<List<cartModel>> response) {
                if (!response.body().isEmpty()){
                    cart_notifi.setVisibility(View.GONE);
                    recyclerCart.setVisibility(View.VISIBLE);
                    bottom_layout.setVisibility(View.VISIBLE);
                    cartList = response.body();
                    CartsAdapter cartsAdapter = new CartsAdapter(getContext(), (ArrayList<cartModel>) cartList, user);
                    recyclerCart.setAdapter(cartsAdapter);
                    cartsAdapter.notifyDataSetChanged();
                }
                else {
                    cart_notifi.setVisibility(View.VISIBLE);
                    recyclerCart.setVisibility(View.GONE);
                    bottom_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<cartModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: ", t);
            }
        });

        cart_fast_ship.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cart_fast_ship.isChecked()){
                    cart_ship.setChecked(false);
                    shipFee = 40000;
                }
            }
        });

        cart_ship.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cart_ship.isChecked()){
                    cart_fast_ship.setChecked(false);
                    shipFee = 20000;
                }
            }
        });

        return view;
    }

}