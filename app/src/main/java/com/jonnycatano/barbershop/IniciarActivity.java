package com.jonnycatano.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class IniciarActivity extends AppCompatActivity {

    EditText etcorreo,etcontrasena;
    Button btnlogin;
    TextView btncrear, olvicontra;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);

        etcorreo = findViewById(R.id.correo);
        etcontrasena = findViewById(R.id.contrasena);
        fAuth = FirebaseAuth.getInstance();
        btnlogin = findViewById(R.id.btnregistrarse);
        olvicontra = findViewById(R.id.tvolvido);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etcorreo.getText().toString().trim();
                String password = etcorreo.getText().toString().trim();


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

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(IniciarActivity.this, "login exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                        }else {
                            Toast.makeText(IniciarActivity.this, "error ! " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        olvicontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reseteo = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("restablece tu contraseña?");
                passwordResetDialog.setMessage("ingresa tu correo para enviarte un link");
                passwordResetDialog.setView(reseteo);

                passwordResetDialog.setPositiveButton(" si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       String mail = reseteo.getText().toString();
                       fAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               Toast.makeText(IniciarActivity.this, "link de reseteo de contraseña enviado a tu correo", Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(IniciarActivity.this, "error ! link de reseteo no enviado " + e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });
                    }
                });

                passwordResetDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }

    //public void principal(View view){
   //     Intent principal = new Intent(this, PrincipalActivity.class);
    //    startActivity(principal);
    //}
}