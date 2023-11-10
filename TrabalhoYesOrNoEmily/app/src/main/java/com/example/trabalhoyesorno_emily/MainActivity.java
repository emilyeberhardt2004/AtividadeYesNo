package com.example.trabalhoyesorno_emily;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String URL = "https://yesno.wtf/";

    private Retrofit retrofit;

    private Button buttonDecide;

    private TextInputEditText txtAnswer;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDecide = findViewById(R.id.buttonDecide);
        progressBar = findViewById(R.id.progressBar);
        txtAnswer = findViewById(R.id.answer);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        progressBar.setVisibility(View.GONE);


        buttonDecide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonDecide) {
            // Chama o método para consultar o CEP
            consultarANSWER();
        }
    }


    private void consultarANSWER(){

        String sCep = txtAnswer.getText().toString().trim();

        RestService restService = retrofit.create(RestService.class);

        Call<YesOrNo> call = restService.consultarANSWER();

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<YesOrNo>() {
            @Override
            public void onResponse(Call<YesOrNo> call, Response<YesOrNo> response) {
                if (response.isSuccessful()) {
                    YesOrNo yesorno = response.body();

                    if(yesorno.getAnswer().equals("yes")){

                        txtAnswer.setText("Sim");
                    }
                    else{
                        txtAnswer.setText("Não");
                    }

                    Toast.makeText(getApplicationContext(), "Resposta gerada com sucesso", Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<YesOrNo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao tentar a resposta. Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

