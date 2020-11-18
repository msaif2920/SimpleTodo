package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//adapter is responsible for putting a list and putting it into the view holder
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    public interface OnLongClickListener{
        void onItemLongClick(int position);
    }

    private ArrayList<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    public ItemAdapter(ArrayList<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener =longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View ourView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(ourView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String item = items.get(position);
      holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{

        TextView itemShow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemShow = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {
            itemShow.setText(item);

            itemShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });

            //handle Delete for listView
            itemShow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClick(getAdapterPosition());
                    return false;
                }
            });
        }



    }

}
