package com.example.testnewaction.dal;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.testnewaction.model.ItemAction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class databaseHelper {
    private DatabaseReference mDatabase;

    public databaseHelper() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public void updateDeviceStatus(String deviceId, long status) {
        DatabaseReference deviceRef = mDatabase.child("devices").child(deviceId).child("status");
        deviceRef.setValue(status);
    }

    public void updateFanSpeed(String deviceId, int speed) {
        DatabaseReference fanRef = mDatabase.child("devices").child(deviceId).child("speed");
        fanRef.setValue(speed);
    }

    public void updateActionStatus(String actionId, int status) {
        DatabaseReference actionRef = mDatabase.child("actions").child(actionId).child("status");
        actionRef.setValue(status);
    }

    public void updateActionStatus(String actionId, String name, int status1, int status2, int status3, int status4) {
        DatabaseReference actionRef = mDatabase.child("actions").child(actionId);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("name", name);
        childUpdates.put("status1", status1);
        childUpdates.put("status2", status2);
        childUpdates.put("status3", status3);
        childUpdates.put("status4", status4);
        actionRef.updateChildren(childUpdates);
    }
    public void updateActionStatus(ItemAction itemAction) {
        DatabaseReference actionRef = mDatabase.child("actions").child(itemAction.getActionid());
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("name", itemAction.getActionname());
        childUpdates.put("status1", itemAction.getStatus1());
        childUpdates.put("status2", itemAction.getStatus2());
        childUpdates.put("status3", itemAction.getStatus3());
        childUpdates.put("status4", itemAction.getStatus4());
        actionRef.updateChildren(childUpdates);
    }
    // Thêm một action vào database
    public void insertAction(String actionId,String actionName, Long status, Long status1, Long status2, Long status3, Long status4) {
        DatabaseReference actionsRef = mDatabase.child("actions").child(actionId);
        actionsRef.child("name").setValue(actionName);
        actionsRef.child("status").setValue(status);
        actionsRef.child("status1").setValue(status1);
        actionsRef.child("status2").setValue(status2);
        actionsRef.child("status3").setValue(status3);
        actionsRef.child("status4").setValue(status4);
    }
    public void insertAction(ItemAction i) {

        DatabaseReference actionsRef = mDatabase.child("actions").child(i.getActionid());
        actionsRef.child("name").setValue(i.getActionname());
        actionsRef.child("status").setValue(i.getStatus());
        actionsRef.child("status1").setValue(i.getStatus1());
        actionsRef.child("status2").setValue(i.getStatus2());
        actionsRef.child("status3").setValue(i.getStatus3());
        actionsRef.child("status4").setValue(i.getStatus4());
    }

    // Xóa một action từ database
    public void removeAction(String actionId) {
        mDatabase.child("actions").child(actionId).removeValue();
    }

    public interface OnActionsLoadedListener {
        void onActionsLoaded(List<ItemAction> actionsList);
    }

    public void getAllActions(final OnActionsLoadedListener listener) {
        DatabaseReference actionsRef = mDatabase.child("actions");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                actionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<ItemAction> actionsList = new ArrayList<>();
                        for (DataSnapshot actionSnapshot : snapshot.getChildren()) {
                            String id = actionSnapshot.getKey();
                            String name = actionSnapshot.child("name").getValue(String.class);
                            Long status = actionSnapshot.child("status").getValue(Long.class);
                            Long status1 = actionSnapshot.child("status1").getValue(Long.class);
                            Long status2 = actionSnapshot.child("status2").getValue(Long.class);
                            Long status3 = actionSnapshot.child("status3").getValue(Long.class);
                            Long status4 = actionSnapshot.child("status4").getValue(Long.class);
                            actionsList.add(new ItemAction(id, name, status, status1, status2, status3, status4));
                        }
                        listener.onActionsLoaded(actionsList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "getAllActions() cancelled: " + error.getMessage());
                        listener.onActionsLoaded(null);
                    }
                });
            }
        });
    }

    public ItemAction getActionbyActionid(String actionid) {
        DatabaseReference actionRef = mDatabase.child("actions").child(actionid);
        ItemAction itemAction = new ItemAction();
        actionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                Long status = dataSnapshot.child("status").getValue(Long.class);
                Long status1 = dataSnapshot.child("status1").getValue(Long.class);
                Long status2 = dataSnapshot.child("status2").getValue(Long.class);
                Long status3 = dataSnapshot.child("status3").getValue(Long.class);
                Long status4 = dataSnapshot.child("status4").getValue(Long.class);
                itemAction.setActionid(actionid);
                itemAction.setActionname(name);
                itemAction.setStatus(status);
                itemAction.setStatus1(status1);
                itemAction.setStatus2(status2);
                itemAction.setStatus3(status3);
                itemAction.setStatus4(status4);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
        return itemAction;
    }
}
