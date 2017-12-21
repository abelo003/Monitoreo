/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.view.slider;

/**
 *
 * @author acruzb
 */
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Demo application panel to display a range slider.
 */
public class RangeSliderDemo extends JPanel {

    private JLabel rangeSliderValue1 = new JLabel();
    private JLabel rangeSliderValue2 = new JLabel();
    private final RangeSlider rangeSlider = new RangeSlider();

    public RangeSliderDemo() {
        setLayout(new GridBagLayout());
        
        rangeSliderValue1.setHorizontalAlignment(JLabel.LEFT);
        rangeSliderValue2.setHorizontalAlignment(JLabel.RIGHT);
        
        rangeSlider.setPreferredSize(new Dimension(200, rangeSlider.getPreferredSize().height));
        rangeSlider.setMinimum(0);
        rangeSlider.setMaximum(23);
        
        // Add listener to update display.
        rangeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                RangeSlider slider = (RangeSlider) e.getSource();
                rangeSliderValue1.setText(formatTime(slider, false));
                rangeSliderValue2.setText(formatTime(slider, true));
            }
        });

        add(rangeSliderValue1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(rangeSliderValue2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(rangeSlider      , new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        display();
    }
    
    public String formatTime(RangeSlider slider, boolean isUpper){
        return String.format("%02d%s", isUpper ? slider.getUpperValue(): slider.getValue(), ":00");
    }
    
    public String getDesde(){
        return rangeSliderValue1.getText();
    }
    
    public String getHasta(){
        return rangeSliderValue2.getText();
    }
    
    public void setTimeRange(int desde, int hasta){
        rangeSlider.setValue(desde);
        rangeSlider.setUpperValue(hasta);
    }
    
    public void display() {
        // Initialize values.
        rangeSlider.setValue(9);
        rangeSlider.setUpperValue(19);
        
        // Initialize value display.
        rangeSliderValue1.setText(formatTime(rangeSlider, false));
        rangeSliderValue2.setText(formatTime(rangeSlider, true));
    }
    
}
