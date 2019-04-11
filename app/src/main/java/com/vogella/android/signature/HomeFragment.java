package com.vogella.android.signature;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.Signature;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.github.gcacace.signaturepad.views.SignaturePad;
import android.graphics.Bitmap;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import android.content.Intent;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;


public class HomeFragment extends Fragment  {


    private TextView mTextMessage;
    private SignaturePad mSignaturePad;

    private FloatingActionButton mClearButton;
    private FloatingActionButton mSaveButton;
    private int currentBackgroundColor = 0xffffffff;
    private SharedPreferences preferences;
    private String mSelectedColor;
    private int mSelectedSize;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

         View view=inflater.inflate(R.layout.activity_home, null);
        getActivity().setTitle(R.string.signature_title);
        mTextMessage = (TextView) view.findViewById(R.id.message);
        mSignaturePad = (SignaturePad) view.findViewById(R.id.signature_pad);


        pickColor(container);
        pickSize();

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
//               mSignaturePad.mMinWidth=20;
            }
        });

        mClearButton = (FloatingActionButton) view.findViewById(R.id.fabClear);
        mSaveButton = (FloatingActionButton) view.findViewById(R.id.fabSave);

         mSaveButton.setEnabled(false);
         mClearButton.setEnabled(false);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               clearAlertDialoge(view);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                open(view);
            }
        });



        return view;
    }


    private void pickSize(){
        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mSelectedSize= preferences.getInt("pencil_size",2);
        mSignaturePad.mMaxWidth = mSelectedSize;

    }

    private void pickColor(ViewGroup container){
        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mSelectedColor = preferences.getString("pencil_color","ddssd");



        int color;

        switch (mSelectedColor) {

            case "red":
                color = ContextCompat.getColor(container.getContext(), R.color.color_pick_red);
                mSignaturePad.mPaint.setColor(color);
                break;

            case "orange":
                color = ContextCompat.getColor(container.getContext(), R.color.color_pick_orange);
                mSignaturePad.mPaint.setColor(color);
                break;
            case "yellow":
                color = ContextCompat.getColor(container.getContext(), R.color.color_pick_yellow);
                mSignaturePad.mPaint.setColor(color);
                break;

            case "green":
                color = ContextCompat.getColor(container.getContext(), R.color.color_pick_green);
                mSignaturePad.mPaint.setColor(color);
                break;
            case "cyan":
                color = ContextCompat.getColor(container.getContext(), R.color.color_pick_cyan);
                mSignaturePad.mPaint.setColor(color);
                break;
            case "blue":
                color = ContextCompat.getColor(container.getContext(), R.color.color_pick_blue);
                mSignaturePad.mPaint.setColor(color);
                break;
            case "purple":
                color = ContextCompat.getColor(container.getContext(), R.color.color_pick_purple);
                mSignaturePad.mPaint.setColor(color);
                break;
            case "pink":
                color = ContextCompat.getColor(container.getContext(), R.color.color_pick_pink);
                mSignaturePad.mPaint.setColor(color);
                break;
            default:
                color = ContextCompat.getColor(container.getContext(), R.color.colorPrimary);
                mSignaturePad.mPaint.setColor(color);
                break;
        }
    }



    public void open(final View view){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getContext());

        edittext.setWidth(20);


        alertDialogBuilder.setMessage("Signature Owner Name");


        alertDialogBuilder.setView(edittext);

                alertDialogBuilder.setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                if(edittext.getText().toString().isEmpty()){
                                   edittext.setHint("please enter name");
                                }
                                else {
                                    saveImage(edittext.getText().toString());
                                }


                            }
                        });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void clearAlertDialoge(View view){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());


        alertDialogBuilder.setMessage("Clear Signature ?");

        alertDialogBuilder.setPositiveButton("Clear",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        mSignaturePad.clear();
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    

    public void saveImage(String name){
                        Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                if (addJpgSignatureToGallery(signatureBitmap,name)) {
                   // Toast.makeText(HomeFragment.this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),R.string.signature_saved_to_gallery,Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(MainActivity.this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),R.string.unable_to_store_the_signature,Toast.LENGTH_SHORT).show();
                }
                if (addSvgSignatureToGallery(mSignaturePad.getSignatureSvg())) {
                  //  Toast.makeText(MainActivity.this, "SVG Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),R.string.svg_signature_saved_into_gallery,Toast.LENGTH_SHORT).show();
                } else {
                   // Toast.makeText(MainActivity.this, "Unable to store the SVG signature", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),R.string.unable_to_store_the_svg_signature,Toast.LENGTH_SHORT).show();
                }
    }



    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature,String name) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format(name+"_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
