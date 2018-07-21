package com.example.kowshick.managecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kowshick.managecar.database.DatabaseSource;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnReg;
    private EditText etEmail,etPass,etType;
    private TextView tvBack;
    private DatabaseSource db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=new DatabaseSource(this);
        btnReg=findViewById(R.id.btnReg);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPassword);
        etType=findViewById(R.id.etType);
        tvBack=findViewById(R.id.tvLogIn);
        btnReg.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReg:
                register();
                break;
            case R.id.tvLogIn:
                startActivity(new Intent(RegisterActivity.this,LogInActivity.class));
                break;
            default:
                break;
        }

    }
    public void register(){
        String emil=etEmail.getText().toString();
        String pass=etPass.getText().toString();
        String type=etType.getText().toString();
        if(emil.isEmpty()&& pass.isEmpty()){
            displayToast("UserName/Password field empty");
        }
        else if(!pass.matches(type)){
            displayToast("Password not matched");
            startActivity(new Intent(this,RegisterActivity.class));
        }
        else
            db.addUser(emil,pass,type);
        displayToast("User Registered");
        finish();
    }
    private void displayToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    }

