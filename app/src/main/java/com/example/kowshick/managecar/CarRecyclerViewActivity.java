package com.example.kowshick.managecar;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kowshick.managecar.database.DatabaseSource;

import java.util.ArrayList;
import java.util.List;

public class CarRecyclerViewActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private CarAdapter adapter;
    private CarInformation carInfo;
    DatabaseSource db;
    private Session session;
    private List<CarInformation> cars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_recycler_view);
        /*Intent intent = getIntent();
        if(intent.getAction().equals(Intent.ACTION_SEARCH)){
            String query = intent.getStringExtra(SearchManager.QUERY);
            //Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }*/

        recyclerview = findViewById(R.id.recyclerView);

        session = new Session(this);
        db = new DatabaseSource(this);
        carInfo = new CarInformation();
        cars = db.getAllCars();
        if(cars.size()==0){
            ((TextView)findViewById(R.id.showEmptyListMsgTV)).setVisibility(View.VISIBLE);
        }
        adapter = new CarAdapter(this,cars,recyclerview);
        GridLayoutManager glm = new GridLayoutManager(this,1);
        recyclerview.setLayoutManager(glm);
        /*LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);*/
        recyclerview.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchItem).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Car");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }




    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem addItem = menu.findItem(R.id.itemAdd);
        MenuItem loginItem = menu.findItem(R.id.itemLogin);
        MenuItem logoutItem = menu.findItem(R.id.itemLogout);
        if (session.loggedin()) {
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
        } else {
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                if (session.loggedin()) {
                    startActivity(new Intent(CarRecyclerViewActivity.this, CarRegistrationActivity.class));
                }
                else
                    displayToast("Log in first");
                break;
            case R.id.itemLogin:
                startActivity(new Intent(CarRecyclerViewActivity.this,LogInActivity.class));
                break;
            case R.id.itemLogout:
                session.setLogedin(false);
                startActivity(new Intent(CarRecyclerViewActivity.this,LogInActivity.class));
                break;
        }

            return super.onOptionsItemSelected(item);
        }
    private void displayToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    }

