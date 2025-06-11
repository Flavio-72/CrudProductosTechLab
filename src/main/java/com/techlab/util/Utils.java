package com.techlab.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Utils {

    public static boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        return email.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    }
    public static String separadorDeMiles(double numero) {
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setGroupingSeparator('.');

        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolo);

        return formato.format(numero);
        }

    public static String formatearTitleCase(String texto) {
        if (texto == null || texto.trim().isEmpty()) return "";

        String[] palabras = texto.trim().toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palabras.length; i++) {
            if (i > 0) resultado.append(" ");
            if (palabras[i].length() > 0) {
                resultado.append(palabras[i].substring(0, 1).toUpperCase())
                        .append(palabras[i].substring(1));
            }
        }

        return resultado.toString();
    }

    }
