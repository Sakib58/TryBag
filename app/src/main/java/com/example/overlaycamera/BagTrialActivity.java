package com.example.overlaycamera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BagTrialActivity extends AppCompatActivity {
    public ViewPagerAdapter viewPagerAdapter;
    public VerticalViewPager viewPager;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    List<BagProperties> bags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag_trial);
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        db.collection("bags")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            bags = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                bags.add(new BagProperties(doc.getData().get("side1").toString(),
                                        doc.getData().get("side2").toString(),
                                        doc.getData().get("side3").toString()
                                ));
                            }
                            if(task.isComplete()){
                                progressDialog.dismiss();
                                viewPager = (VerticalViewPager) findViewById(R.id.viewPager);
                                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),bags);
                                viewPager.setAdapter(viewPagerAdapter);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Helper.log("Failed to retrieve data from firebase because "+e.getMessage());
                progressDialog.dismiss();
            }
        });

    }



}