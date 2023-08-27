package com.example.myapplication.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.userModel;
import com.example.myapplication.R;
import com.example.myapplication.sharedPreferencesHelper.SharedPreferencesHelper;

public class ProfileFragment extends Fragment {

    private ImageView profile_img;
    private TextView profile_username, profile_email;
    private userModel user;

    LinearLayout profile_my_invoices, profile_my_favours, profile_logout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_img = view.findViewById(R.id.profile_img);
        profile_username = view.findViewById(R.id.profile_username);
        profile_email = view.findViewById(R.id.profile_email);
        profile_my_invoices = view.findViewById(R.id.profile_my_invoices);
        profile_logout = view.findViewById(R.id.profile_logout);
        profile_my_favours = view.findViewById(R.id.profile_my_favours);

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        user = sharedPreferencesHelper.getObject("userInfo", userModel.class);
        Glide.with(getContext())
                .load(user.getImage())
                .circleCrop()
                .into(profile_img);
        profile_username.setText(user.getFullname());
        profile_email.setText(user.getEmail());

        profile_my_invoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new MyInvoicesFragment());
            }
        });

        profile_my_favours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new MyFavouritesFragment());
            }
        });

        profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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