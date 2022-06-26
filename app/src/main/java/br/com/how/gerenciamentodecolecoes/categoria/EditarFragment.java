package br.com.how.gerenciamentodecolecoes.categoria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.how.gerenciamentodecolecoes.R;
import br.com.how.gerenciamentodecolecoes.database.DatabaseHelper;

public class EditarFragment extends Fragment {

    EditText etNome;
    DatabaseHelper databaseHelper;
    Categoria c;

    public EditarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categoria_fragment_editar, container, false);

        Bundle b = getArguments();
        //Pega o id dos parâmetros
        int id_categoria = b.getInt("id");
        databaseHelper = new DatabaseHelper(getActivity());
        //Busca a categoria no banco de dados pelo id
        c = databaseHelper.getByIdCategoria(id_categoria);

        //Busca os campos do formulário e preenche eles
        etNome = v.findViewById(R.id.editTextNomeCategoria);
        etNome.setText(c.getNome());

        //Busca o botão editar
        Button btnEditar = v.findViewById(R.id.buttonEditarCategoria);

        //Sobscreve o listner de selecionar o botão para chamar o método editar da própria classe
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar(id_categoria);
            }
        });

        //Busca o botão excluir
        Button btnExcluir = v.findViewById(R.id.buttonExcluirCategoria);

        //Sobscreve o listner de selecionar o botão para mostrar um alert dialog
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_excluir_categoria);
                //Caso o botão "sim" for selecionar ele chama o método excluir da própria classe
                builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir(id_categoria);
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
        if (etNome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else {
            //Instância uma nova categoria preenche com os campos do formulário
            c = new Categoria();
            c.setId(id);
            c.setNome(etNome.getText().toString());
            //Atualiza a categoria no banco de dados
            databaseHelper.updateCategoria(c);
            Toast.makeText(getActivity(), "Categoria editada!", Toast.LENGTH_LONG).show();
            //Muda a tela para a listagem de categoria
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameCategoria, new ListarFragment()).commit();
        }
    }

    private void excluir (int id) {
        //Instância uma nova categoria preenche com o id
        c = new Categoria();
        c.setId(id);
        //Remove a categoria no banco de dados
        databaseHelper.deleteCategoria(c);
        Toast.makeText(getActivity(), "Categoria excluída!", Toast.LENGTH_LONG).show();
        //Muda a tela para a listagem de categoria
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameCategoria, new ListarFragment()).commit();
    }
}