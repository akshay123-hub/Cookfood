package com.example.demoproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    List<Model> userModel;
    Context context;
    List<Model> backup;

    boolean check=true;

    public Adapter(List<Model> userModel,Context context) {
        this.userModel = userModel;
        this.context=context;
        backup = new ArrayList<>(userModel);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(userModel.get(position).getImg());
        holder.text.setText(userModel.get(position).getName());
        holder.priceText.setText(userModel.get(position).getPrice());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ItemActivity.class);
                i.putExtra("image",userModel.get(position).getImg());
                i.putExtra("name",userModel.get(position).getName());
                i.putExtra("price",userModel.get(position).getPrice());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.favText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                    if (check){
                        holder.favText.setBackgroundResource(R.drawable.favorite);
                        Toast.makeText(context, "Add to Favorite ! ", Toast.LENGTH_SHORT).show();
                        check=false;
                    }else{
                        holder.favText.setBackgroundResource(R.drawable.blank_favorite);
                        Toast.makeText(context, "Remove Favorite ! ", Toast.LENGTH_SHORT).show();
                        check=true;
                    }
                }else{
                    Toast.makeText(context, "Please Login !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModel.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView text,favText,priceText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            text=itemView.findViewById(R.id.recipeName);
            favText=itemView.findViewById(R.id.fav);
            priceText=itemView.findViewById(R.id.price);
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Model> filterData=new ArrayList<>();
            if (keyword.toString().isEmpty()){
                filterData.addAll(backup);
            }else{
                for (Model obj:backup){
                    if (obj.getName().toString().toLowerCase().contains(keyword.toString().toLowerCase())){
                        filterData.add(obj);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterData;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            userModel.clear();
            userModel.addAll((ArrayList<Model>)results.values);
            notifyDataSetChanged();
        }
    };
}
