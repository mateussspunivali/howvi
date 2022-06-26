package br.com.how.gerenciamentodecolecoes.itens_colecao;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class EditarFragment extends Fragment {

    EditText etNome;
    EditText etDescricao;
    Spinner spColecao;
    ArrayList<Integer> listColecaoId;
    ArrayList<String> listColecaoName;
    DatabaseHelper databaseHelper;
    ItensColecao iC;

    public EditarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.itens_colecao_fragment_editar, container, false);

        //Pega o id dos parâmetros
        Bundle bundle = getArguments();
        int id_itens_colecao = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        //Busca o item de coleção no banco de dados pelo id
        iC = databaseHelper.getByIdItensColecao(id_itens_colecao);

        //Busca os campos do formulário
        spColecao = v.findViewById(R.id.spinnerColecaoItensColecao);
        etNome = v.findViewById(R.id.editTextNomeItensColecao);
        etDescricao = v.findViewById(R.id.editTextDescricaoItensColecao);

        //Busca os nomes de todas as coleções
        listColecaoId = new ArrayList<>();
        listColecaoName = new ArrayList<>();
        databaseHelper.getAllNameColecao(listColecaoId, listColecaoName);

        //Preenche o spinner de coleções com os valores encontrados do banco de dados
        ArrayAdapter<String> spColecaoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listColecaoName);
        spColecao.setAdapter(spColecaoArrayAdapter);

        //Preenche os campos do formulário
        etNome.setText(iC.getNome());
        etDescricao.setText(iC.getDescricao());
        spColecao.setSelection(listColecaoId.indexOf(iC.getId_colecao()));

        //Busca o botão editar
        Button btnEditar = v.findViewById(R.id.buttonEditarItensColecao);

        //Sobscreve o listner de selecionar o botão para chamar o método editar da própria classe
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_itens_colecao);
            }
        });

        //Busca o botão excluir
        Button btnExcluir = v.findViewById(R.id.buttonExcluirItensColecao);

        //Sobscreve o listner de selecionar o botão para mostrar um alert dialog
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_itens_colecao);
                //Caso o botão "sim" for selecionar ele chama o método excluir da própria classe
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_itens_colecao);
                    }
                });
                //Caso o botão "não" for selecionado ele não faz nada
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        //Valida se os campos do fomulário não foram enviados vázios quando selecionado o botão editar
        if (spColecao.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a coleção!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a descrição!", Toast.LENGTH_LONG).show();
        } else {
            //Instância um novo item de coleção preenche com os campos do formulário
            ItensColecao iC = new ItensColecao();
            iC.setId(id);
            String nomeColecao = spColecao.getSelectedItem().toString();
            iC.setId_colecao(listColecaoId.get(listColecaoName.indexOf(nomeColecao)));
            iC.setNome(etNome.getText().toString());
            iC.setDescricao(etDescricao.getText().toString());
            //Atualiza a coleção no banco de dados
            databaseHelper.updateItensColecao(iC);
            Toast.makeText(getActivity(), "Item da Coleção editada!", Toast.LENGTH_LONG).show();
            //Muda a tela para a listagem de itens de coleção
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new br.com.how.gerenciamentodecolecoes.itens_colecao.ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        //Instância um novo item de coleção preenche com o id
        iC = new ItensColecao();
        iC.setId(id);
        //Remove o item de coleção no banco de dados
        databaseHelper.deleteItensColecao(iC);
        Toast.makeText(getActivity(), "Item da Coleção excluída!", Toast.LENGTH_LONG).show();
        //Muda a tela para a listagem de itens de coleção
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new ListarFragment()).commit();
    }
}