package com.jonnycatano.barbershop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MostrarCitaActivity extends AppCompatActivity {

    TextView finalbarbero, finalfecha, finalhora, finalcelular;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    Adapter mAdapter;
    RecyclerView mRecycler;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cita);


        finalbarbero = findViewById(R.id.barberof);
        finalfecha = findViewById(R.id.fechaf);
        finalhora = findViewById(R.id.horaf);
        finalcelular = findViewById(R.id.celularf);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mfirestore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();


        DocumentReference documentReference = mfirestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                finalbarbero.setText(documentSnapshot.getString("Barbero"));
                finalfecha.setText(documentSnapshot.getString("Fecha"));
                finalhora.setText(documentSnapshot.getString("Hora"));
                finalcelular.setText(documentSnapshot.getString("Phone"));

            }
        });
    }

    public void volver(View view){
        Intent volver = new Intent(this,PrincipalActivity.class);
        startActivity(volver);
        finish();
    }
}