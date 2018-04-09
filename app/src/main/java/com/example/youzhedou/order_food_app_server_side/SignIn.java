package com.example.youzhedou.order_food_app_server_side;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.youzhedou.order_food_app_server_side.Common.Common;
import com.example.youzhedou.order_food_app_server_side.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    Button buttonSignIn;
    EditText editTel, editPwd;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        editPwd = (EditText) findViewById(R.id.editPwd);
        editTel = (EditText) findViewById(R.id.editTel);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInUser(editTel.getText().toString(),
                        editPwd.getText().toString());
            }
        });
    }

    private void SignInUser(String tel, String pwd) {
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please wait...");
        mDialog.show();


        final String localTel = tel;
        final String localPwd = pwd;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(localTel).exists()){
                    mDialog.dismiss();
                    User user = dataSnapshot.child(localTel).getValue(User.class);
                    user.setTel(localTel);
                    if(Boolean.parseBoolean(user.getIsStaff())){
                        if(user.getPassword().equals(localPwd)){
                            //log in ok
                            Intent homeIntent = new Intent(SignIn.this,Home.class);
                            Common.currentUser = user;
                            startActivity(homeIntent);
                            finish();
                        }
                        else {
                            Toast.makeText(SignIn.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(SignIn.this, "Not Staff Account", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignIn.this, "Not such user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
