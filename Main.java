package ir.ac.aut;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

//        System.out.println("\033[33m"+"              Welcome to Uno"+"\n");
//        System.out.println("Do you want to play against Humans or against AI?");
//        System.out.println("\033[4;31m"+"1)"+"\u001b[33m"+" Humans\n"+"\033[4;31m"+"2)"+"\u001b[33m"+" AI");
//        int input=(new Scanner(System.in)).nextInt();
//        while(input!=1&&input!=2){
//            System.out.println("Please enter a valid number");
//            input=(new Scanner(System.in)).nextInt();
//        }
//        if(input==1){
//            System.out.println("\033[4;34m"+"Select the number of Players");
//            System.out.println("\033[4;31m"+"1)"+"\u001b[33m"+" 2 players\n"+"\033[4;31m"+"2)"+"\u001b[33m"+" 3 Players"+"\033[4;31m"+"3) "+"\u001b[33m"+"4 players");
//            int numberOfPlayers=(new Scanner(System.in)).nextInt();
//            while(numberOfPlayers<1||numberOfPlayers>3){
//                System.out.println("Please enter a valid number");
//                numberOfPlayers=(new Scanner(System.in)).nextInt();
//            }
//        }else{
//            System.out.println("Still working on it :)");
//        }
        Game Uno = new Game(2,1);
    }
}
