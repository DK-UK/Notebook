package com.example.notebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class recyclerviewFragment extends Fragment{
    RecyclerView recyclerView;
    myAdapter adapter;
    ArrayList<Items> itemsArrayList;
    FloatingActionButton floatingActionButton;
    String note;
    int request_code = 0;
    public static final String TAG = "Main";
    public recyclerviewFragment() {
        // Required empty public constructor
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult: of recyclerviewFragment");
        int position = -1;
        boolean clickedItem = false;
        position = data.getExtras().getInt("position",-1);
        Log.e(TAG, "check position : "+position);
        String note = data.getExtras().getString("note");
        clickedItem = data.getExtras().getBoolean("clickedItem");

        if (requestCode == 0 && resultCode == Activity.RESULT_OK){

            Log.e(TAG, "onActivityResult: POSITION : "+position);
            String time = getTime();
            if (clickedItem) {
                itemsArrayList.set(position,new Items(note,time));
                adapter.notifyItemChanged(position);
            }
            else {
                itemsArrayList.add(new Items(note,time));
                adapter.notifyItemInserted(position);
            }

        }
            Log.e(TAG, "onActivityResult: "+note);
            Log.e(TAG, "onActivityResult: Size : "+itemsArrayList.size());
        }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(TAG, "onCreateView:");

        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        Log.e(TAG, "RecyclerViewFragment : "+getActivity().getSupportFragmentManager().toString());
        Log.e(TAG, "onCreateView: "+itemsArrayList.size());
        floatingActionButton = v.findViewById(R.id.floatingButton);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        Log.e(TAG,"Array Size : "+itemsArrayList.size());
        adapter = new myAdapter(getContext(),itemsArrayList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new myAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Log.e(TAG, "onItemClick: ");
                Items currentItem = itemsArrayList.get(position);
                Bundle bundle = new Bundle();
                bundle.putBoolean("clickedItem",true);
                bundle.putInt("position",position);
                bundle.putString("note",currentItem.getNote());
                createNote createNote = new createNote();
                replaceFragment(createNote,bundle,true);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new createNote(),new Bundle(),false);
            }
        });
        Log.e(TAG,"Array elements : "+itemsArrayList.indexOf(1));
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.e(TAG, "onAttach:");
        loadData();
        if (myAdapter.getArrayList() != null){
            this.itemsArrayList = myAdapter.getArrayList();
        }
        super.onAttach(context);
    }


    @Override
    public void onDestroyView() {
        saveData();
        super.onDestroyView();
    }

    public void replaceFragment(Fragment fragment, Bundle bundle, boolean fromItemClick){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        fragment.setTargetFragment(recyclerviewFragment.this,0);
        if (fromItemClick) {
            fragment.onActivityResult(request_code, Activity.RESULT_OK, new Intent().putExtras(bundle));
        }
        ft.replace(R.id.fragmentContainer,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void saveData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("save",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemsArrayList);

        editor.putString("noteList",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("save",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("noteList",null);

        Type type = new TypeToken<ArrayList<Items>>() {}.getType();

        itemsArrayList = gson.fromJson(json,type);

        if (itemsArrayList == null){
            itemsArrayList = new ArrayList<>();
        }
    }

    public String getTime(){
        Calendar calendar = GregorianCalendar.getInstance();
        Date date = calendar.getTime();
        String time = DateFormat.getDateTimeInstance().format(date.getTime());
        Log.e(TAG, "Date : "+DateFormat.getDateInstance().format(date.getTime()));
        return time;
    }

    public void onItemClick(int position){
        Log.e(TAG, "onItemClick: RecyclerviewFragment :"+position);
    try {
        itemsArrayList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, itemsArrayList.size());
        adapter.notifyDataSetChanged();
        getActivity().recreate();
    }
    catch (IndexOutOfBoundsException e){
        Log.e(TAG, "Index Out of Bound : "+e.getMessage());
    }
    }

}
