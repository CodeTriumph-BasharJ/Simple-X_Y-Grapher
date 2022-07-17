/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.graphing;

/**
 *
 * @author Bashar Jirjees
 */

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;


public class Graphing extends JPanel {
    
 
    List <String> x_y_Coordinates;
    final String x_axis_label;
    final String y_axis_label;
    
    /**
     * Graphing class constructor that accepts and organizes file's data array.
     * 
     * @param data data list that contains the data read and passed form the csv file.
     */
    public Graphing(List<String> data){
        
      x_y_Coordinates = new ArrayList<>(data);
      x_y_Coordinates = adjustData(x_y_Coordinates);
      x_axis_label = x_y_Coordinates.get(0);
      y_axis_label = x_y_Coordinates.get(1);
    }
    
    /**
     * paintComponent method from JPanel super class is overriden to generate the graphing plot.
     * 
     * @param graph graph class object that represent the 2D graph that is plotted.
     */
    @Override
    protected void paintComponent(Graphics graph){
        
        
        super.paintComponent(graph);
        Graphics2D plot = (Graphics2D) graph;
       
        plot.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        drawAxis_and_Text(plot);
        draw_Data(x_y_Coordinates,plot);
       
    }
    
    /**
     * drawAxis_and_Text method that draws both x and y axis without the data.
     * 
     * @param plot plot a Graphics2D class objet that is used to set the dimensions
     * necessary for both axis.
     */
    private void drawAxis_and_Text(Graphics2D plot){
        
        
        plot.setColor(Color.BLACK);
        plot.draw(new Line2D.Double(60,400,360,400));
        plot.draw(new Line2D.Double(60,100,60,400));
        plot.setFont(new Font("Serif Plain",Font.BOLD,15));
        plot.drawString(x_axis_label,400,400);
        plot.drawString(y_axis_label,85,90);
        plot.setFont(new Font("Serif Plain",Font.BOLD,10));
        for(int x = 75, y = 385; x<=400 && y >=100 ; x+=30, y-=30){
            
        plot.draw(new Line2D.Double(x,410,x,400));
        plot.draw(new Line2D.Double(50,y,60,y));
       
        
        }
   
    }
    
    /**
     * adjustData method that takes the file's data list and removes all commas 
     * and separates all the 2 words in each index too a single index.
     * 
     * @param Data Data list that contains all file data.
     * @return Data list.
     */
    private List<String> adjustData(List<String> Data){
        
        List <String> dataFinal = new ArrayList<>();
        
        Data.stream().forEach(e -> {
         int i = 0;
         final StringBuilder s = new StringBuilder();
         
         while(i < e.length()){
         if(e.charAt(i) != ',') {
             s.append(e.charAt(i));
             
         }else{
             dataFinal.add(s.toString());
             s.delete(0, s.length());
         }
         ++i;
            }
        dataFinal.add(s.toString());
         
        });
        
        return dataFinal;
    }
    
    /**
     * draw_Data method that plots the x and y axis data and scale them appropriately.
     * 
     * @param data data adjusted list.
     * @param plot plot a Graphics2D object that is used to plot the data and 
     * draw each axis scale.
     */
    private void draw_Data(List <String> data, Graphics2D plot){
        List<Double> x_axis_data = new ArrayList<>(), y_axis_data = new ArrayList<>();
        List<Double> unmodified_x_axis_data = new ArrayList<>(),unmodified_y_axis_data = new ArrayList<>();
       
        DecimalFormat decimalRound = new DecimalFormat("0000E0");
        
        for(int i = 2; i < data.size() - 1; i+=2){
            x_axis_data.add(Double.valueOf(data.get(i)));
            y_axis_data.add(Double.valueOf(data.get(i + 1)));  
            unmodified_x_axis_data.add(Double.valueOf(data.get(i)));
            unmodified_y_axis_data.add(Double.valueOf(data.get(i + 1)));
        }
      
        Collections.sort(x_axis_data);
        Collections.sort(y_axis_data);
       
        plot.drawString(String.valueOf(decimalRound.format(x_axis_data.get(0))),60,420);
        plot.drawString(String.valueOf(decimalRound.format(x_axis_data.get(x_axis_data.size() - 1))),330,420);
        
        plot.drawString(String.valueOf(decimalRound.format(y_axis_data.get(0))),10,390);
        plot.drawString(String.valueOf(decimalRound.format(y_axis_data.get(y_axis_data.size() - 1))),10,114);
        if(x_axis_data.size()>2) {
            
            if(Double.compare(x_axis_data.get(x_axis_data.size() / 2),x_axis_data.get(x_axis_data.size() - 1)) != 0)
                plot.drawString(String.valueOf(decimalRound.format(x_axis_data.get((Integer)(x_axis_data.size()/2)))), 180, 420);
            
            if(Double.compare(y_axis_data.get(y_axis_data.size() / 2),y_axis_data.get(y_axis_data.size() - 1)) != 0)
                plot.drawString(String.valueOf(decimalRound.format(y_axis_data.get((Integer)(y_axis_data.size()/2)))), 10, 237);
        }
     
        draw_Dots(unmodified_x_axis_data,unmodified_y_axis_data,x_axis_data,
                y_axis_data, plot);
      
    }
    
    /**
     * 
     * draw_Dots method that plots all the data on the graph.
     * 
     * @param unsorted_x_values unsorted_x_values list with unsorted x axis data.
     * @param unsorted_y_values unsorted_y_values list with unsorted y axis data.
     * @param sorted_x_values sorted_x_values list with sorted x axis data.
     * @param sorted_y_values sorted_y_values list with sorted y axis data.
     * @param plot plot a Graphics2D object that is used to plot the data.
     */
    private void draw_Dots(List <Double> unsorted_x_values, List <Double> unsorted_y_values,List <Double> sorted_x_values,
                           List <Double> sorted_y_values, Graphics2D plot){
     
    final int size = sorted_x_values.size();
    final double difference_x = sorted_x_values.get(size - 1) - sorted_x_values.get(0),
                 difference_y = sorted_y_values.get(size - 1) - sorted_y_values.get(0);
    
    double first_half_x , second_half_x, first_half_y , second_half_y,
           position_x, position_y;

    for(int j = 0; j < size ; j++){
       
            first_half_x = (unsorted_x_values.get(j)- sorted_x_values.get(0))/ difference_x;
            second_half_x = (sorted_x_values.get(size - 1)- unsorted_x_values.get(j)) / difference_x;  
            position_x = first_half_x == second_half_x ? (360 / 2) : (300 * second_half_x);
             
             if(second_half_x == 0) position_x = 340;
             else if(first_half_x == 0) position_x = 70;
             else if(Double.compare(first_half_x, second_half_x) > 0) position_x = (1 - second_half_x) * 300;
             
            first_half_y = (unsorted_y_values.get(j)- sorted_y_values.get(0)) / difference_y ;
            second_half_y = (sorted_y_values.get(size - 1)- unsorted_y_values.get(j)) / difference_y;
            position_y = first_half_y == second_half_y ? (360/2) : (300 * second_half_y);
             
             if(second_half_y == 0) position_y = 110;
             else if(first_half_y == 0) position_y = 380;
             else if (Double.compare(first_half_y, second_half_y) > 0) position_y = (1 - second_half_y) * 300;
             
            plot.draw(new Ellipse2D.Double(position_x, position_y, 7, 7));  
     }
  }
}
