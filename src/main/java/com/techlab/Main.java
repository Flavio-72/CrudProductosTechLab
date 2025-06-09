package com.techlab;

import com.techlab.repository.Inventario;
import com.techlab.ui.InterfazUsuario;

public class Main {
    public static void main(String[] args) {
        Inventario.inicializar();

        InterfazUsuario interfaz = new InterfazUsuario();
        interfaz.iniciar();
    }
}