package com.example.notebook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements bottomSheet.onItemClickDeleteListener{
public static final String TAG = "Main";
Activity activity;
ActionBar toolbar;
static FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar_settings));
        activity = this;
        recyclerviewFragment recyclerviewFragment = new recyclerviewFragment();
        replaceFragment(recyclerviewFragment);

    }

    public void replaceFragment(Fragment fragment){
        fm = getSupportFragmentManager();
        Log.e(TAG, "Main Activity : "+getSupportFragmentManager().toString());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer,fragment,"recyclerFrag");
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onItemDelete(int position) {
        MainActivity activity = new MainActivity();
        activity.onItemClick(position);
    }

//    @Override
//    public void onBackPressed() {
//        if (new createNote().isInLayout()){
//            replaceFragment(new recyclerviewFragment());
//        }
//        else
//            finish();
//        super.onBackPressed();
//    }
    public void onItemClick(int position){

        Log.e(TAG, "fragment Manager : "+fm.toString());
        Log.e(TAG, "find fragment : "+fm.findFragmentById(R.id.recyclerViewFragment));
        recyclerviewFragment fragment = (recyclerviewFragment)fm.findFragmentByTag("recyclerFrag");
        fragment.onItemClick(position);
    }
}
