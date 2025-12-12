package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
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
        edtemail = findViewById(R.id.edtEmail);
        edtsenha = findViewById(R.id.edtSenha);
        edtConfirmarSenha = findViewById(R.id.edtConfirmarSenha);
    }
    public void login(View v){
        finish();
    }

    public void registrarUsuario(View v){
        String email = edtemail.getText().toString().trim();
        String confirmarSenha = edtConfirmarSenha.getText().toString();
        String senha = edtsenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validarEmail(email)) {
            edtemail.setError("Digite um e-mail válido!");
            edtemail.requestFocus();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            edtConfirmarSenha.setError("As senhas não são iguais!");
            edtConfirmarSenha.requestFocus();
            return;
        }

        String erroSenha = validarSegurancaSenha(senha);
        if (erroSenha != null) {
            edtsenha.setError(erroSenha);
            edtsenha.requestFocus();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("email", email);
        editor.putString("senha", senha);
        editor.apply();

        Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validarEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private String validarSegurancaSenha(String senha) {
        if (senha.length() <= 5) {
            return "A senha deve ter mais de 5 caracteres.";
        }

        boolean temMaiuscula = false;
        boolean temNumero = false;

        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) temMaiuscula = true;
            if (Character.isDigit(c)) temNumero = true;
        }

        if (!temMaiuscula) return "A senha precisa de uma letra maiúscula.";
        if (!temNumero) return "A senha precisa de um número.";
        return null;
    }
}