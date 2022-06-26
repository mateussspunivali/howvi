package br.com.how.gerenciamentodecolecoes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.how.gerenciamentodecolecoes.categoria.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Caso não existir uma instância salva no estado ele mudar a tela para a listar categorias
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new MainFragment()).commit();
        }
    }
}