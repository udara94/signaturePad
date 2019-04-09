package com.vogella.android.signature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;
import android.content.SharedPreferences;
import android.widget.SeekBar;


public class SettingFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences preferences;
    private  SeekBar seekBar;
    private int mSelectedSize;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_setting, null);


        getActivity().setTitle(R.string.setting_title);

        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);




        seekBar=(SeekBar) view.findViewById(R.id.seekBar);
//        seekBar.setProgress();

        setProgress();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                SharedPreferences.Editor prefsEditor = preferences.edit();
                Toast.makeText(getActivity(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();

                prefsEditor.putInt("pencil_size",progress);
                prefsEditor.apply();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        FloatingActionButton fabRed = (FloatingActionButton) view.findViewById(R.id.fab_red);
        FloatingActionButton fabOrange = (FloatingActionButton) view.findViewById(R.id.fab_orange);
        FloatingActionButton fabYellow = (FloatingActionButton) view.findViewById(R.id.fab_yellow);
        FloatingActionButton fabGreen = (FloatingActionButton) view.findViewById(R.id.fab_green);
        FloatingActionButton fabCyan = (FloatingActionButton) view.findViewById(R.id.fab_cyan);
        FloatingActionButton fabBlue = (FloatingActionButton) view.findViewById(R.id.fab_blue);
        FloatingActionButton fabPurple = (FloatingActionButton) view.findViewById(R.id.fab_purple);
        FloatingActionButton fabPink = (FloatingActionButton) view.findViewById(R.id.fab_pink);


        fabRed.setOnClickListener(this);
        fabOrange.setOnClickListener(this);
        fabYellow.setOnClickListener(this);
        fabGreen.setOnClickListener(this);
        fabCyan.setOnClickListener(this);
        fabBlue.setOnClickListener(this);
        fabPurple.setOnClickListener(this);
        fabPink.setOnClickListener(this);

        return view;
    }

    private void setProgress(){
        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mSelectedSize= preferences.getInt("pencil_size",2);
        seekBar.setProgress(mSelectedSize);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor prefsEditor = preferences.edit();

        int id= v.getId();

        switch (id) {

            case R.id.fab_red:
                Toast.makeText(getActivity(), R.string.color_red_selected, Toast.LENGTH_SHORT).show();
                prefsEditor.putString("pencil_color","red");
                prefsEditor.apply();
                break;

            case R.id.fab_orange:
                Toast.makeText(getActivity(), R.string.color_orange_selected, Toast.LENGTH_SHORT).show();
                prefsEditor.putString("pencil_color","orange");
                prefsEditor.apply();
                break;
            case R.id.fab_yellow:
                Toast.makeText(getActivity(), R.string.color_yellow_selected, Toast.LENGTH_SHORT).show();
                prefsEditor.putString("pencil_color","yellow");
                prefsEditor.apply();
                break;

            case R.id.fab_green:
                Toast.makeText(getActivity(), R.string.color_green_selected, Toast.LENGTH_SHORT).show();
                prefsEditor.putString("pencil_color","green");
                prefsEditor.apply();
                break;
            case R.id.fab_cyan:
                Toast.makeText(getActivity(), R.string.color_cyan_selected, Toast.LENGTH_SHORT).show();
                prefsEditor.putString("pencil_color","cyan");
                prefsEditor.apply();
                break;
            case R.id.fab_blue:
                Toast.makeText(getActivity(), R.string.color_blue_selected, Toast.LENGTH_SHORT).show();
                prefsEditor.putString("pencil_color","blue");
                prefsEditor.apply();
                break;
            case R.id.fab_purple:
                Toast.makeText(getActivity(), R.string.color_purple_selected, Toast.LENGTH_SHORT).show();
                prefsEditor.putString("pencil_color","purple");
                prefsEditor.apply();
                break;
            case R.id.fab_pink:
                Toast.makeText(getActivity(), R.string.color_pink_selected, Toast.LENGTH_SHORT).show();
                prefsEditor.putString("pencil_color","pink");
                prefsEditor.apply();
                break;
        }

        }

    public void ButtonClicked(View v){


    }

}
