package com.example.myapplication.ui.carts;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Model.cartModel;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;
import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment {

    ImageView prd_detail_img, prd_detail_back_btn;
    TextView prd_detail_name, prd_detail_desc, prd_detail_price, prd_button_buy;
    productModel product;
    userModel user;

    public ProductDetailsFragment(productModel products) {
        this.product = products;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        prd_detail_img = view.findViewById(R.id.prd_detail_img);
        prd_detail_name = view.findViewById(R.id.prd_detail_name);
        prd_detail_desc = view.findViewById(R.id.prd_detail_desc);
        prd_detail_price = view.findViewById(R.id.prd_detail_price);
        prd_button_buy = view.findViewById(R.id.prd_button_buy);
        prd_detail_back_btn = view.findViewById(R.id.prd_detail_back_btn);

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        user = sharedPreferencesHelper.getObject("userInfo", userModel.class);

        Glide.with(getContext())
                .load(product.getImage())
                .into(prd_detail_img);
        prd_detail_name.setText(product.getName());
        prd_detail_desc.setText(product.getDesc());
        prd_detail_price.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                .format(product.getPrice()));

        prd_button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartModel myCart = new cartModel(user.get_id(), product, 1, product.getPrice());
                ApiServices.apiServices.addToCart(myCart).enqueue(new Callback<List<cartModel>>() {
                    @Override
                    public void onResponse(Call<List<cartModel>> call, Response<List<cartModel>> response) {
                        if (response.code() == 200){
                            Toast.makeText(getContext(), "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                            loadFragment(new CartsFragment());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<cartModel>> call, Throwable t) {

                    }
                });
            }
        });

        prd_detail_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new DashboardFragment());
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