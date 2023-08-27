package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.ui.carts.ProductDetailsFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FavourProductsAdapter extends RecyclerView.Adapter<FavourProductsAdapter.FavourProductsViewHolder> {

    private Context context;
    private ArrayList<productModel> listProducts;
    private userModel user;

    public FavourProductsAdapter(Context context, ArrayList<productModel> listProducts, userModel user) {
        this.context = context;
        this.listProducts = listProducts;
        this.user = user;
    }

    @NonNull
    @Override
    public FavourProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favour_product_item, parent, false);
        return new FavourProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavourProductsViewHolder holder, int position) {
        productModel product = listProducts.get(position);
        holder.favour_product_name.setText(product.getName());
        holder.favour_product_price.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                .format(product.getPrice()));
        Glide.with(context)
                .load(product.getImage())
                .into(holder.favour_product_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ProductDetailsFragment(product));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class FavourProductsViewHolder extends RecyclerView.ViewHolder{

        ImageView favour_product_img;
        TextView favour_product_name, favour_product_price, favour_product_detail;

        public FavourProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            favour_product_img = itemView.findViewById(R.id.favour_product_img);
            favour_product_name = itemView.findViewById(R.id.favour_product_name);
            favour_product_price = itemView.findViewById(R.id.favour_product_price);
            favour_product_detail = itemView.findViewById(R.id.favour_product_detail);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
