/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author acruzb
 */
public class GraficaBarras extends JPanel{
    
    public GraficaBarras(String tipoCambio, double minimo, double actual, double maximo) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "",
                "",
                createDataset(tipoCambio, minimo, actual, maximo),
                PlotOrientation.VERTICAL,
                true, true, false);

        final CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        //Indicar el label vertical
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE);//Color de las barras
        renderer.setDrawBarOutline(true); //Borde a las barras
        renderer.setSeriesItemLabelGenerator(0,
                new StandardCategoryItemLabelGenerator(
                        "{2}",
                        NumberFormat.getCurrencyInstance(Locale.US))
        );
        renderer.setSeriesItemLabelsVisible(0, true);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        chartPanel.zoomOutBoth(0.1, 0.1);
        super.setLayout(new BorderLayout());
        super.add(chartPanel, BorderLayout.CENTER);
        super.repaint();
    }

    private CategoryDataset createDataset(String tipoCambio, double minimo, double actual, double maximo) {
        final DefaultCategoryDataset dataset
                = new DefaultCategoryDataset();

        dataset.addValue(minimo, tipoCambio, "Mínimo");
        dataset.addValue(actual, tipoCambio, "Actual");
        dataset.addValue(maximo, tipoCambio, "Máximo");

        return dataset;
    }
    
}
