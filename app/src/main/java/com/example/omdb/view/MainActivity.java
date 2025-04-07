package com.example.omdb.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omdb.R;
import com.example.omdb.model.OMDbModel;
import com.example.omdb.viewmodel.OMDbViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    OMDbViewModel viewModel;
    MyAdapter myAdapter;
    List<OMDbModel> itemList;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Firebase Connections
        FirebaseApp.initializeApp(this);
        //FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText textMovie = findViewById(R.id.appSearch);
        Button myButton = findViewById(R.id.appSearchBtn);
        RecyclerView recyclerView = findViewById(R.id.rvMovies);

        viewModel = new ViewModelProvider(this).get(OMDbViewModel.class);
        itemList = new ArrayList<>();

        myAdapter = new MyAdapter(itemList, this);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(myAdapter);

        viewModel.getOMDbData().observe(this, movieList-> {
            itemList.clear();
            itemList.addAll(movieList);
            myAdapter.notifyDataSetChanged();
        });

        myButton.setOnClickListener(view -> {
            String movie = textMovie.getText().toString();
            viewModel.Refresh(movie);
        });
    }
}