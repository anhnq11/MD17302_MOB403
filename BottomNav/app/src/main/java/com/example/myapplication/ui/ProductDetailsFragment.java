package com.example.myapplication.ui;

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
import com.example.myapplication.Model.productModel;
import com.example.myapplication.R;
import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.squareup.picasso.Picasso;

public class ProductDetailsFragment extends Fragment {

    ImageView prd_detail_img, prd_detail_back_btn;
    TextView prd_detail_name, prd_detail_desc, prd_detail_price, prd_button_buy;
    productModel product;

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

        Glide.with(getContext())
                .load(product.getImage())
                .into(prd_detail_img);
        prd_detail_name.setText(product.getName());
        prd_detail_desc.setText(product.getDesc());
        prd_detail_price.setText(product.getPrice() + " Đ");

        prd_button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Hi " + product.getName(), Toast.LENGTH_SHORT).show();
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