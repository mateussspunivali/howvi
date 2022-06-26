package br.com.how.gerenciamentodecolecoes;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MenuFragment extends Fragment {

    public MenuFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            //Caso seleciona o item do menu de categoria irá mudar a tela para a listagem de categorias
            case R.id.menu_categoria:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new br.com.how.gerenciamentodecolecoes.categoria.MainFragment()).commit();
                break;
            //Caso seleciona o item do menu de coleção irá mudar a tela para a listagem de coleções
            case R.id.menu_colecao:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new br.com.how.gerenciamentodecolecoes.colecao.MainFragment()).commit();
                break;
            //Caso seleciona o item do menu de item de coleção irá mudar a tela para a listagem de itens de coleções
            case R.id.menu_itens_colecao:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new br.com.how.gerenciamentodecolecoes.itens_colecao.MainFragment()).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}