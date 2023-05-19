package com.example.testnewaction.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testnewaction.R;
import com.example.testnewaction.UpdateDeleteActivity;
import com.example.testnewaction.dal.databaseHelper;
import com.example.testnewaction.model.ItemAction;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {
    private List<ItemAction> list;
    private ItemListener itemListener;

    public RecycleViewAdapter () {
        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<ItemAction> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public ItemAction getItemAction (int position) {
        return list.get(position);
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemaction, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        ItemAction itemAction = list.get(position);
        holder.nameAction.setText(itemAction.getActionname());
        if (itemAction.getStatus() == 1) {
            holder.switchAction.setChecked(true);
        }
        else {
            holder.switchAction.setChecked(false);
        }
        if (itemAction.getStatus1() == 1) {
            holder.img1.setImageResource(R.drawable.light_on);
        }
        else {
            holder.img1.setImageResource(R.drawable.light_off);
        }
        if (itemAction.getStatus2() == 1) {
            holder.img2.setImageResource(R.drawable.door_open);
        }
        else {
            holder.img2.setImageResource(R.drawable.door_close);
        }
        if (itemAction.getStatus3() == 1) {
            holder.img3.setImageResource(R.drawable.air_conditioner_on);
        }
        else {
            holder.img3.setImageResource(R.drawable.air_conditioner_off);
        }
        if (itemAction.getStatus4() == 1) {
            holder.img4.setImageResource(R.drawable.fan_on_low);
        }
        else {
            holder.img4.setImageResource(R.drawable.fan_off);
        }
        holder.switchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper dbhelp = new databaseHelper();
                if (holder.switchAction.isChecked()) {
                    dbhelp.updateActionStatus(itemAction.getActionid(), 1);
                    itemAction.setStatus(1);
                    dbhelp.getAllActions(new databaseHelper.OnActionsLoadedListener() {
                        @Override
                        public void onActionsLoaded(List<ItemAction> list1) {
                            if (list1 != null) {
                                setList(list1);
                            }
                        }
                    });
                    if (itemAction.getStatus1() == 1) {
                        dbhelp.updateDeviceStatus("device1",1);
                    }
                    else {
                        if(checkStatus(1) != 1) {
                            dbhelp.updateDeviceStatus("device1",0);
                        }
                    }
                    if (itemAction.getStatus2() == 1) {
                        dbhelp.updateDeviceStatus("device2",1);
                    }
                    else {
                        if(checkStatus(2) != 1) {
                            dbhelp.updateDeviceStatus("device2",0);
                        }
                    }
                    if (itemAction.getStatus3() == 1) {
                        dbhelp.updateDeviceStatus("device3",1);
                    }
                    else {
                        if(checkStatus(3) != 1) {
                            dbhelp.updateDeviceStatus("device3",0);
                        }
                    }
                    if (itemAction.getStatus4() == 1) {
                        dbhelp.updateDeviceStatus("device4",1);
                    }
                    else {
                        if(checkStatus(4) != 1) {
                            dbhelp.updateDeviceStatus("device4",0);
                        }
                    }
                }
                else {
                    dbhelp.updateActionStatus(itemAction.getActionid(), 0);
                    itemAction.setStatus(0);
                    dbhelp.getAllActions(new databaseHelper.OnActionsLoadedListener() {
                        @Override
                        public void onActionsLoaded(List<ItemAction> list1) {
                            if (list1 != null) {
                                setList(list1);
                            }
                        }
                    });
                    if (itemAction.getStatus1() == 1) {
                        if (checkStatus(1) != 1) {
                            dbhelp.updateDeviceStatus("device1",0);
                        }
                    }
                    if (itemAction.getStatus2() == 1) {
                        if (checkStatus(2) != 1) {
                            dbhelp.updateDeviceStatus("device2",0);
                        }
                    }
                    if (itemAction.getStatus3() == 1) {
                        if (checkStatus(3) != 1) {
                            dbhelp.updateDeviceStatus("device3",0);
                        }
                    }
                    if (itemAction.getStatus4() == 1) {
                        if (checkStatus(4) != 1) {
                            dbhelp.updateDeviceStatus("device4",0);
                        }
                    }
                }
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemAction.getActionname() != null ) {
                    Intent intent = new Intent(view.getContext(), UpdateDeleteActivity.class);
                    intent.putExtra("itemaction", itemAction);
                    view.getContext().startActivity(intent);
                }
                else
                    Toast.makeText(view.getContext(), "Edit not ok", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public long checkStatus(int i) {
        switch (i) {
            case 1:
                for (ItemAction item : list) {
                    if (item.getStatus1() == 1  && item.getStatus() == 1 ) {
                        return 1;
                    }
                }
                break;
            case 2:
                for (ItemAction item : list) {
                    if (item.getStatus2() == 1 && item.getStatus() == 1) {
                        return 1;
                    }
                }
                break;
            case 3:
                for (ItemAction item : list) {
                    if (item.getStatus3() == 1 && item.getStatus() == 1) {
                        return 1;
                    }
                }
                break;
            case 4:
                for (ItemAction item : list) {
                    if (item.getStatus4() == 1 && item.getStatus() == 1 ) {
                        return 1;
                    }
                }
                break;
        }
        return 0;
    }
    public interface ItemListener  {
        void onItemClick(View view, int position);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameAction;
        private SwitchCompat switchAction;
        private ImageView img1, img2, img3, img4;
        private Button btnEdit;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameAction = itemView.findViewById(R.id.nameAction);
            switchAction = itemView.findViewById(R.id.switchAction);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            img1 = itemView.findViewById(R.id.image_view_1);
            img2 = itemView.findViewById(R.id.image_view_2);
            img3 = itemView.findViewById(R.id.image_view_3);
            img4 = itemView.findViewById(R.id.image_view_4);
        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
