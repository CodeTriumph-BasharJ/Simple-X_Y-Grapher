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
    
    
    public Graphing(List<String> data){
        
      x_y_Coordinates = new ArrayList<>(data);  
      x_y_Coordinates = adjustData(x_y_Coordinates);
      
    }
    
    @Override
    protected void paintComponent(Graphics graph){
        
        
        super.paintComponent(graph);
        Graphics2D plot = (Graphics2D) graph;
       
        plot.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        drawAxis_and_Text(plot);
        draw_Data(x_y_Coordinates,plot);
       
    }
    private void drawAxis_and_Text(Graphics2D plot){
        
        plot.setColor(Color.BLACK);
        plot.draw(new Line2D.Double(60,400,360,400));
        plot.draw(new Line2D.Double(60,100,60,400));
        plot.setFont(new Font("Serif Plain",Font.PLAIN,10));
        
        for(int x = 75, y = 385; x<=400 && y >=100 ; x+=30, y-=30){
            
        plot.draw(new Line2D.Double(x,410,x,400));
        plot.draw(new Line2D.Double(50,y,60,y));
       
        
        }
        
        
    }
    
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
            
            plot.drawString(String.valueOf(decimalRound.format(x_axis_data.get((Integer)(x_axis_data.size()/2)))), 180, 420);
            plot.drawString(String.valueOf(decimalRound.format(y_axis_data.get((Integer)(y_axis_data.size()/2)))), 10, 237);
        }
     
        draw_Dots(unmodified_x_axis_data,unmodified_y_axis_data,x_axis_data,
                y_axis_data, plot);
      
    }
    
    private void draw_Dots(List <Double> unsorted_x_values, List <Double> unsorted_y_values,List <Double> sorted_x_values, List <Double> sorted_y_values, Graphics2D plot){
     
     final int size = sorted_x_values.size();
   
     
     List<Double> coordinates= new ArrayList<>();
     List<Double> x_scale= new ArrayList<>(x_interval(size));
     List<Double> y_scale= new ArrayList<>(y_interval(size));
     plot.setPaint(Color.BLUE);
     
    for(int i = 0; i < size - 1; ++i){
      
           coordinates.add (x_scale.get(sorted_x_values.indexOf(unsorted_x_values.get(i))));
           coordinates.add (y_scale.get(sorted_y_values.indexOf(unsorted_y_values.get(i))));
           
    }
      
    for(int i = 0; i < coordinates.size() - 1; i+=2)
        plot.draw(new Ellipse2D.Double(coordinates.get(i),coordinates.get(i+1),7,7));
            
       
    }
    
    private List<Double> x_interval(int size){
        
        List <Double> interval_x = new ArrayList<>();
        final int summation = (360 - 60)/ size;
        for(double i = 60; i <= 360; i+= summation ) interval_x.add(i);
        
        return interval_x;
    }
 
    private List<Double> y_interval(int size){
       
        List <Double> interval_y = new ArrayList<>();
        final int deduction = (400 - 100) / size;
        for(double i = 400; i >= 100 ; i-= deduction) interval_y.add(i);

        return interval_y;
        
    }
}
