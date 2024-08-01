/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoreMasterMarket.gui.tools;

import java.util.Calendar;

/**
 *
 * @author rodolfobrandao
 */
public class CondicaoTempo {

    public static String obterSaudacao() {
            Calendar c = Calendar.getInstance();
            int hora = c.get(Calendar.HOUR_OF_DAY);

            if (hora >= 0 && hora < 6) {
                return "Boa madrugada";
            } else if (hora >= 6 && hora < 12) {
                return "Bom dia";
            } else if (hora >= 12 && hora < 18) {
                return "Boa tarde";
            } else {
                return "Boa noite";
            }
        }

        public static void main(String[] args) {
            System.out.println(obterSaudacao());
        }
    }
