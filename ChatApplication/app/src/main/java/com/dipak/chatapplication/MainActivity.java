package com.dipak.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dipak.chatapplication.messages.MessagesAdapter;
import com.dipak.chatapplication.messages.MessagesList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private final List<MessagesList> messagesLists=new ArrayList<>();
    private  String mobile;
    private  String email;
    private  String name;
    private  int unseenMessages=0;
    private  String lastMessage="";
    private  String chatKey="";
    private RecyclerView messagesRecyclerView;
    private MessagesAdapter messagesAdapter;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatapp-48d54-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircleImageView userProfilePic=findViewById(R.id.userProfilePic);
        messagesRecyclerView=findViewById(R.id.messageRecycleView);

        //get intent data from register.class activity

        mobile=getIntent().getStringExtra("mobile");
        email=getIntent().getStringExtra("email");
        name=getIntent().getStringExtra("name");

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter to recyclerview
        messagesAdapter=new MessagesAdapter(messagesLists, MainActivity.this);


        messagesRecyclerView.setAdapter(messagesAdapter);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        //get profile pic from firebase database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String profilePicUrl=snapshot.child("users").child(mobile).child("profile_pic").getValue(String.class);

                if(!profilePicUrl.isEmpty())
                {
                //set profile pic to circle image view
                Picasso.get().load(profilePicUrl).into(userProfilePic);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesLists.clear();
                unseenMessages=0;
                lastMessage="";
                chatKey="";
                for (DataSnapshot dataSnapshot:snapshot.child("users").getChildren())
                {
                    final String getMobile=dataSnapshot.getKey();

                    if(!getMobile.equals(mobile))
                    {
                        final String getName=dataSnapshot.child("name").getValue(String.class);
                        final String getProfilePic=dataSnapshot.child("profile_pic").getValue(String.class);

                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCounts=(int)snapshot.getChildrenCount();
                                if(getChatCounts>0)
                                {
                                    for (DataSnapshot dataSnapshot1:snapshot.getChildren())
                                    {
                                        final  String getKey=dataSnapshot1.getKey();
                                        chatKey=getKey;
                                        final String getUserOne=dataSnapshot1.child("user_1").getValue(String .class);
                                        final String getUserTwo=dataSnapshot1.child("user_2").getValue(String .class);

                                        if((getUserOne.equals(getMobile) && getUserTwo.equals(mobile)) ||
                                                (getUserOne.equals(mobile) && getUserTwo.equals(getMobile) ))
                                        {
                                            for (DataSnapshot chatDataSnapshopt : dataSnapshot1.child("messages").getChildren() )
                                            {
                                                final long getMessageKey= Long.parseLong(chatDataSnapshopt.getKey());
                                                final long getLastSeenMessage =Long.parseLong(MemoryData.getLastMsgTS(MainActivity.this,getKey));

                                                lastMessage=chatDataSnapshopt.child("msg").getValue(String.class);
                                                if(getMessageKey>getLastSeenMessage)
                                                {
                                                 unseenMessages++;
                                                }
                                            }
                                        }
                                    }
                                }
                                MessagesList messagesList=new MessagesList(getName, getMobile,lastMessage, getProfilePic,unseenMessages, chatKey);
                                messagesLists.add(messagesList);
                                messagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists, MainActivity.this));
                                messagesAdapter.updateData(messagesLists);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}