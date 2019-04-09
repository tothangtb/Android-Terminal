package com.example.v0932593.tm_billy;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class  Utils {


    public static void writeToSDFile(String file_name, String data){

        // lay duong dan thu muc
        File dir = new File ( Environment.getExternalStorageDirectory() + "/ThangCoder");
        // Tao folder
        dir.mkdirs();
        // tao file
        File file = new File(dir, file_name);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            String logW = "";
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);

            // ghi noi dung
            pw.append("=================LOG_FILE_SCAN_================\n");
            //Log.d("show",contentSend.toString());


            logW+="Scan : "+data+"\n"+"Reply : "+data+"\n";
            pw.append(logW.toString());
            // Log.d("DisplayTest",contentRespond.getText().toString());
            pw.append("==============================================\n");
            // dong file
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Log.i("THANGCODER", "******* File not found. Did you" + " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d("THANGCODER","\n\nFile written to "+file);
    }



    public static String ReadFile(String file_name){
        //Find the directory for the SD Card using the API
        File sdcard = Environment.getExternalStorageDirectory();

//Get the text file
        //"ThangCoder/Log_5.txt"
        File file = new File(sdcard,file_name);

//Read text from file
        StringBuilder log = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                log.append(line);
                log.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            e.printStackTrace();
        }
        return log.toString();
        //Toast.makeText(getBaseContext(),log.toString(),Toast.LENGTH_SHORT).show();
    }
}
