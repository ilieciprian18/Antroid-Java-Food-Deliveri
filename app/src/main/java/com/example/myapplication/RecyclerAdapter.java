package com.example.myapplication;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RecyclerAdapter";
    List<String> foodList;
    List<String> foodListAll;
    boolean[] isOrdered;
    boolean ordered;

    public RecyclerAdapter(List<String> foodList) {
        this.foodList = foodList;
        isOrdered = new boolean[foodList.size()];
        foodListAll = new ArrayList<>();
        foodListAll.addAll(foodList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] titlePriceArray = foodList.get(position).split(";");
        String title = titlePriceArray[0];
        String price = titlePriceArray[1];
        holder.rowCountTextView.setText(price);
        holder.textView.setText(title);

        if(isOrdered[position]) {
            holder.imageOrdered.setVisibility(View.VISIBLE);
        } else {
            holder.imageOrdered.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<String> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(foodListAll);
            } else {
                for (String movie: foodListAll) {
                    if (movie.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            foodList.clear();
            foodList.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();
        }

    };

    public void filterList(ArrayList<String> filteredList){
        //foodList.clear();
        foodList = filteredList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView, imageOrdered;
        TextView textView, rowCountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            imageOrdered = itemView.findViewById(R.id.imageOrdered);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            int listSize = foodList.size();
            Context context = view.getContext();

            if (position == listSize - 1) {
                if(ordered) {
                    StringBuilder selections = new StringBuilder();
                    for (int i = 0; i < listSize; ++i) {
                        if (isOrdered[i]) {
                            selections.append(foodList.get(i));
                            selections.append(":");
                        }
                    }

                    String singleString = selections.toString();

                    Intent i = new Intent(context, Order.class);
                    i.putExtra("order", singleString);
                    context.startActivity(i);
                }
                else {
                    Toast.makeText(view.getContext(), "Please select an item.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(view.getContext(), foodList.get(position).split(";")[0], Toast.LENGTH_SHORT).show();
                isOrdered[position] = !isOrdered[position];
                ordered = true;
                notifyDataSetChanged();
            }
        }
    }
}















