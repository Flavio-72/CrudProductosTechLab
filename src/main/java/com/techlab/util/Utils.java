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

    }
