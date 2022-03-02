package com.example.notesj.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notesj.R;
import com.example.notesj.adapter.NotesAdapter;
import com.example.notesj.helper.RecyclerItemTouchHelper;
import com.example.notesj.manager.RealmManager;
import com.example.notesj.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText et_notes;
    private FloatingActionButton bt_add;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private ArrayList<Notes> courseModalArrayList = new ArrayList<Notes>();
    RealmManager realmManager = RealmManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        bt_add = findViewById(R.id.bt_add);
        recyclerView = findViewById(R.id.recyclerView);
        buildRecyclerView();
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked();
            }
        });

    }

    public void showAlertDialogButtonClicked() {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);builder.setTitle("New Note");
        final View customLayout = getLayoutInflater().inflate(R.layout.item_dialog, null);
        builder.setView(customLayout);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et_notes = customLayout.findViewById(R.id.et_notes);
                courseModalArrayList.add(adapter.addNote(getNote(et_notes.getText().toString())));
            }});
         builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }});
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @SuppressLint("SimpleDateFormat")
    public Notes getNote(String text) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = dateFormat.format(date);
        return new Notes(date.toString(),text,dateText);
    }
    private ArrayList<Notes> getNotes() {
        return realmManager.loadNotes();
    }

    private void buildRecyclerView(){
        adapter = new NotesAdapter(getNotes(), this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int adapterPosition) {

            }
        });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

}
