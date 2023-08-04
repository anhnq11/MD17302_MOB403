package com.example.myapplication.ui.carts;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.example.myapplication.Model.invoicesModel;
import com.example.myapplication.Model.productModel;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartsFragment extends Fragment {
    userModel user;
    private List<cartModel> cartList;
    RecyclerView recyclerCart;
    TextView cart_notifi, cart_total, cart_button_pay;
    RelativeLayout bottom_layout;
    RadioButton cart_ship, cart_fast_ship;
    EditText cart_note, cart_address;
    private int shipFee = 20000;
    private int cartTotal = 0;
    private String cartNote, userAddress;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carts, container, false);

        recyclerCart = view.findViewById(R.id.recyclerCart);
        cart_notifi = view.findViewById(R.id.cart_notifi);
        bottom_layout = view.findViewById(R.id.bottom_layout);
        cart_ship = view.findViewById(R.id.cart_ship);
        cart_fast_ship = view.findViewById(R.id.cart_fast_ship);
        cart_total = view.findViewById(R.id.cart_total);
        cart_note = view.findViewById(R.id.cart_note);
        cart_address = view.findViewById(R.id.cart_address);
        cart_button_pay = view.findViewById(R.id.cart_button_pay);

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        user = sharedPreferencesHelper.getObject("userInfo", userModel.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerCart.setLayoutManager(linearLayoutManager);

        cartList = new ArrayList<>();
        getCartList();

        cart_fast_ship.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cart_fast_ship.isChecked()){
                    cart_ship.setChecked(false);
                    shipFee = 40000;
                    getCartList();
                }
            }
        });

        cart_ship.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cart_ship.isChecked()){
                    cart_fast_ship.setChecked(false);
                    shipFee = 20000;
                    getCartList();
                }
            }
        });

        cart_button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartNote = cart_note.getText().toString().trim();
                userAddress = cart_address.getText().toString().trim();
                if (!userAddress.isEmpty()){
                    Date nowDate = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    String timeCreate = simpleDateFormat.format(nowDate);

                    invoicesModel invoice = new invoicesModel(
                            user.get_id(),
                            (ArrayList<cartModel>) cartList,
                            cartTotal,
                            timeCreate,
                            userAddress,
                            "Đang xử lý"
                    );
                    Log.d("TAG", "onClick: " + invoice.getUser_id() +
                            " - " + invoice.getListCart().size() +
                            " - " + invoice.getTotalAmount() +
                            " - " + invoice.getCreatedAt() +
                            " - " + invoice.getUserAddress() +
                            " - " + invoice.getStatus());
//                            Thêm hóa đơn vào bảng hóa đơn
                    ApiServices.apiServices.addToInvoices(invoice).enqueue(new Callback<invoicesModel>() {
                        @Override
                        public void onResponse(Call<invoicesModel> call, Response<invoicesModel> response) {
                            if (response.code() == 200){

                                Log.d("TAG", "Success!");

                                ApiServices.apiServices.deleteCarts(user.get_id()).enqueue(new Callback<cartModel>() {
                                    @Override
                                    public void onResponse(Call<cartModel> call, Response<cartModel> response) {
                                        if (response.code() == 200){
                                            Log.d("TAG", "Delete Success!");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<cartModel> call, Throwable t) {
                                        Log.d("TAG", "Connection Error! " + t);
                                    }
                                });

                                Dialog dialog = new Dialog(getActivity());
                                dialog.setContentView(R.layout.user_address_dialog);
                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                TextView dialog_address_dimiss = dialog.findViewById(R.id.dialog_dimiss);
                                dialog_address_dimiss.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        getCartList();
                                    }
                                });
                                dialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<invoicesModel> call, Throwable t) {
                            Log.d("TAG", "Connection Error! " + t);
                        }
                    });
                }
                else {
                    cart_address.setError("Vui lòng nhập!");
                }
            }
        });

        return view;
    }

    public void getCartList(){
        ApiServices.apiServices.getCart(user.get_id()).enqueue(new Callback<List<cartModel>>() {
            @Override
            public void onResponse(Call<List<cartModel>> call, Response<List<cartModel>> response) {
                if (response.body() != null){
                    cart_notifi.setVisibility(View.GONE);
                    recyclerCart.setVisibility(View.VISIBLE);
                    bottom_layout.setVisibility(View.VISIBLE);
                    cartList = response.body();
                    CartsAdapter cartsAdapter = new CartsAdapter(getContext(), (ArrayList<cartModel>) cartList, user);
                    recyclerCart.setAdapter(cartsAdapter);
                    cartsAdapter.notifyDataSetChanged();

//                    Tổng tiền thanh toán
                    cartTotal = 0;
                    for (cartModel cart : cartList) {
                        cartTotal += (cart.getQuantity() * cart.getPrice());
                    }
                    cartTotal += shipFee;

                    cart_total.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                            .format(cartTotal));
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
    }

}