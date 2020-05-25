package com.example.notebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class createNote extends Fragment {
public static final String TAG = "Main";
private EditText editNote;
public String note;
public int position = 0;
public boolean clickedItem = false;
    public createNote() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.e(TAG, "onAttach: onAttach ");
        if (getArguments() != null){
            Log.e(TAG, "onAttach: NOT NULL");
        }
        else
            Log.e(TAG, "onAttach NULL");
        super.onAttach(context);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.save:
               String note = editNote.getText().toString().trim();
               if (!note.isEmpty()) {
                   Log.e(TAG, "save clicked");
                   Log.e(TAG, "NOTE : " + editNote.getText().toString());
                   boolean fromSave = true;
                   Log.e(TAG, "onOptionsItemSelected: Position : " + position);

                   Bundle bundle = new Bundle();
                   if (clickedItem) {
                       Log.e(TAG, "clickedItem");
                       bundle.putInt("position", position);
                       bundle.putBoolean("clickedItem", true);
                   }
                   bundle.putString("note", editNote.getText().toString());
                   replaceFragment(new recyclerviewFragment(), fromSave, bundle);
               }
               else{
                   editNote.requestFocus();
               }
               break;
           case R.id.delete:
               Log.e(TAG,"delete clicked");
               replaceFragment(new recyclerviewFragment(),false,new Bundle());
               break;
           default:
               break;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e(TAG, "onActivityResult: of createNote");

        if (requestCode == 0 && resultCode == Activity.RESULT_OK){
            position = data.getExtras().getInt("position");
            note = data.getExtras().getString("note");
            clickedItem = data.getExtras().getBoolean("clickedItem",false);
            Log.e(TAG, "onActivityResult: "+note+" Position : "+position);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void replaceFragment(Fragment fragment, boolean fromSave,Bundle bundle){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (fromSave) {
            Log.e(TAG, "replaceFragment: "+bundle.toString());
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent().putExtras(bundle));
        }
        ft.replace(R.id.fragmentContainer,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_note, container, false);
        setHasOptionsMenu(true);
        editNote = v.findViewById(R.id.editNote);
        editNote.requestFocus();
        if (clickedItem)
            editNote.setText(note);
        else
            editNote.setText("");
//        String edit = getActivity().getIntent().getStringExtra("editNote");
//        if (!edit.isEmpty()){
//            editNote.setText(edit);
//        }
        return v;
    }

}
