package com.example.testnewaction.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testnewaction.R;
import com.example.testnewaction.UpdateDeleteActivity;
import com.example.testnewaction.adapter.RecycleViewAdapter;
import com.example.testnewaction.dal.databaseHelper;
import com.example.testnewaction.model.ItemAction;

import java.util.ArrayList;
import java.util.List;

public class FragmentAction extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private databaseHelper dbhelp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_actions,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        adapter = new RecycleViewAdapter();
        dbhelp = new databaseHelper();
        dbhelp.getAllActions(new databaseHelper.OnActionsLoadedListener() {
            @Override
            public void onActionsLoaded(List<ItemAction> list) {
                if (list != null) {
                    adapter.setList(list);
                }
            }
        });

//        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        int itemCount = adapter.getItemCount();
        if (position >= 0 && position < itemCount) {
            ItemAction item = adapter.getItemAction(position);
            if (item == null) {
                Toast.makeText(getActivity(), "Item is null", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
            intent.putExtra("itemaction", item);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "Invalid position", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dbhelp.getAllActions(new databaseHelper.OnActionsLoadedListener() {
            @Override
            public void onActionsLoaded(List<ItemAction> list) {
                if (list != null) {
                    adapter.setList(list);
                }
            }
        });
    }
}
