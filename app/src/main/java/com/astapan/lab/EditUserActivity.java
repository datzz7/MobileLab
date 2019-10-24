package com.astapan.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;

public class EditUserActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etFullname;
    DbHelper db;
    String username,password,fullname;
    int formsuccess, userid;

    ArrayList<HashMap<String, String>> select_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        db = new DbHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFullname = findViewById(R.id.etFullname);


        Intent intent = getIntent();
        userid = intent.getIntExtra(db.TBL_USER_ID, 0);

        select_user = db.getSelectUser(userid);

        etUsername.setText(select_user.get(0).get(db.TBL_USER_USERNAME));
        etPassword.setText(select_user.get(0).get(db.TBL_USER_PASSWORD));
        etFullname.setText(select_user.get(0).get(db.TBL_USER_FULLNAME));

    }
}
