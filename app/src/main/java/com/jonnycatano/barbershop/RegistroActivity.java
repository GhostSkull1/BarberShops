package com.jonnycatano.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistroActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText etnombre, etcorreo, etcontrasena, etcelular;
    Button btnregistro;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etnombre = findViewById(R.id.nombre);
        etcelular = findViewById(R.id.celular);
        etcorreo = findViewById(R.id.correo);
        etcontrasena = findViewById(R.id.contrasena);
        btnregistro = findViewById(R.id.btnregistrarse);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
            finish();
        }

        btnregistro.setOnClickListener((v) -> {
            String name = etnombre.getText().toString();
            String phone = etcelular.getText().toString();
            String email = etcorreo.getText().toString().trim();
            String password = etcontrasena.getText().toString().trim();

            if (TextUtils.isEmpty(name)){
                etnombre.setError("se requiere un nombre");
                return;
            }

            if (TextUtils.isEmpty(phone)){
                etcelular.setError("se requiere un numero de celular");
                return;
            }

            if (TextUtils.isEmpty(email)){
                etcorreo.setError("se requiere un correo");
                return;
            }

            if (TextUtils.isEmpty(password)){
                etcontrasena.setError("se requiere una contraseña");
                return;
            }

            if (password.length() < 6){
                etcontrasena.setError("la clave debe de contener mas de 6 digitos");
                return;
            }

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener((task) -> {

                if (task.isSuccessful()){
                    Toast.makeText(RegistroActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String, Object> users = new HashMap<>();
                    users.put("fName",name);
                    users.put("Phone",phone);
                    documentReference.set(users).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                        Log.d(TAG, "onSuccess: perfil de usuario creado por " + userID);
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                }else {
                    Toast.makeText(RegistroActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    //public void principal(View view){
    //    Intent principal = new Intent(this, PrincipalActivity.class);
   //     startActivity(principal);
    //}

}