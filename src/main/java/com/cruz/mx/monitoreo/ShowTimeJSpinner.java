/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo;

import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author acruzb
 */
public class ShowTimeJSpinner{
  public static void main(String[] args) {
  ShowTimeJSpinner h = new ShowTimeJSpinner();
  }

  public ShowTimeJSpinner(){
  JFrame frame = new JFrame("Creating JSpinner Component with time");
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  Date date = new Date();
  SpinnerDateModel sm = 
  new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
  JSpinner spinner = new JSpinner(sm);
  JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "hh:mm");
  spinner.setEditor(de);
  frame.add(spinner,BorderLayout.NORTH);
  frame.setSize(100,100);
  frame.setVisible(true);
  }
}
