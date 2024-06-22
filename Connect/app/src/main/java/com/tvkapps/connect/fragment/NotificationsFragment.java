package com.tvkapps.connect.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tvkapps.connect.R;
import com.tvkapps.connect.adapters.AdapterNotification;
import com.tvkapps.connect.models.ModelNotifications;

import java.util.ArrayList;
import java.util.Collections;


public class NotificationsFragment extends Fragment {


    //Recyclerview
    RecyclerView notificationsRv;

    private ArrayList<ModelNotifications> notificationsList;
    private FirebaseAuth firebaseAuth;
    private AdapterNotification adapterNotification;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        //init recyclerview
        notificationsRv = view.findViewById(R.id.notificationsRv);

        firebaseAuth = FirebaseAuth.getInstance();


        getAllNotifications();


        return view;
    }

    private void getAllNotifications() {
        notificationsList=new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("Notifications")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        notificationsList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){
                            //get data
                            ModelNotifications model=ds.getValue(ModelNotifications.class);
                            //add to list
                            notificationsList.add(model);
                        }
                        //adapter
                        Collections.reverse(notificationsList);
                        adapterNotification=new AdapterNotification(getActivity(),notificationsList);
                        notificationsRv.setAdapter(adapterNotification);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}