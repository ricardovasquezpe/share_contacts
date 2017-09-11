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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.rikardo308.sharecontacts.Model.EmployeeInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 0;

    private List<EmployeeInfo> employeeInfoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EmployeeInfoAdapter employeeInfoAdapter;

    Cursor cursor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.employee_recycler_view);
        employeeInfoAdapter = new EmployeeInfoAdapter(employeeInfoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(employeeInfoAdapter);

        getRequestPermission();
    }

    private void getRequestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }else{
            GetContactsIntoArrayList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GetContactsIntoArrayList();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void GetContactsIntoArrayList(){
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
        while (cursor.moveToNext()) {
            String name        = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            EmployeeInfo employeeInfo = new EmployeeInfo(name, phonenumber);
            employeeInfoList.add(employeeInfo);
        }
        cursor.close();
        employeeInfoAdapter.notifyDataSetChanged();
    }
}
