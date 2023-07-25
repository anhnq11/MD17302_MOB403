package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.R;
import com.example.myapplication.ui.ProductDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Context context;
    private ArrayList<productModel> listProducts;

    public ProductsAdapter(Context context, ArrayList<productModel> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        productModel products = listProducts.get(position);
        holder.txtProductName.setText(products.getName());
        holder.txtProductPrice.setText( products.getPrice() + "");
        Glide.with(context)
                .load(products.getImage())
                .into(holder.img_prdItem_prdImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ProductDetailsFragment(products));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView txtProductName, txtProductPrice;
        private ImageView img_prdItem_prdImg;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            img_prdItem_prdImg = itemView.findViewById(R.id.img_prdItem_prdImg);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setReorderingAllowed(true);
        transaction.replace(R.id.nav_host_fragment_activity_main, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
