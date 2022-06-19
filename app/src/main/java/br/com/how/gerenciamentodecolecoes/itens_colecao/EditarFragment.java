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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.itens_colecao_fragment_editar, container, false);
        Bundle bundle = getArguments();
        int id_itens_colecao = bundle.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        iC = databaseHelper.getByIdItensColecao(id_itens_colecao);

        spColecao = v.findViewById(R.id.spinnerColecaoItensColecao);
        etNome = v.findViewById(R.id.editTextNomeItensColecao);
        etDescricao = v.findViewById(R.id.editTextDescricaoItensColecao);

        listColecaoId = new ArrayList<>();
        listColecaoName = new ArrayList<>();
        databaseHelper.getAllNameColecao(listColecaoId, listColecaoName);

        ArrayAdapter<String> spColecaoArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listColecaoName);
        spColecao.setAdapter(spColecaoArrayAdapter);

        etNome.setText(iC.getNome());
        etDescricao.setText(iC.getDescricao());
        spColecao.setSelection(listColecaoId.indexOf(iC.getId_colecao()));

        Button btnEditar = v.findViewById(R.id.buttonEditarItensColecao);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_itens_colecao);
            }
        });

        Button btnExcluir = v.findViewById(R.id.buttonExcluirItensColecao);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_itens_colecao);
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_itens_colecao);
                    }
                });
                builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Não faz nada
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return v;
    }

    private void editar (int id) {
        if (spColecao.getSelectedItem() == null) {
            Toast.makeText(getActivity(), "Por favor, selecione a coleção!", Toast.LENGTH_LONG).show();
        } else if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (etDescricao.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a descrição!", Toast.LENGTH_LONG).show();
        } else {
            ItensColecao iC = new ItensColecao();
            iC.setId(id);
            String nomeColecao = spColecao.getSelectedItem().toString();
            iC.setId_colecao(listColecaoId.get(listColecaoName.indexOf(nomeColecao)));
            iC.setNome(etNome.getText().toString());
            iC.setDescricao(etDescricao.getText().toString());
            databaseHelper.updateItensColecao(iC);
            Toast.makeText(getActivity(), "Item da Coleção editada!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new br.com.how.gerenciamentodecolecoes.itens_colecao.ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        iC = new ItensColecao();
        iC.setId(id);
        databaseHelper.deleteItensColecao(iC);
        Toast.makeText(getActivity(), "Item da Coleção excluída!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameItensColecao, new ListarFragment()).commit();
    }
}