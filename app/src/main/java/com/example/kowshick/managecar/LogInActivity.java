package com.example.kowshick.managecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kowshick.managecar.database.DatabaseSource;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnLog,btnReg,btnlist;
    private EditText etEmail,etPass;
    private DatabaseSource db;
    private Session session;
    private boolean isLoggedin=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        session=new Session(this);
        db=new DatabaseSource(this);
        btnLog=findViewById(R.id.btnLogIn);
        btnReg=findViewById(R.id.btnReg);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPassword);
        btnlist=findViewById(R.id.btnlist);
        btnLog.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        btnlist.setOnClickListener(this);
        if (session.loggedin()){
            startActivity(new Intent(LogInActivity.this,CarRecyclerViewActivity.class));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogIn:
                isLoggedin=true;
                login();
                break;
            case R.id.btnReg:
               startActivity(new Intent(LogInActivity.this,RegisterActivity.class));
                break;
            case R.id.btnlist:
                startActivity(new Intent(LogInActivity.this,CarRecyclerViewActivity.class));
                break;
            default:
                break;
        }

    }



    public void login(){
        String emil=etEmail.getText().toString();
        String pass=etPass.getText().toString();
        if (db.getUser(emil,pass)){
            session.setLogedin(true);
            Toast.makeText(this, "Log in Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LogInActivity.this,CarRecyclerViewActivity.class));

        }
        else{
            Toast.makeText(this, "Wrong Email and password", Toast.LENGTH_SHORT).show();
        }
    }
}
