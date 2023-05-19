package com.example.testnewaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.testnewaction.dal.databaseHelper;
import com.example.testnewaction.model.ItemAction;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtName;
    private SwitchCompat switch1, switch2, switch3, switch4;
    private ImageView img1, img2, img3, img4;
    private Button btnadd, btncancel;
    private databaseHelper dbhelp = new databaseHelper();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btncancel.setOnClickListener(this);
        btnadd.setOnClickListener(this);
        switch1.setOnClickListener(this);
        switch2.setOnClickListener(this);
        switch3.setOnClickListener(this);
        switch4.setOnClickListener(this);

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

        btnadd = findViewById(R.id.btnAdd);
        btncancel = findViewById(R.id.btnCancle);
    }

    @Override
    public void onClick(View v) {
        if(v == btncancel) {
            finish();
        }
        if(v == btnadd) {
            ItemAction itemAction = new ItemAction();
            itemAction.setActionname(edtName.getText().toString());
            itemAction.setActionid(edtName.getText().toString());
            itemAction.setStatus(0);
            if (switch1.isChecked() == true) {
                itemAction.setStatus1(1);
            }
            else {
                itemAction.setStatus1(0);
            }
            if (switch2.isChecked() == true) {
                itemAction.setStatus2(1);
            }
            else {
                itemAction.setStatus2(0);
            }
            if (switch3.isChecked() == true) {
                itemAction.setStatus3(1);
            }
            else {
                itemAction.setStatus3(0);
            }
            if (switch4.isChecked() == true) {
                itemAction.setStatus4(1);
            }
            else {
                itemAction.setStatus4(0);
            }
            if (itemAction.getActionid() != null) {
                dbhelp.insertAction(itemAction);
                finish();
            }
            else
                Toast.makeText(this, "Insert action name", Toast.LENGTH_SHORT).show();
        }
        if(v == switch1) {
            if (switch1.isChecked() == true) {
                onoff_device(1);
            }
            else {
                onoff_device(2);
            }
        }
        if(v == switch2) {
            if (switch2.isChecked() == true) {
                onoff_device(3);
            }
            else {
                onoff_device(4);
            }
        }
        if(v == switch3) {
            if (switch3.isChecked() == true) {
                onoff_device(5);
            }
            else {
                onoff_device(6);
            }
        }
        if(v == switch4) {
            if (switch4.isChecked() == true) {
                onoff_device(7);
            }
            else {
                onoff_device(8);
            }
        }
    }
    private void onoff_device(int i) {
        switch (i) {
            case 1:
                img1.setImageResource(R.drawable.light_on);
                break;
            case 2:
                img1.setImageResource(R.drawable.light_off);
                break;
            case 3:
                img2.setImageResource(R.drawable.door_open);
                break;
            case 4:
                img2.setImageResource(R.drawable.door_close);
                break;
            case 5:
                img3.setImageResource(R.drawable.air_conditioner_on);
                break;
            case 6:
                img3.setImageResource(R.drawable.air_conditioner_off);
                break;
            case 7:
                img4.setImageResource(R.drawable.fan_on_low);
                break;
            case 8:
                img4.setImageResource(R.drawable.fan_off);
                break;
            case 9:
                img4.setImageResource(R.drawable.fan_on_high);
                break;

        }
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
