package com.rikardo308.sharecontacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import static android.Manifest.permission.READ_CONTACTS;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;
    ArrayList<String> vCard ;
    String vfile;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vfile = "Contacts" + "_" + System.currentTimeMillis()+".vcf";
        //getRequestPermission();
    }

    private void getRequestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }else{
            getVcardString();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getVcardString();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getVcardString(){
        vCard = new ArrayList<String>();
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if(cursor!=null&&cursor.getCount()>0) {
                cursor.moveToFirst();
                for(int i =0;i<cursor.getCount();i++)
                {

                    get(cursor);
                    Log.d("TAG", "Contact "+(i+1)+"VcF String is"+vCard.get(i));
                    cursor.moveToNext();
                }

            }
            else{
                Log.d("TAG", "No Contacts in Your Phone");
            }
        }catch(Exception e){
            Log.d("TAG", e.getMessage());
        }
    }

    public void get(Cursor cursor){
        String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
        AssetFileDescriptor fd;
        try {
            fd = this.getContentResolver().openAssetFileDescriptor(uri, "r");

            // Your Complex Code and you used function without loop so how can you get all Contacts Vcard.??


           /* FileInputStream fis = fd.createInputStream();
            byte[] buf = new byte[(int) fd.getDeclaredLength()];
            fis.read(buf);
            String VCard = new String(buf);
            String path = Environment.getExternalStorageDirectory().toString() + File.separator + vfile;
            FileOutputStream out = new FileOutputStream(path);
            out.write(VCard.toString().getBytes());
            Log.d("Vcard",  VCard);*/

            FileInputStream fis = fd.createInputStream();
            byte[] buf = new byte[(int) fd.getDeclaredLength()];
            fis.read(buf);
            String vcardstring= new String(buf);
            vCard.add(vcardstring);

            String storage_path = Environment.getExternalStorageDirectory().toString() + File.separator + vfile;

            Log.d("TAG", "STORAGE" + storage_path);
            FileOutputStream mFileOutputStream = new FileOutputStream(storage_path, false);
            mFileOutputStream.write(vcardstring.toString().getBytes());

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
