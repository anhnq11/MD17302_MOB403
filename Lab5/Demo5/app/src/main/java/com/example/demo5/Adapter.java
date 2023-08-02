package com.example.demo5;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private ArrayList<Model> listUser;
    String url = "https://64b93df279b7c9def6c0cca0.mockapi.io/users/users";

    public Adapter(Context context, ArrayList<Model> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model user = listUser.get(position);
        holder.txtUsername.setText((position + 1) + " - " + user.getUsername());
        holder.txtEmail.setText(user.getEmail());
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Delete method here!!!
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url + "/" + user.getId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, "Đã xóa người dùng!", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("TAG", "Eror!!");
                            }
                        }
                );
                requestQueue.add(deleteRequest);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Update method here

                Gson gson = new Gson();
                String userData = gson.toJson(user);
                Intent intent =  new Intent(context, UpdateActivity.class);
                intent.putExtra("data", userData);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtUsername, txtEmail;
        Button btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}

