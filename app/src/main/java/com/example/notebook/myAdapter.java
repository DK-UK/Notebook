package com.example.notebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {
    public static ArrayList<Items> itemsArrayList;
    Context context;
    public static final String TAG = "Main";
    private onItemClickListener mListener;
    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }
    public myAdapter(Context context,ArrayList<Items> itemsArrayList){
        Log.e(TAG, "myAdapter");
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }
    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView noteTextView,noteTime;
        ImageView imageView;
        CardView cardView;
        public myViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.notesTextView);
            noteTime = itemView.findViewById(R.id.noteTime);
            imageView = itemView.findViewById(R.id.delete);
            cardView = itemView.findViewById(R.id.cardview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick:");
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_items,parent,false);
        myViewHolder myViewHolder = new myViewHolder(view,mListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder:");
        final Items items = itemsArrayList.get(position);
        holder.noteTime.setText(items.getTime());
        holder.noteTextView.setText(items.getNote());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet bottomSheet = new bottomSheet(position);
                Log.e(TAG, "recycler : "+((AppCompatActivity)context).getSupportFragmentManager().toString());
                bottomSheet.show(((AppCompatActivity)context).getSupportFragmentManager(),"NoteBook");
                Log.e(TAG, "delete clicked");
//                int position = holder.getAdapterPosition();
//                itemsArrayList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position,itemsArrayList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public void replaceFragment(Fragment fragment){

//        FragmentTransaction ft = .beginTransaction();
//        ft.replace(R.id.fragmentContainer,fragment);
//        ft.addToBackStack(null);
//        ft.commit();
    }

    public static ArrayList<Items> getArrayList(){
        return itemsArrayList;
    }

}
