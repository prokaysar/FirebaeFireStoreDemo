package com.prokaysar.firebaefirestoredemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentListenOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button saveButton,loadData,goTorecycler;
    private EditText textName;
    private TextView textView;




    private FirebaseFirestore db;

    @Override
    protected void onStart() {
        super.onStart();
        DocumentListenOptions documentListenOptions = new DocumentListenOptions();
        documentListenOptions.includeMetadataChanges();

        db.collection("User").document("one").addSnapshotListener(this,documentListenOptions,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                String username = documentSnapshot.getString("name");
                textView.setText("Welcome to "+username);
                if (documentSnapshot.getMetadata().hasPendingWrites()){

                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
                }else {

                    textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize firebase Firestore
        db = FirebaseFirestore.getInstance();

        saveButton  = findViewById(R.id.save_id);
        loadData  = findViewById(R.id.load_data_id);
        textName = findViewById(R.id.name_id);
        textView = findViewById(R.id.textView);
        goTorecycler = findViewById(R.id.go_to_recycler_id);
        goTorecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
            }
        });

        //load data from firestore
       loadData.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              db.collection("User").document("one").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if (task.isSuccessful()){
                          DocumentSnapshot documentSnapshot = task.getResult();
                          if (documentSnapshot.exists() && documentSnapshot !=null){
                              String username  = documentSnapshot.getString("name");
                              textView.setText("Welcome to "+username);
                          }
                      }else {
                          //exception handling
                      }
                  }
              });
           }
       });


        //inserting data on fire store
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textName.getText().toString().trim();
                Map<String,String> userMap = new HashMap<>();

                userMap.put("name",username);
                userMap.put("image","Image_ling");
                db.collection("User").document("one").set(userMap).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String erron = e.getMessage();
                        Toast.makeText(MainActivity.this, "Inser Error!"+erron, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
