package com.dipak.onlyfortask;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dipak.onlyfortask.adapter.FirebaseRecyclerAdapter;
import com.dipak.onlyfortask.model.UserModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Firebase extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ArrayList<UserModel> usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        recyclerView=findViewById(R.id.rv);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        FirebaseRecyclerOptions<UserModel> options =
//                new FirebaseRecyclerOptions.Builder<UserModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), UserModel.class)
//                        .build();

        getUsers();

//        FirebaseRecyclerAdapter adapter=new FirebaseRecyclerAdapter(this,new ArrayList<>());

//        recyclerView.setAdapter(adapter);


    }

    private void getUsers() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        usersList = new ArrayList<>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    usersList.add(user);
                }
                // Now usersList contains all the user objects
                Log.d("MainActivity", "Users List: " + usersList);
                Toast.makeText(Firebase.this, "Users List:  + usersList", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Failed to read user data", databaseError.toException());
            }
        });
    }
}