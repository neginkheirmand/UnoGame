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


        NumericCart myNumericCart1= new NumericCart(0, COLOR.BLUE);
        NumericCart myNumericCart2= new NumericCart(1, COLOR.BLUE);
        NumericCart myNumericCart3= new NumericCart(2, COLOR.BLUE);
        NumericCart myNumericCart4= new NumericCart(3, COLOR.BLUE);
        NumericCart myNumericCart5= new NumericCart(4, COLOR.BLUE);
        NumericCart myNumericCart6= new NumericCart(5, COLOR.BLUE);
        NumericCart myNumericCart7= new NumericCart(6, COLOR.BLUE);
        NumericCart myNumericCart8= new NumericCart(7, COLOR.BLUE);
        NumericCart myNumericCart9= new NumericCart(8, COLOR.BLUE);
        NumericCart myNumericCart10= new NumericCart(9, COLOR.BLUE);
        ArrayList <Cart> FisrtPlayersCarts= new ArrayList<>();
        FisrtPlayersCarts.add(myNumericCart1);
        FisrtPlayersCarts.add(myNumericCart2);
        FisrtPlayersCarts.add(myNumericCart3);
        FisrtPlayersCarts.add(myNumericCart4);
        FisrtPlayersCarts.add(myNumericCart5);
        FisrtPlayersCarts.add(myNumericCart6);
        FisrtPlayersCarts.add(myNumericCart7);
        FisrtPlayersCarts.add(myNumericCart8);
        FisrtPlayersCarts.add(myNumericCart9);
        FisrtPlayersCarts.add(myNumericCart10);

        Player first_player= new Player("FisrtPlayer", FisrtPlayersCarts );
        first_player.show(new NumericCart(0, COLOR.BLUE));
        System.out.printf("the number of first player carts are:"+ first_player.numberOfCarts());

//        Game Uno = new Game()
    }
}
