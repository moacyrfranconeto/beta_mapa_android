package br.com.moacyr.franco.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaComponentes();
    }

    private void iniciaComponentes(){

        Button btBuscar = (Button) findViewById(R.id.btBuscar);
        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                EditText editText = (EditText) findViewById(R.id.txtNome);
                if(editText.getText().toString().isEmpty()){
                    editText.setError("Informe o nome");
                    return;
                }
                if(editText.getText().toString().length() < 3){
                    editText.setError("O nome precisa ter no mínimo 3 letras");
                    return;
                }

                String nome = editText.getText().toString();
                intent.putExtra("NOME", nome);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
