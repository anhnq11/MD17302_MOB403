package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.invoicesModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InvoicesAdapter extends RecyclerView.Adapter<InvoicesAdapter.InvoiceViewHolder>{

    Context context;
    List<invoicesModel> invoicesList;
    userModel user;

    public InvoicesAdapter(Context context, List<invoicesModel> invoicesList, userModel user) {
        this.context = context;
        this.invoicesList = invoicesList;
        this.user = user;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_item, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        invoicesModel invoice = invoicesList.get(position);
        holder.invoice_time.setText((position + 1) + " - " + invoice.getCreatedAt());
        holder.invoice_address.setText("Địa chỉ: " + invoice.getUserAddress());
        holder.invoice_status.setText(invoice.getStatus());
        holder.invoice_total_amount.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                .format(invoice.getTotalAmount()));
//        Glide.with(context)
//                .load(invoice.getListCart().get(0).getProduct_id().getImage())
//                .into(holder.invoice_item_img);
    }

    @Override
    public int getItemCount() {
        return invoicesList.size();
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder{

        private TextView invoice_time, invoice_address, invoice_status, invoice_total_amount;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            invoice_time = itemView.findViewById(R.id.invoice_time);
            invoice_address = itemView.findViewById(R.id.invoice_address);
            invoice_status = itemView.findViewById(R.id.invoice_status);
            invoice_total_amount = itemView.findViewById(R.id.invoice_total_amount);
        }
    }
}
