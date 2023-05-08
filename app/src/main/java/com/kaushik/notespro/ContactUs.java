package com.kaushik.notespro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ContactUs extends AppCompatActivity {

    EditText edt_content, edt_title, edt_from;
    Button sendEmail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        edt_content = findViewById(R.id.edt_content_email);
        edt_title = findViewById(R.id.edt_title_email);
        edt_from = findViewById(R.id.edt_from_email);
        sendEmail = findViewById(R.id.send_email_btn);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSendEmail();
            }
        });
    }
    void buttonSendEmail(){
        String from = edt_from.getText().toString().trim();
        String title = edt_title.getText().toString();
        String content = edt_content.getText().toString();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Map<String, Object> messages = new HashMap<>();
        messages.put("sender", from);
        messages.put("title", title);
        messages.put("content", content);

        firestore.collection("messages").add(messages).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ContactUs.this, "Success!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ContactUs.this, "Failed to send message!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}