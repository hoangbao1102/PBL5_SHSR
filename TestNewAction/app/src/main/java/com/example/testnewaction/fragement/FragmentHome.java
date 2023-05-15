package com.example.testnewaction.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioRecord;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.testnewaction.dal.databaseHelper;
import com.example.testnewaction.R;

public class FragmentHome extends Fragment {

    private TextView txtName1, txtName2, txtName3, txtName4;
    private SwitchCompat switch1, switch2, switch3, switch4;
    private ImageView img1, img2, img3, img4;
    private Button btnConnect, btnRecord, btnModel;
    private MediaRecorder mediaRecorder;
    private AudioRecord audioRecord;
    private MediaPlayer mediaPlayer;
    private String audioSavePath = null;
    private boolean isRecording = false;
    private DatabaseReference databaseReference;
    private Thread recordingThread = null;


    private static final int bufferElements2Rec = 16;
    public static final int RECORDER_SAMPLE_RATE = 16000;
    public static final int RECORDER_CHANNELS = android.media.AudioFormat.CHANNEL_IN_MONO;
    public static final int RECORDER_AUDIO_ENCODING = android.media.AudioFormat.ENCODING_PCM_16BIT;
    public static final short BITS_PER_SAMPLE = 16;
    public static final short NUMBER_CHANNELS = 1;
    public static final int BYTE_RATE = RECORDER_SAMPLE_RATE * NUMBER_CHANNELS * 16 / 8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home,container, false);

        initView(view);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("devices");


        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    // Start recording
                    audioSavePath = getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath()
                            + "/recordingAudio.wav";

                    int bufferElements2Rec = AudioRecord.getMinBufferSize(RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING) / 2;
                    startRecording(bufferElements2Rec);
                    Toast.makeText(getContext(), "Recording Start", Toast.LENGTH_SHORT).show();
                    btnRecord.setText("STOP RECORDING");
                } else {
                    // Stop recording
                    stopRecording();
                    isRecording = false;
                    btnRecord.setText("Start Recording");
                    Toast.makeText(getContext(), "Recording Stop", Toast.LENGTH_SHORT).show();
                    playRecording(audioSavePath);
                }
            }
        });

        btnModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("model");

                // Kiểm tra giá trị hiện tại của child "model"
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Lấy giá trị của child "model"
                        int modelValue = dataSnapshot.getValue(Integer.class);

                        // Đổi giá trị của child "model"
                        int newModelValue = (modelValue == 0) ? 1 : 0;
                        databaseReference1.setValue(newModelValue);

                        if (newModelValue == 0) {
                            btnModel.setText("HMM");
                        } else {
                            btnModel.setText("CNN");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }
        });
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String deviceId = "";
                        String deviceName = "";
                        Long deviceStatus = Long.valueOf(0);
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
                        } else {
                            onoff_device(2);
                        }

                        // Set up device 2 : door
                        String deviceName2 = (String) deviceInfoMap.get("device2").get("name");
                        Long deviceStatus2 = (Long) deviceInfoMap.get("device2").get("status");
                        txtName2.setText(deviceName2);
                        if (deviceStatus2.intValue() == 1) {
                            onoff_device(3);
                        } else {
                            onoff_device(4);
                        }

                        // Set up device 3 : air conditioner
                        String deviceName3 = (String) deviceInfoMap.get("device3").get("name");
                        Long deviceStatus3 = (Long) deviceInfoMap.get("device3").get("status");
                        txtName3.setText(deviceName3);
                        if (deviceStatus3.intValue() == 1) {
                            onoff_device(5);
                        } else {
                            onoff_device(6);
                        }
                        // Set up device 4 : fan
                        String deviceName4 = (String) deviceInfoMap.get("device4").get("name");
                        Long deviceStatus4 = (Long) deviceInfoMap.get("device4").get("status");
                        Long deviceSpeed4 = (Long) deviceInfoMap.get("device4").get("speed");
                        txtName4.setText(deviceName4);
                        if (deviceStatus4.intValue() == 1) {
                            if (deviceSpeed4.intValue() == 1) {
                                onoff_device(9);
                            }
                            else {
                                onoff_device(7);
                            }
                        } else {
                            onoff_device(8);
                        }


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("model");

                // Kiểm tra giá trị hiện tại của child "model"
                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Lấy giá trị của child "model"
                        int modelValue = dataSnapshot.getValue(Integer.class);

                        // Đổi giá trị của child "model"
                        int newModelValue = (modelValue == 0) ? 1 : 0;
                        databaseReference1.setValue(newModelValue);

                        if (newModelValue == 0) {
                            btnModel.setText("HMM");
                        } else {
                            btnModel.setText("CNN");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1.isChecked()) {
                    databaseReference.child("device1").child("status").setValue(1);
                } else {
                    databaseReference.child("device1").child("status").setValue(0);
                }
            }
        });
        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch2.isChecked()) {
                    databaseReference.child("device2").child("status").setValue(1);
                } else {
                    databaseReference.child("device2").child("status").setValue(0);
                }
            }
        });
        switch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch3.isChecked()) {
                    databaseReference.child("device3").child("status").setValue(1);
                } else {
                    databaseReference.child("device3").child("status").setValue(0);
                }
            }
        });
        switch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch4.isChecked()) {
                    databaseReference.child("device4").child("status").setValue(1);
                } else {
                    databaseReference.child("device4").child("status").setValue(0);
                }
            }
        });
        return view;

    }

    private void initView(View view) {
        txtName1 = view.findViewById(R.id.name_device1);
        txtName2 = view.findViewById(R.id.name_device2);
        txtName3 = view.findViewById(R.id.name_device3);
        txtName4 = view.findViewById(R.id.name_device4);

        switch1 = view.findViewById(R.id.switch_button1);
        switch2 = view.findViewById(R.id.switch_button2);
        switch3 = view.findViewById(R.id.switch_button3);
        switch4 = view.findViewById(R.id.switch_button4);

        img1 = view.findViewById(R.id.device_icon1);
        img2 = view.findViewById(R.id.device_icon2);
        img3 = view.findViewById(R.id.device_icon3);
        img4 = view.findViewById(R.id.device_icon4);

        btnConnect = view.findViewById(R.id.btnConnect);
        btnRecord = view.findViewById(R.id.btnRecord);
        btnModel = view.findViewById(R.id.btnModel);
    }

    public void startRecording(int bufferElements2Rec) {

        if (checkPermission()) {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    RECORDER_SAMPLE_RATE, RECORDER_CHANNELS,
                    RECORDER_AUDIO_ENCODING, bufferElements2Rec);

            audioRecord.startRecording();
            isRecording = true;

            recordingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    writeAudioDataToFile(audioSavePath, bufferElements2Rec);
                }
            }, "AudioRecorder Thread");
            recordingThread.start();
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }
    }
    private void stopRecording() {
        if (audioRecord != null) {
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            recordingThread = null;
            audioRecord = null;
        }

    }
    private void playRecording(String filePath) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists() && file.canRead()) {
                Uri uri = Uri.parse("file://" + filePath);
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(getContext(), uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getContext(), "Start Playing", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Upload audio file to Firebase Storage
                Uri upfile = Uri.fromFile(new File(filePath));
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference audioRef = storageRef.child("audio/" + upfile.getLastPathSegment());
                UploadTask uploadTask = audioRef.putFile(upfile);
                Toast.makeText(getContext(), "Uploaded to Storage", Toast.LENGTH_SHORT).show();
                // Listen for upload success/failure
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded file
                        audioRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Save audio file information to Realtime Database
                                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                                HashMap<String, Object> audioInfo = new HashMap<>();
                                audioInfo.put("file_name", upfile.getLastPathSegment());
                                audioInfo.put("file_url", uri.toString());
                                databaseRef.child("audio").setValue(audioInfo);
                            }

                        });
                        Toast.makeText(getContext(), "Uploaded to Dataset", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle failure
                    }
                });
            } else {
                Toast.makeText(getContext(), "Recording file does not exist or cannot be read", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No recording file found", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeAudioDataToFile(String path, int BufferElements2Rec) {
        short[] sData = new short[BufferElements2Rec];
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Byte> data = new ArrayList<Byte>();

        for (byte b : wavFileHeader()) {
            data.add(b);
        }

        while (isRecording) {
            // gets the voice output from microphone to byte format
            audioRecord.read(sData, 0, BufferElements2Rec);
            try {
                byte[] bData = short2byte(sData);
                for (byte b : bData)
                    data.add(b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        updateHeaderInformation(data);

        try {
            os.write(toByteArray(data));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private byte[] toByteArray(ArrayList<Byte> data) {
        byte[] bytes = new byte[data.size()];
        for (int i = 0; i < data.size(); i++) {
            bytes[i] = data.get(i);
        }
        return bytes;
    }
    private byte[] short2byte(short[] sData) {
        int arrSize = sData.length;
        byte[] bytes = new byte[arrSize * 2];
        for (int i = 0; i < arrSize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[i * 2 + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;
    }
    private void updateHeaderInformation(ArrayList<Byte> data) {
        int fileSize = data.size();
        int contentSize = fileSize - 44;

        data.set(4, (byte)(fileSize & 0xff)); // Size of the overall file
        data.set(5, (byte)((fileSize >> 8) & 0xff));
        data.set(6, (byte)((fileSize >> 16) & 0xff));
        data.set(7, (byte)((fileSize >> 24) & 0xff));

        data.set(40, (byte)(contentSize & 0xff)); // Size of the data section.
        data.set(41, (byte)((contentSize >> 8) & 0xff));
        data.set(42, (byte)((contentSize >> 16) & 0xff));
        data.set(43, (byte)((contentSize >> 24) & 0xff));
    }
    /**
     * Constructs header for wav file format
     */
    private byte[] wavFileHeader() {
        int headerSize = 44;
        byte[] header = new byte[headerSize];

        header[0] = 'R'; // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';

        header[4] = (byte) (0); // Size of the overall file, 0 because unknown
        header[5] = (byte) (0);
        header[6] = (byte) (0);
        header[7] = (byte) (0);

        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';

        header[12] = 'f'; // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';

        header[16] = 16; // Length of format data
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;

        header[20] = 1; // Type of format (1 is PCM)
        header[21] = 0;

        header[22] = NUMBER_CHANNELS;
        header[23] = 0;

        header[24] = (byte) (RECORDER_SAMPLE_RATE & 0xff); // Sampling rate
        header[25] = (byte) ((RECORDER_SAMPLE_RATE >> 8) & 0xff);
        header[26] = (byte) ((RECORDER_SAMPLE_RATE >> 16) & 0xff);
        header[27] = (byte) ((RECORDER_SAMPLE_RATE >> 24) & 0xff);

        header[28] = (byte) (BYTE_RATE & 0xff); // Byte rate = (Sample Rate * BitsPerSample * Channels) / 8
        header[29] = (byte) ((BYTE_RATE >> 8) & 0xff);
        header[30] = (byte) ((BYTE_RATE >> 16) & 0xff);
        header[31] = (byte) ((BYTE_RATE >> 24) & 0xff);

        header[32] = (byte) ((NUMBER_CHANNELS * BITS_PER_SAMPLE) / 8); //  16 Bits stereo
        header[33] = 0;

        header[34] = BITS_PER_SAMPLE; // Bits per sample
        header[35] = 0;

        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';

        header[40] = (byte) (0); // Size of the data section.
        header[41] = (byte) (0);
        header[42] = (byte) (0);
        header[43] = (byte) (0);

        return header;
    }
    private void onoff_device(int i) {
        switch (i) {
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
                img4.setImageResource(R.drawable.fan_on_low);
                break;
            case 8:
                switch4.setChecked(false);
                img4.setImageResource(R.drawable.fan_off);
                break;
            case 9:
                switch4.setChecked(true);
                img4.setImageResource(R.drawable.fan_on_high);
                break;

        }
    }

    private boolean checkPermission() {
        int first = ActivityCompat.checkSelfPermission(requireContext().getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(requireContext().getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED;
    }
    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(requireContext().getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int second = ActivityCompat.checkSelfPermission(requireContext().getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        return first == PackageManager.PERMISSION_GRANTED &&
                second == PackageManager.PERMISSION_GRANTED;
    }
}
