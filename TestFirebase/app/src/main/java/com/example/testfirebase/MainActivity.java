package com.example.testfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView txtName1, txtName2, txtName3, txtName4;
    private SwitchCompat switch1, switch2, switch3, switch4;
    private ImageView img1, img2, img3, img4;

    private Button btnConnect;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        initView();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("devices");

//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String data = editText.getText().toString();
//                databaseReference.setValue(data);
//            }
//        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String deviceId ="";
                        String deviceName = "";
                        Long deviceStatus =  Long.valueOf(0);
                        Long deviceSpeed = Long.valueOf(0);
                        Map<String, HashMap<String, Object>> deviceInfoMap = new HashMap<>();
                        for (DataSnapshot deviceSnapshot : snapshot.getChildren()) {
                            deviceId = deviceSnapshot.getKey();
                            deviceName = deviceSnapshot.child("name").getValue(String.class);
                            deviceStatus = deviceSnapshot.child("status").getValue(Long.class);
                            deviceSpeed = deviceSnapshot.child("speed").getValue(Long.class);
                            // Do something with the device data
                            Log.d("Device data", "Device ID: " + deviceId + ", Name: " + deviceName + ", Status: " + deviceStatus + ", Speed: " + deviceSpeed);
                            // Create a new HashMap to store the device info
                            HashMap<String, Object> deviceInfo = new HashMap<>();
                            deviceInfo.put("name", deviceName);
                            deviceInfo.put("status", deviceStatus);
                            deviceInfo.put("speed", deviceSpeed);

                            // Add the device info to the map
                            deviceInfoMap.put(deviceId, deviceInfo);
                        }
                        // Set up device 1 : light
                        String deviceName1 = (String) deviceInfoMap.get("device1").get("name");
                        Long deviceStatus1 = (Long) deviceInfoMap.get("device1").get("status");
                        txtName1.setText(deviceName1);
                        if (deviceStatus1.intValue() == 1) {
                            switch1.setChecked(true);
                            img1.setImageResource(R.drawable.light_on);
                        }
                        else {
                            switch1.setChecked(false);
                            img1.setImageResource(R.drawable.light_off);
                        }

                        // Set up device 2 : door
                        String deviceName2 = (String) deviceInfoMap.get("device2").get("name");
                        Long deviceStatus2 = (Long) deviceInfoMap.get("device2").get("status");
                        txtName2.setText(deviceName2);
                        if (deviceStatus2.intValue() == 1) {
                            switch2.setChecked(true);
                            img2.setImageResource(R.drawable.door_open);
                        }
                        else {
                            switch2.setChecked(false);
                            img2.setImageResource(R.drawable.door_close);
                        }

                        // Set up device 3 : air conditioner
                        String deviceName3 = (String) deviceInfoMap.get("device3").get("name");
                        Long deviceStatus3 = (Long) deviceInfoMap.get("device3").get("status");
                        txtName3.setText(deviceName3);
                        if (deviceStatus3.intValue() == 1) {
                            switch3.setChecked(true);
                            img3.setImageResource(R.drawable.air_conditioner_on);
                        }
                        else {
                            switch3.setChecked(false);
                            img3.setImageResource(R.drawable.air_conditioner_off);
                        }
                        // Set up device 4 : fan
                        String deviceName4 = (String) deviceInfoMap.get("device4").get("name");
                        Long deviceStatus4 = (Long) deviceInfoMap.get("device4").get("status");
                        txtName4.setText(deviceName4);
                        if (deviceStatus4.intValue() == 1) {
                            switch4.setChecked(true);
                            img4.setImageResource(R.drawable.fan_on);
                        }
                        else {
                            switch4.setChecked(false);
                            img4.setImageResource(R.drawable.fan_off);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void initView() {
        txtName1 = findViewById(R.id.name_device1);
        txtName2 = findViewById(R.id.name_device2);
        txtName3 = findViewById(R.id.name_device3);
        txtName4 = findViewById(R.id.name_device4);

        switch1 = findViewById(R.id.switch_button1);
        switch2 = findViewById(R.id.switch_button2);
        switch3 = findViewById(R.id.switch_button3);
        switch4 = findViewById(R.id.switch_button4);

        img1 = findViewById(R.id.device_icon1);
        img2 = findViewById(R.id.device_icon2);
        img3 = findViewById(R.id.device_icon3);
        img4 = findViewById(R.id.device_icon4);

        btnConnect = findViewById(R.id.btnConnect);
    }
}