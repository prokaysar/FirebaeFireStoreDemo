package com.prokaysar.firebaefirestoredemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    private RecyclerView mlist;
    private FirebaseFirestore db;
    private PostListAdapter postListAdapter;
    private List<DataModel> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        db = FirebaseFirestore.getInstance();

        postList = new ArrayList<>();
        postListAdapter = new PostListAdapter(getApplicationContext(),postList);

        mlist = findViewById(R.id.main_list);
        mlist.setHasFixedSize(true);
        mlist.setLayoutManager(new LinearLayoutManager(this));
        mlist.setAdapter(postListAdapter);


        db.collection("Health_Book").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                   if (doc.getType() == DocumentChange.Type.ADDED){
                     /*  String header = doc.getDocument().getString("header");
                       String desc  = doc.getDocument().getString("desc");*/
                     // user for list
                       String post_id = doc.getDocument().getId();
                       DataModel dataModel = doc.getDocument().toObject(DataModel.class).withId(post_id);
                       postList.add(dataModel);
                       postListAdapter.notifyDataSetChanged();
                   }

                }
            }
        });
    }

}
