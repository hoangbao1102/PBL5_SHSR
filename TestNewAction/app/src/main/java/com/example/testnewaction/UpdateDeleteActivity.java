package com.example.testnewaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.testnewaction.dal.databaseHelper;
import com.example.testnewaction.model.ItemAction;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName;
    private SwitchCompat switch1, switch2, switch3, switch4;
    private ImageView img1, img2, img3, img4;
    private Button btnupdate, btncancel, btndelete;
    private databaseHelper dbhelp = new databaseHelper();
    private ItemAction itemAction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btncancel.setOnClickListener(this);
        btnupdate.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        switch1.setOnClickListener(this);
        switch2.setOnClickListener(this);
        switch3.setOnClickListener(this);
        switch4.setOnClickListener(this);

        Intent intent = getIntent();
        itemAction = (ItemAction) intent.getSerializableExtra("itemaction");
        edtName.setText(itemAction.getActionname());
        if (itemAction.getStatus1() == 1) {
            onoff_device(1);
        }
        else {
            onoff_device(2);
        }
        if (itemAction.getStatus2() == 1) {
            onoff_device(3);
        }
        else {
            onoff_device(4);
        }
        if (itemAction.getStatus3() == 1) {
            onoff_device(5);
        }
        else {
            onoff_device(6);
        }
        if (itemAction.getStatus4() == 1) {
            onoff_device(7);
        }
        else {
            onoff_device(8);
        }
    }

    private void initView() {
        edtName = findViewById(R.id.edtName);
        switch1 = findViewById(R.id.switch_button1);
        switch2 = findViewById(R.id.switch_button2);
        switch3 = findViewById(R.id.switch_button3);
        switch4 = findViewById(R.id.switch_button4);

        img1 = findViewById(R.id.device_icon1);
        img2 = findViewById(R.id.device_icon2);
        img3 = findViewById(R.id.device_icon3);
        img4 = findViewById(R.id.device_icon4);

        btnupdate = findViewById(R.id.btnUpdate);
        btncancel = findViewById(R.id.btnCancle);
        btndelete = findViewById(R.id.btnDelete);
    }

    @Override
    public void onClick(View v) {
        if(v == btncancel) {
            finish();
        }
        if(v == btnupdate) {
            if (itemAction.getActionname() != null) {
                dbhelp.updateActionStatus(itemAction);
                finish();
            }
            else
                Toast.makeText(this, "Insert action name", Toast.LENGTH_SHORT).show();
        }
        if (v == btndelete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thong bao xoa");
            builder.setMessage("Ban co chac muon xoa '" + itemAction.getActionname() +"' khong?");
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbhelp.removeAction(itemAction.getActionid());
                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        if(v == switch1) {
            if (switch1.isChecked() == true) {
                onoff_device(1);
                itemAction.setStatus1(1);
            }
            else {
                onoff_device(2);
                itemAction.setStatus1(0);
            }
        }
        if(v == switch2) {
            if (switch2.isChecked() == true) {
                onoff_device(3);
                itemAction.setStatus2(1);
            }
            else {
                onoff_device(4);
                itemAction.setStatus2(0);
            }
        }
        if(v == switch3) {
            if (switch3.isChecked() == true) {
                onoff_device(5);
                itemAction.setStatus3(1);
            }
            else {
                onoff_device(6);
                itemAction.setStatus3(0);

            }
        }
        if(v == switch4) {
            if (switch4.isChecked() == true) {
                onoff_device(7);
                itemAction.setStatus4(1);
            }
            else {
                onoff_device(8);
                itemAction.setStatus4(0);

            }
        }
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
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
