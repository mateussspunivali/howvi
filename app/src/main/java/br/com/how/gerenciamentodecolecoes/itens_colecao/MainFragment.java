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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.itens_colecao_fragment_main, container, false);

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new br.com.how.gerenciamentodecolecoes.itens_colecao.ListarFragment()).commit();
        }

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionar);
        Button btnListar = v.findViewById(R.id.buttonListar);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new ListarFragment()).commit();
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new AdicionarFragment()).commit();
            }
        });

        return v;
    }
}