package com.jonnycatano.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PantallaInicial extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);



    }

    public void registro(View view){
        Intent registro = new Intent(this,RegistroActivity.class);
        startActivity(registro);
    }

    public void iniciar(View view){
        Intent iniciar = new Intent(this,IniciarActivity.class);
        startActivity(iniciar);
    }
}