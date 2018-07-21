package com.example.kowshick.managecar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kowshick.managecar.R;
import com.example.kowshick.managecar.CarRegistrationActivity;
import com.example.kowshick.managecar.database.DatabaseSource;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> implements Filterable{
    private List<CarInformation>carList;
    private List<CarInformation> filteredList;
    Context c;
    private RecyclerView mRecyclerV;
    Session session;


    public CarAdapter(Context ctx,List<CarInformation>carList,RecyclerView mRecyclerV)
    {
        this.c=ctx;
        this.carList = carList;
        this.mRecyclerV=mRecyclerV;
        filteredList=carList;

    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.car_row,parent,false);
        return new CarViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final CarViewHolder holder, final int position) {
        //final CarInformation person = filteredList.get(position);
        holder.carType.setText(filteredList.get(position).getCarType());
        holder.carModel.setText(filteredList.get(position).getCarModel());
        holder.carPrice.setText(Double.toString(filteredList.get(position).getPrice()));

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarInformation fc =filteredList.get(position);
                Intent intent = new Intent(c, CarDetailsActivity.class);
                intent.putExtra("msg", fc);
                c.startActivity(intent);
            }
        });

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (new Session(c).loggedin()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        builder.setTitle("Choose option");
                        builder.setMessage("Update or delete car?");
                        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //go to update activity
                                if(filteredList.size()>0) {
                                    int rowId = filteredList.get(position).getCarId();

                                    goToUpdateActivity(rowId);
                                }

                            }
                        });
                        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                                builder.setTitle("Are You sure?");
                                builder.setMessage("Yes or No?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DatabaseSource dbHelper = new DatabaseSource(c);
                                        dbHelper.deleteCar(filteredList.get(position).getCarId());

                                        filteredList.remove(position);
                                        mRecyclerV.removeViewAt(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, filteredList.size());
                                        notifyDataSetChanged();
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();

                    } else {
                        Toast.makeText(c, "If you want to update or delete car Log in first", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if(query.isEmpty()){
                    filteredList = carList;
                }else{
                    List<CarInformation>tempList = new ArrayList<>();
                    for(CarInformation m : carList){
                        if(m.getCarType().toLowerCase().contains(query.toLowerCase()) ||
                                m.getCarModel().toLowerCase().contains(query.toLowerCase())){
                            tempList.add(m);
                        }
                    }
                    filteredList = tempList;

                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<CarInformation>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public class CarViewHolder extends RecyclerView.ViewHolder  {
        TextView carType,carPrice,carModel;
        Button details;



        public View layout;
        public CarViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            carType=itemView.findViewById(R.id.tvType);
            carModel=itemView.findViewById(R.id.tvModel);
            carPrice=itemView.findViewById(R.id.tvPrice);
            details=itemView.findViewById(R.id.detailbtn);


        }

    }
    public void add(int position, CarInformation person) {
        filteredList.add(position, person);
        notifyItemInserted(position);
    }
    public void remove(int position) {
        filteredList.remove(position);
        notifyItemRemoved(position);
    }
   private void goToUpdateActivity(int carId){
        Intent goToUpdate = new Intent(c,CarRegistrationActivity.class);
        goToUpdate.putExtra("rowId", carId);
        c.startActivity(goToUpdate);
    }

}


