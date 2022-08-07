package com.codelancer.notesreminder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Helper {
    @NonNull
    public static String parseString(@NonNull String string, int length){
        return string.trim().length() > length ? string.trim().substring(0, length)+"..." : string.trim();
    }

    public static void exportFile(@NonNull Context context, String name, String body){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            askPermission(context);

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!root.exists()) root.mkdirs();
        File file = new File(root, name+".txt");//
        try {
            FileOutputStream fileWriter = new FileOutputStream(file);
            fileWriter.write(body.getBytes());
            fileWriter.flush();
            fileWriter.close();
            Toast.makeText(context, R.string.note_saved, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("testing", e.toString());
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void askPermission(Context context){
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    public static String getTimeString(String string){
        String time_div = "", div = "";
        int hr = Integer.parseInt(string.split("-")[0]);
        int mn = Integer.parseInt(string.split("-")[1]);

        if(hr>12) {
            hr = hr-12;
            div = " PM";
        }else div = " AM";


        if (hr<10) time_div = "0" + hr;
        else time_div = String.valueOf(hr);

        if(mn<10) time_div = time_div + ":0" + mn;
        else time_div = time_div + ":" + mn;

        return time_div+div;
    }

}
