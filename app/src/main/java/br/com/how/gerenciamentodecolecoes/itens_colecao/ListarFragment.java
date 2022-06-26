package br.com.how.gerenciamentodecolecoes.itens_colecao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.com.how.gerenciamentodecolecoes.R;
import br.com.how.gerenciamentodecolecoes.database.DatabaseHelper;

public class ListarFragment extends Fragment {

    public ListarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.itens_colecao_fragment_listar, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        //Busca a listView de itens de coleções
        ListView lv = v.findViewById(R.id.listViewItensColecoes);
        //Busca todas os itens de coleções preenchendo as list views
        databaseHelper.getAllItensColecao(getContext(), lv);

        //Sobscreve o listner de selecionar um item da listView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pega o id do item de coleção e coloca como parâmetro para o fragment de editar
                TextView tvId = view.findViewById(R.id.textViewIdListarItensColecao);
                Bundle b = new Bundle();
                b.putInt("id", Integer.parseInt(tvId.getText().toString()));

                br.com.how.gerenciamentodecolecoes.itens_colecao.EditarFragment editar = new EditarFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                editar.setArguments(b);
                //Muda a tela para a editar o item de coleção
                ft.replace(R.id.frameItensColecao, editar).commit();
            }
        });
        return v;
    }
}