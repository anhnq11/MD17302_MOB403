package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.API.ApiServices;
import com.example.myapplication.Model.cartModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.CartViewHolder>{

    Context context;
    ArrayList<cartModel> cartList;
    userModel user;
    private int qty;

    public CartsAdapter(Context context, ArrayList<cartModel> cartList, userModel user) {
        this.context = context;
        this.cartList = cartList;
        this.user = user;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        cartModel cart = cartList.get(position);
        holder.cart_item_name.setText(cart.getProduct_id().getName());
        holder.cart_item_price.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                .format(cart.getPrice()));
        holder.cart_item_qty.setText(cart.getQuantity() + "");
        Glide.with(context)
                .load(cart.getProduct_id().getImage())
                .into(holder.cart_item_img);
        qty = cart.getQuantity();
        holder.cart_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartModel myCart = new cartModel(user.get_id(), cart.getProduct_id(), 1, cart.getPrice());
                ApiServices.apiServices.addToCart(myCart).enqueue(new Callback<List<cartModel>>() {
                    @Override
                    public void onResponse(Call<List<cartModel>> call, Response<List<cartModel>> response) {
                        if (response.code() == 200){
                            Toast.makeText(context, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<cartModel>> call, Throwable t) {

                    }
                });
            }
        });

        holder.cart_item_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Minus Onclick!");
                ApiServices.apiServices.removeFromCart(cart.get_id(), cart).enqueue(new Callback<List<cartModel>>() {
                    @Override
                    public void onResponse(Call<List<cartModel>> call, Response<List<cartModel>> response) {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<cartModel>> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView cart_item_img;
        TextView cart_item_name, cart_item_price, cart_item_qty;
        LinearLayout cart_item_add, cart_item_minus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_item_img = itemView.findViewById(R.id.cart_item_img);
            cart_item_name = itemView.findViewById(R.id.cart_item_name);
            cart_item_price = itemView.findViewById(R.id.cart_item_price);
            cart_item_qty = itemView.findViewById(R.id.cart_item_qty);
            cart_item_add = itemView.findViewById(R.id.cart_item_add);
            cart_item_minus = itemView.findViewById(R.id.cart_item_minus);
        }
    }
}
