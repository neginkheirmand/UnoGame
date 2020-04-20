package ir.ac.aut;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("\033[33m"+"              Welcome to Uno"+"\n");
        System.out.println("Do you want to play against Humans or against AI?");
        System.out.println("\033[4;31m"+"1)"+"\u001b[33m"+" Humans\n"+"\033[4;31m"+"2)"+"\u001b[33m"+" AI");
        int input=(new Scanner(System.in)).nextInt();
        while(input!=1&&input!=2){
            System.out.println("Please enter a valid number");
            input=(new Scanner(System.in)).nextInt();
        }
        if(input==1){
            System.out.println("\033[4;34m"+"Select the number of Players");
            System.out.println("\033[4;31m"+"1)"+"\u001b[33m"+" 2 players\n"+"\033[4;31m"+"2)"+"\u001b[33m"+" 3 Players\n"+"\033[4;31m"+"3) "+"\u001b[33m"+"4 players");
            System.out.println("\033[4;31m"+"4)"+"\u001b[33m"+" other number of players"+"\033[0m");
            int numberOfPlayers=(new Scanner(System.in)).nextInt();
            while(numberOfPlayers<1||numberOfPlayers>4){
                System.out.println("Please enter a valid number");
                numberOfPlayers=(new Scanner(System.in)).nextInt();
            }
            if(numberOfPlayers==4){
                System.out.println("Enter the number of player you want");
                numberOfPlayers=(new Scanner(System.in)).nextInt();
                while(numberOfPlayers<5||numberOfPlayers>10){
                    System.out.println("Please enter a valid number");
                    numberOfPlayers=(new Scanner(System.in)).nextInt();
                }
                Game Uno = new Game(numberOfPlayers,0);
            }else{
                Game Uno = new Game(numberOfPlayers+1,0);
            }
        }else{
            int numBots=0;
            int numHumanPlayers=0;
            while (true){
                System.out.println("\033[1;31m"+"we cant have more than 10 players in a uno game"+"\033[0m");
                System.out.println("Enter the number of bots you want in your game");
                numBots=(new Scanner(System.in)).nextInt();
                if(numBots<1||numBots>9){
                    continue;
                }
                System.out.println("Enter the number of human players in this game");
                numHumanPlayers=(new Scanner(System.in)).nextInt();
                if(numBots+numHumanPlayers>10||numHumanPlayers>9||numHumanPlayers<1){
                    continue;
                }
                Game Uno = new Game(numHumanPlayers,numBots);
                break;
            }
        }
    }
}
