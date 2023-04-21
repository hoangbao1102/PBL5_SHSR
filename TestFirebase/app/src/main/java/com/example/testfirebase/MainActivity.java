package com.example.testfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView txtName1, txtName2, txtName3, txtName4;
    private SwitchCompat switch1, switch2, switch3, switch4;
    private ImageView img1, img2, img3, img4;
    private Button btnConnect, btnRecord;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String audioSavePath = null;
    private boolean isRecording = false;
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
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions() == true) {
                    if (!isRecording) {
                        isRecording = true;
                        audioSavePath = getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()
                                + "/" + "recordingAudio.wav";

                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        mediaRecorder.setOutputFile(audioSavePath);

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                            Toast.makeText(MainActivity.this, "Recording started", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        isRecording = false;
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        Toast.makeText(MainActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();

                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(audioSavePath);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            Toast.makeText(MainActivity.this, "Start playing", Toast.LENGTH_SHORT).show();

                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    isRecording = false;
                                    Toast.makeText(MainActivity.this, "Stopped playing", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },1);
                }
            }
        });

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
                            onoff_device(1);
                        }
                        else {
                            onoff_device(2);
                        }

                        // Set up device 2 : door
                        String deviceName2 = (String) deviceInfoMap.get("device2").get("name");
                        Long deviceStatus2 = (Long) deviceInfoMap.get("device2").get("status");
                        txtName2.setText(deviceName2);
                        if (deviceStatus2.intValue() == 1) {
                            onoff_device(3);
                        }
                        else {
                            onoff_device(4);
                        }

                        // Set up device 3 : air conditioner
                        String deviceName3 = (String) deviceInfoMap.get("device3").get("name");
                        Long deviceStatus3 = (Long) deviceInfoMap.get("device3").get("status");
                        txtName3.setText(deviceName3);
                        if (deviceStatus3.intValue() == 1) {
                            onoff_device(5);                        }
                        else {
                            onoff_device(6);                        }
                        // Set up device 4 : fan
                        String deviceName4 = (String) deviceInfoMap.get("device4").get("name");
                        Long deviceStatus4 = (Long) deviceInfoMap.get("device4").get("status");
                        txtName4.setText(deviceName4);
                        if (deviceStatus4.intValue() == 1) {
                            onoff_device(7);                        }
                        else {
                            onoff_device(8);
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
        btnRecord = findViewById(R.id.btnRecord);
    }

    private void onoff_device(int i) {
        switch(i) {
            case 1:
                switch1.setChecked(true);
                img1.setImageResource(R.drawable.light_on);
                break;
            case 2:
                switch1.setChecked(false);
                img1.setImageResource(R.drawable.light_off);
                break;
            case 3:
                switch2.setChecked(true);
                img2.setImageResource(R.drawable.door_open);
                break;
            case 4:
                switch2.setChecked(false);
                img2.setImageResource(R.drawable.door_close);
                break;
            case 5:
                switch3.setChecked(true);
                img3.setImageResource(R.drawable.air_conditioner_on);
                break;
            case 6:
                switch3.setChecked(false);
                img3.setImageResource(R.drawable.air_conditioner_off);
                break;
            case 7:
                switch4.setChecked(true);
                img4.setImageResource(R.drawable.fan_on);
                break;
            case 8:
                switch4.setChecked(false);
                img4.setImageResource(R.drawable.fan_off);
                break;

        }
    }
    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED;
    }
}