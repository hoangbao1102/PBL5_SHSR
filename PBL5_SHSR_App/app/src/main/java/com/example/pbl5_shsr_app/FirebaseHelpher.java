package com.example.pbl5_shsr_app;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pbl5_shsr_app.RecyclerView.DeviceAdapter;
import com.example.pbl5_shsr_app.model.Device;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelpher extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private DeviceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("devices");

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),mRecyclerView.VERTICAL,false ));
        mAdapter = new DeviceAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Device> devices = new ArrayList<>();
                for (DataSnapshot deviceSnapshot : snapshot.getChildren()) {
                    Device device = deviceSnapshot.getValue(Device.class);
                    devices.add(device);
                }
                mAdapter.setDevices(devices);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DeviceListActivity", "Database error: " + error.getMessage());
            }
        });
    }


}
