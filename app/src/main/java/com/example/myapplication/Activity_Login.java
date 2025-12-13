package com.example.myapplication;

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

public class Activity_Login extends AppCompatActivity {

    Button btnRegistrar;
    Button btnEntrar;
    EditText edtEmailLogin;
    EditText edtSenhaLogin;
    private static final long TEMPO_LIMITE = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
        boolean estaLogado = prefs.getBoolean("estaLogado", false);
        long ultimoAcesso = prefs.getLong("ultimo_acesso", 0);
        long agora = System.currentTimeMillis();

        if (estaLogado) {
            if ((agora - ultimoAcesso) < TEMPO_LIMITE) {
                Intent irParaHome = new Intent(this, Activity_Home.class);
                startActivity(irParaHome);
                finish();
                return;
            } else {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("estaLogado", false);
                editor.apply();
                Toast.makeText(this, "Sessão expirada. Faça login novamente.", Toast.LENGTH_LONG).show();
            }
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnEntrar = findViewById(R.id.btnEntrar);
        edtEmailLogin = findViewById(R.id.edtEmail);
        edtSenhaLogin = findViewById(R.id.edtSenha);
    }
    public void registrar(View v) {
        Intent registrar = new Intent(this, Activity_Registrar.class);
        startActivity(registrar);
    }
    public void Entrar(View v) {
        String emailDigitado = edtEmailLogin.getText().toString().trim();
        String senhaDigitada = edtSenhaLogin.getText().toString();

        edtEmailLogin.setError(null);
        edtSenhaLogin.setError(null);

        if (emailDigitado.isEmpty()) {
            edtEmailLogin.setError("Preencha o e-mail");
            edtEmailLogin.requestFocus();
            return;
        }
        if (senhaDigitada.isEmpty()) {
            edtSenhaLogin.setError("Preencha a senha");
            edtSenhaLogin.requestFocus();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
        String emailSalvo = prefs.getString("email", "");
        String senhaSalva = prefs.getString("senha", "");

        if (emailDigitado.equals(emailSalvo) && senhaDigitada.equals(senhaSalva)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("estaLogado", true);
            editor.putLong("ultimo_acesso", System.currentTimeMillis());
            editor.apply();

            Toast.makeText(this, "Bem-vindo(a)!", Toast.LENGTH_SHORT).show();

            Intent entrar = new Intent(this, Activity_Home.class);
            entrar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(entrar);
            finish();

        } else {
            String msg = "E-mail ou senha incorretos";
            edtEmailLogin.setError(msg);
            edtSenhaLogin.setError(msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            edtEmailLogin.requestFocus(); // Foca no email para corrigir
        }
    }
}