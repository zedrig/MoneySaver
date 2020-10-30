package com.example.moneysaver.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.moneysaver.R;
import com.example.moneysaver.VistaNavigationPrincipal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PerfilFragment extends Fragment {

    ListView lista;
    String vectorOpc [] = new String[] {"Cambiar correo","Cambiar contraseña","Cerrar sesión"};

    private PerfilViewModel mViewModel;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.perfil_fragment, container, false);

        //((VistaNavigationPrincipal) getActivity()).hideFloatingActionButton();

        lista = root.findViewById(R.id.opcionesPerfil);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), R.layout.design_listview, vectorOpc);

        lista.setAdapter(adaptador);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

}