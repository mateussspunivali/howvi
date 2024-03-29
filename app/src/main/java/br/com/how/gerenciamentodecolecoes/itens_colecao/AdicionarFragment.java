package br.com.how.gerenciamentodecolecoes.itens_colecao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.how.gerenciamentodecolecoes.R;
import br.com.how.gerenciamentodecolecoes.database.DatabaseHelper;

public class AdicionarFragment extends Fragment {

    EditText etNome;
    EditText etDescricao;
    Spinner spColecao;
    ArrayList<Integer> listColecaoId;
    ArrayList<String> listColecaoName;
    DatabaseHelper databaseHelper;

    public AdicionarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.itens_colecao_fragment_adicionar, container, false);

        //Busca os campos do formulário
        spColecao = v.findViewById(R.id.spinnerColecaoItensColecao);
        etNome = v.findViewById(R.id.editTextNomeItensColecao);
        etDescricao = v.findViewById(R.id.editTextDescricaoItensColecao);

        databaseHelper = new DatabaseHelper(getActivity());

        //Busca os nomes de todas as coleções
        listColecaoId = new ArrayList<>();
        listColecaoName = new ArrayList<>();
        databaseHelper.getAllNameColecao(listColecaoId, listColecaoName);

        //Preenche o spinner de coleções com os valores encontrados do banco de dados
        ArrayAdapter<String> spColecaoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listColecaoName);
        spColecao.setAdapter(spColecaoArrayAdapter);

        //Busca o button adicionar
        Button btnAdicionar = v.findViewById(R.id.buttonAdicionarItensColecao);

        //Sobscreve o listner de selecionar o botão para chamar o método adicionar da própria classe
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionar();
            }
        });

        return v;
    }

    private void adicionar () {
        //Valida se os campos do fomulário não foram enviados vázios quando selecionado o botão adicionar
        if (spColecao.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a coleção!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a descrição!", Toast.LENGTH_LONG).show();
        } else {
            //Instância um novo item de coleção preenche com os campos do formulário
            ItensColecao iC = new ItensColecao();
            String nomeColecao = spColecao.getSelectedItem().toString();
            iC.setId_colecao(listColecaoId.get(listColecaoName.indexOf(nomeColecao)));
            iC.setNome(etNome.getText().toString());
            iC.setDescricao(etDescricao.getText().toString());
            //Salva a coleção no banco de dados
            databaseHelper.createItensColecao(iC);
            Toast.makeText(getActivity(), "Item da Coleção salva!", Toast.LENGTH_LONG).show();
            //Muda a tela para a listagem de itens de coleção
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new ListarFragment()).commit();
        }
    }
}