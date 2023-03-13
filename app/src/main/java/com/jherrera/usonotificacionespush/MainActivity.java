package com.jherrera.usonotificacionespush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    //controls
    private TextView textViewToken;
    private Button buttonGetToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInitComponents();
        addEventsButtons();
        registerToFireBaseTopic();
    }

    private void setInitComponents() {
        textViewToken = findViewById(R.id.textViewToken);
        buttonGetToken = findViewById(R.id.buttonGetToken);
    }

    private void addEventsButtons() {
        buttonGetToken.setOnClickListener(view -> {
            getToken();
        });
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            //Successful
            if (!task.isSuccessful()) {
                Log.e("Error Token", "No se pudo completar el token");
            }else {
                textViewToken.setText("[TOKEN GENERADO "+task.getResult()+"]");
                Log.e("Token", task.getResult());
            }
        }).addOnFailureListener(e -> {
            //Exception
            Log.e("Token error", e.getMessage());
        });
    }

    //suscribiendo dispositivo a tema para enviar notificaciones de forma global
    private void registerToFireBaseTopic() {
        Log.d("MainActivity", "Register");
        FirebaseMessaging.getInstance().subscribeToTopic("server-notifications").addOnCompleteListener(
            task -> {
                if (task.isSuccessful()) {
                    Log.d("MainActivity", "Subscribed Topic");
                }else {
                    Log.e("Error Topic","Error Task Topic");
                }
            }
        ).addOnFailureListener(
            e -> {
                Log.e("Topic error", e.getMessage());
            }
        );
    }
}