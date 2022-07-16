/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.graphing;

/**
 * @author Bashar Jirjees
 */
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.stream.Stream;
import java.util.Scanner;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        // TODO code application logic here
   
    JFrame frame =new JFrame();
    frame.add(new Graphing(file_open()));
    frame.setSize(600,600);
    frame.setLocation(400,400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  
    
    }
    
    private static List<String> file_open(){
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Input File Address: ");
        String fileName = scan.nextLine();
        Path path = Paths.get(fileName);
        if(!path.toString().contains(".csv")){
            System.out.println("Please input a .csv file only.");
            System.exit(0);
            
        }
        return read_file(path);
        
        
    }
    private static List<String> read_file(Path path){
        List <String> Data = new ArrayList<>();
        try(Stream <String> fileData = Files.lines(path)){
            
            fileData.forEach(e -> {
                
            Data.add(e);
            
            });
            
          
        }catch(Exception m){
            System.out.println(m.getMessage());
            
            
        } 
    return Data;
    }
}

