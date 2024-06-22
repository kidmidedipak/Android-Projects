package com.dipak.chatapplication.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dipak.chatapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapp-48d54-default-rtdb.firebaseio.com/");

    private int generatedChatKey;
    String getUserMobile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageView backBtn=findViewById(R.id.backBtn);
        final TextView nameTV=findViewById(R.id.name);
        final EditText messageEditText=findViewById(R.id.messageEditTxt);
        final ImageView sendbtn=findViewById(R.id.sendBtn);
        final CircleImageView profilePic=findViewById(R.id.profilePic);

        //get data from message adapter class
        final  String getName=getIntent().getStringExtra("name");
        final  String getProfilePic=getIntent().getStringExtra("profile_pic");
        final  String chatKey=getIntent().getStringExtra("chat_key");
        final  String getMobile=getIntent().getStringExtra("mobile");


        nameTV.setText(getName);
        Picasso.get().load(getProfilePic).into(profilePic);

        if (chatKey.isEmpty())
        {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //generate chat key. default cahtkey is 1
                generatedChatKey=1;
                if(snapshot.hasChild("chat"))
                {
                    generatedChatKey=(int)(snapshot.child("chat").getChildrenCount()+1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final  String getTxtMessage=messageEditText.getText().toString();
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}