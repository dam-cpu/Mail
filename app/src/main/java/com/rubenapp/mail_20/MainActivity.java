package com.rubenapp.mail_20;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private  EditText edtDe;
    private EditText edtPara;
    private EditText edtCC;
    private EditText edtCCo;
    private EditText edtAsunto;
    private EditText edtMensaje;
    private Button btnAceptar;
    private Button btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDe = findViewById(R.id.edtDe);
        edtPara = findViewById(R.id.edtPara);
        edtCC = findViewById(R.id.edtCC);
        edtCCo = findViewById(R.id.edtCCo);
        edtAsunto = findViewById(R.id.edtAsunto);
        edtMensaje = findViewById(R.id.edtMensaje);

        btnAceptar = findViewById(R.id.btnAceptar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnAceptar.setOnClickListener(v ->{
            if(validarFormulario()){
                enviarCorreo();
            }
        });
        btnCancelar.setOnClickListener(v->{
            edtPara.setText("");
            edtCC.setText("");
            edtCCo.setText("");
            edtAsunto.setText("");
            edtMensaje.setText("");
        });


    }

    private void enviarCorreo(){
        String destinatario = edtPara.getText().toString().trim();
        String copia = edtCC.getText().toString().trim();
        String copiaOculta = edtCCo.getText().toString().trim();
        String asunto = edtAsunto.getText().toString().trim();
        String mensaje = edtMensaje.getText().toString().trim();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario});
        if(!copia.isEmpty()){
            intent.putExtra(Intent.EXTRA_CC, new String[]{copia});

        }
        if(!copiaOculta.isEmpty()){
            intent.putExtra(Intent.EXTRA_BCC, new String[]{copiaOculta});

        }
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        intent.putExtra(Intent.EXTRA_TEXT, mensaje);
        try {
            startActivity(Intent.createChooser(
                    intent,
                    "Selecciona una aplicación de correo"
            ));
        } catch (ActivityNotFoundException e){
            Toast.makeText(
                    this,
                    "No hay aplicaciones de correo disponibles",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private boolean validarFormulario(){
        String destinatario = edtPara.getText().toString().trim();
        String asunto = edtAsunto.getText().toString().trim();
        if (destinatario.isEmpty()) {
            edtPara.setError("Campo obligatorio");
            edtPara.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(destinatario).matches()) {
            edtPara.setError("Correo electrónico inválido");
            edtPara.requestFocus();
            return false;
        }

        if (asunto.isEmpty()) {
            edtAsunto.setError("Campo obligatorio");
            edtAsunto.requestFocus();
            return false;
        }

        return true;
    }
}