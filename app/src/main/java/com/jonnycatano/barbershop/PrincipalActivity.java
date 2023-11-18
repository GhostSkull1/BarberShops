package com.jonnycatano.barbershop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PrincipalActivity extends AppCompatActivity {

    TextView etnombre, etcelular;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        etnombre = findViewById(R.id.perfilnombre);
        etcelular = findViewById(R.id.perfilcelular);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                etnombre.setText(documentSnapshot.getString("fName"));
                etcelular.setText(documentSnapshot.getString("Phone"));

            }
        });



    }

    public void cerrar(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),IniciarActivity.class));
        finish();
    }

    public void agendar(View view){
        Intent agendar = new Intent(this,CitaActivity.class);
        startActivity(agendar);
    }

    public void consultar(View view){
        Intent consultar = new Intent(this,MostrarCitaActivity.class);
        startActivity(consultar);
    }

}