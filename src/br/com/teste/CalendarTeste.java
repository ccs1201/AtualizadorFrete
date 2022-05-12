/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.teste;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author ti
 */
public class CalendarTeste {
    public static void main(String[] args) {
         GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT-3"),new Locale("pt_BR"));
         
         
         String calString;
        calString = ""+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
         
         System.out.println(calString);
      
    }
}
