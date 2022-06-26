package br.com.how.gerenciamentodecolecoes.colecao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.how.gerenciamentodecolecoes.R;

public class MainFragment extends Fragment {

    public MainFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.colecao_fragment_main, container, false);

        //Caso não existir uma instância salva no estado ele mudar a tela para a listar coleções
        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameColecao, new br.com.how.gerenciamentodecolecoes.colecao.ListarFragment()).commit();
        }

        //Busca os botões adicionar e listar
        Button btnAdicionar = v.findViewById(R.id.buttonAdicionar);
        Button btnListar = v.findViewById(R.id.buttonListar);

        //Sobscreve o listner de selecionar o botão listar para mudar a tela para a listar coleções
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameColecao, new ListarFragment()).commit();
            }
        });

        //Sobscreve o listner de selecionar o botão listar para mudar a tela para a adicionar uma coleção
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameColecao, new AdicionarFragment()).commit();
            }
        });

        return v;
    }
}