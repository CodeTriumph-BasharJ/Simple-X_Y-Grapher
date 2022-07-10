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
    
    public Graphing(List<String> data){
        
      x_y_Coordinates = new ArrayList<>(data);
      x_y_Coordinates = adjustData(x_y_Coordinates);
      x_axis_label = x_y_Coordinates.get(0);
      y_axis_label = x_y_Coordinates.get(1);
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
        plot.setFont(new Font("Serif Plain",Font.BOLD,15));
        plot.drawString(x_axis_label,400,400);
        plot.drawString(y_axis_label,85,90);
        plot.setFont(new Font("Serif Plain",Font.BOLD,10));
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
            
            if(Double.compare(x_axis_data.get(x_axis_data.size() / 2),x_axis_data.get(x_axis_data.size() - 1)) != 0)
                plot.drawString(String.valueOf(decimalRound.format(x_axis_data.get((Integer)(x_axis_data.size()/2)))), 180, 420);
            
            if(Double.compare(y_axis_data.get(y_axis_data.size() / 2),y_axis_data.get(y_axis_data.size() - 1)) != 0)
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
     List<Double> drawn = new ArrayList<>();
     plot.setPaint(Color.BLUE);
     
    for(int i = 0; i < size - 1; ++i){
            if(Double.compare(sorted_x_values.get(i),sorted_x_values.get(i+1)) != 0)
                coordinates.add (x_scale.get(sorted_x_values.indexOf(unsorted_x_values.get(i))));
            else 
                coordinates.add(x_scale.get(sorted_x_values.lastIndexOf(sorted_x_values.get(i))));
                
            
           coordinates.add (y_scale.get(sorted_y_values.indexOf(unsorted_y_values.get(i))));
    }
    
    
    
    for(int i = 0, j = 0; i < coordinates.size() - 1 ; i+= 2){
        if(Double.compare(sorted_x_values.get(j),sorted_x_values.get(j+1)) != 0 && !drawn.contains(sorted_x_values.get(j))){
            plot.draw(new Ellipse2D.Double(coordinates.get(i),coordinates.get(i+1),7,7));
            
            
        } else if(!drawn.contains(sorted_x_values.get(j))) {
            draw_similar_x_values(coordinates,sorted_x_values,i, sorted_x_values.get(j), plot);
            drawn.add(sorted_x_values.get(j));
        }
        
        if(i % 2 == 0)++j;
        

            }
    }
    
    private List<Double> x_interval(int size){
        
        List <Double> interval_x = new ArrayList<>();
        
        final double summation = (double)(360 - 60)/ size;
        
        for(double i = 60; i <= 360; i+= summation ) interval_x.add(i);
        
        return interval_x;
    }
 
    private List<Double> y_interval(int size){
       
        List <Double> interval_y = new ArrayList<>();
        final double deduction = (double)(400 - 100) / size;
        
        for(double i = 390; i >= 100 ; i-= deduction) interval_y.add(i);

        return interval_y;
        
    }
    
    private void draw_similar_x_values(List<Double> coordinates, List<Double> sorted_x_values, int index, double repetitive, Graphics2D plot){
        
        int last_index =  coordinates.lastIndexOf(coordinates.get(index));
        final int counter = Collections.frequency(sorted_x_values, repetitive);
        
        //issue
        for(int i = 0; i < counter * 2; i+=2){
             plot.draw(new Ellipse2D.Double(coordinates.get(last_index),coordinates.get(i+1),7,7));
             
            
        }
        
    }
    
   
        private void draw_similar_y_values(List<Double> coordinates, List<Double> sorted_x_values, int index, double repetitive, Graphics2D plot){
        
        int last_index =  coordinates.lastIndexOf(coordinates.get(index));
        final int counter = Collections.frequency(sorted_x_values, repetitive);
        
        //issue
        for(int i = 0; i < counter * 2; i+=2){
             plot.draw(new Ellipse2D.Double(coordinates.get(last_index),coordinates.get(i+1),7,7));
             
            
        }
        
    }
    
}
