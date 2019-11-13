package com.astapan.lab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    DbHelper db;
    ListView lvUsers;
    ArrayList<HashMap<String, String>> all_users;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DbHelper(this);
        lvUsers = findViewById(R.id.lvUsers);
        fetch_users();
    }

    private void fetch_users() {
        all_users = db.getAllUsers();
        adapter = new ListViewAdapter(this, R.layout.adapter_users, all_users);
        lvUsers.setAdapter(adapter);
    }

    private class ListViewAdapter extends ArrayAdapter {

        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> all_users;
        TextView tvFullname , tvUsername;
        ImageView ivEdit, ivDelete;

        public ListViewAdapter(Context context, int resource, ArrayList<HashMap<String, String>> all_users){
            super(context, resource, all_users);
            inflater = LayoutInflater.from(context);
            this.all_users = all_users;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.adapter_users, parent,false);
            tvFullname = convertView.findViewById(R.id.tvFullname);
            tvUsername = convertView.findViewById(R.id.tvUsername);
            ivEdit = convertView.findViewById(R.id.ivEdit);
            ivDelete = convertView.findViewById(R.id.ivDelete);

            tvFullname.setText(all_users.get(position).get(db.TBL_USER_FULLNAME));
            tvUsername.setText(all_users.get(position).get(db.TBL_USER_USERNAME));

            final int userid = Integer.parseInt(all_users.get(position).get(db.TBL_USER_ID));
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    db.deleteUser(userid);
                    Toast.makeText(HomeActivity.this, "Deleted !", Toast.LENGTH_SHORT).show();
                    fetch_users();
                }
            });

            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, EditUserActivity.class);
                    intent.putExtra(db.TBL_USER_ID, userid);
                    startActivity(intent);

                }
            });

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        fetch_users();
        super.onResume();
    }
}
