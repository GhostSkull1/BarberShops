package com.jonnycatano.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class CitaActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mfirestore;
    Button btnadd;
    EditText etfecha, ethora, etbarbero;

    private TextView tvhora, tvdate;
    private Button hora, btndate;

    private int dia, mes, ano;

    FirebaseFirestore fStore;

    String userID;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);

        mfirestore = FirebaseFirestore.getInstance();

        etfecha = findViewById(R.id.Etfecha);
        ethora = findViewById(R.id.Ethora);
        etbarbero = findViewById(R.id.Etnombarbero);
        btnadd = findViewById(R.id.btnregistrarcita);
        tvhora = findViewById(R.id.Ethora);
        hora = findViewById(R.id.btnhora);
        tvdate = findViewById(R.id.Etfecha);
        btndate = findViewById(R.id.btnfecha);
        btndate.setOnClickListener(this);


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechat = etfecha.getText().toString().trim();
                String horat = ethora.getText().toString().trim();
                String barberot = etbarbero.getText().toString().trim();

                if (fechat.isEmpty() && horat.isEmpty() && barberot.isEmpty()){
                    Toast.makeText(getApplicationContext(), "ingrese la información", Toast.LENGTH_SHORT).show();
                }else {
                    postDate(fechat, horat, barberot);
                    finish();
                }

            }
        });


        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CitaActivity.this, androidx.appcompat.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat formato = new SimpleDateFormat("k:mm a");
                        String time = formato.format(c.getTime());
                        tvhora.setText(time);
                    }
                },hours, mins, false);
                timePickerDialog.show();
            }
        });
    }

    private void postDate(String fechat, String horat, String barberot) {

        Map<String, Object> map = new HashMap<>();
        map.put("Fecha", fechat);
        map.put("Hora", horat);
        map.put("Barbero", barberot);

        mfirestore.collection("users").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "creado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btndate){
            final  Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tvdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            },dia, mes, ano);
            datePickerDialog.show();
        }
    }
}