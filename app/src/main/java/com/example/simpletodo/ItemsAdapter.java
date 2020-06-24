package com.example.simpletodo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    List<String> items;

    public ItemsAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    // creates each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // use layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        // wrap it inside a View Holder and return it
        return new ViewHolder(todoView);
    }

    @Override
    // taking data @ a position and putting it into the view holder
    // binding data to a view holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // grab item @ position
        String item = items.get(position);

        // bind item into specified view holder
        holder.bind(item);
    }

    @Override
    // # items in data
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // update view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);
        }
    }

}
