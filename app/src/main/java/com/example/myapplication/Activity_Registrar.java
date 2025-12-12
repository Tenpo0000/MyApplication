package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Registrar extends AppCompatActivity {

    Button btnEntrar;
    EditText edtConfirmarSenha;
    EditText edtemail;
    EditText edtsenha;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
     btnEntrar = findViewById(R.id.btnEntrar);
     edtemail = findViewById(R.id.edtEmail);edtsenha = findViewById(R.id.edtSenha);
     edtConfirmarSenha = findViewById(R.id.edtConfirmarSenha);

    }
    public void login(View v){
        Intent login = new Intent(this, Activity_Login.class);
        startActivity(login);
    }
    public void registrarUsuario(View v){
        String email = edtemail.getText().toString();
        String ConfirmarSenha = edtConfirmarSenha.getText().toString();
        String senha = edtsenha.getText().toString();

        SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("Confirmar Senha", ConfirmarSenha);
        editor.putString("email", email);
        editor.putString("senha", senha);

        editor.apply();
        Toast.makeText(this, "Usuário registrado!", Toast.LENGTH_SHORT).show();
        finish();
    }
}