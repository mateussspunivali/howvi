package br.com.how.gerenciamentodecolecoes.itens_colecao;

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
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.itens_colecao_fragment_main, container, false);

        //Caso não existir uma instância salva no estado ele mudar a tela para a listar itens de coleções
        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new br.com.how.gerenciamentodecolecoes.itens_colecao.ListarFragment()).commit();
        }

        //Busca os botões adicionar e listar
        Button btnAdicionar = v.findViewById(R.id.buttonAdicionar);
        Button btnListar = v.findViewById(R.id.buttonListar);

        //Sobscreve o listner de selecionar o botão listar para mudar a tela para a listar itens de coleções
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new ListarFragment()).commit();
            }
        });

        //Sobscreve o listner de selecionar o botão listar para mudar a tela para a adicionar um item de coleção
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new AdicionarFragment()).commit();
            }
        });

        return v;
    }
}