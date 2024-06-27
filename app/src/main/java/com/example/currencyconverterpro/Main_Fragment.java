package com.example.currencyconverterpro;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class Main_Fragment extends Fragment {

    MyAdapter adapter;
    public List<MyModel> data;
    ListView listView;
    public Main_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_, container, false);

        listView = view.findViewById(R.id.listView);
        data = new ArrayList<>();
        displayItems(adapter);
        return view;

    }

    public void filteredList(String s) {
        List<MyModel> filter = new ArrayList<>();
        for(MyModel item: data){
            if(item.getC_name().toLowerCase().contains(s.toLowerCase())){
                filter.add(item);
            }
        }
        adapter.filteredList(filter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    public void displayItems(MyAdapter adapter) {
        data.add(new MyModel("PKR", R.drawable.pakistan));
        data.add(new MyModel("AFG", R.drawable.afghanistan));
        data.add(new MyModel("USD", R.drawable.us));
        data.add(new MyModel("EUR", R.drawable.eruope));
        data.add(new MyModel("INR", R.drawable.india));
        data.add(new MyModel("IRR", R.drawable.iran));
        data.add(new MyModel("JPY", R.drawable.japan));
        data.add(new MyModel("CNY", R.drawable.china));
        adapter = new MyAdapter(data, getContext());
        listView.setAdapter(adapter);

    }



}