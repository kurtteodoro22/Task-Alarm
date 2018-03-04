package com.example.sprike.myapplication;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentItens extends Fragment{

    ListView listaItens;
    List<String> lista;
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_itens, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(lista == null) {
            lista = new ArrayList<String>();
        }

        listaItens = (ListView) getView().findViewById(R.id.lista_itens);
        listaItens.setOnItemLongClickListener(itemLongClickListener());
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);
        listaItens.setAdapter(adapter);
    }

    public void load(String item){
        if(lista != null){
            lista.add(0, item);
            adapter.notifyDataSetChanged();
        }else{

            lista = new ArrayList<String>();
            lista.add(0, item);
        }
    }

    public AdapterView.OnItemLongClickListener itemLongClickListener(){
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int ind, long l) {
                android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(getActivity());
                alert.setIcon(android.R.drawable.ic_dialog_info);
                alert.setTitle("Deletar");
                alert.setMessage("Tem certeza que deseja apagar essa atividade ?");
                alert.setNegativeButton("Não", null);
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        lista.remove(ind);
                        adapter.notifyDataSetChanged();

                        if(lista.isEmpty()){
                            MainActivity.passarFragment = false;
                            FragmentNoItens fragmentNoItens = new FragmentNoItens();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.layoutFragment, fragmentNoItens, "fragment no itens");
                            ft.commit();
                        }

                    }
                });
                alert.create().show();
                return false;
            }
        };

    }

    public void deleteAll(){

        if(lista.isEmpty()){
            Toast.makeText(getActivity(), "Não há atividades para serem apagadas", Toast.LENGTH_SHORT).show();
        }else{
            android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(getActivity());
            alert.setIcon(android.R.drawable.ic_dialog_info);
            alert.setTitle("Deletar");
            alert.setMessage("Tem certeza que deseja apagar todas essas atividades ?");
            alert.setNegativeButton("Não", null);
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    lista.clear();
                    adapter.notifyDataSetChanged();

                    FragmentNoItens fragmentNoItens = new FragmentNoItens();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.layoutFragment, fragmentNoItens, "fragment no itens");
                    ft.commit();

                }
            });
            alert.create().show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("task", (ArrayList<String>) lista);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            lista = savedInstanceState.getStringArrayList("task");
            listaItens = (ListView) getView().findViewById(R.id.lista_itens);
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lista);
            listaItens.setAdapter(adapter);
        }
        super.onActivityCreated(savedInstanceState);
    }


}
