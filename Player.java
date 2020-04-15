package ir.ac.aut;

import sun.plugin2.gluegen.runtime.CPU;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private final String namePlayer;
    private int playersPoint;
    //when adding a new cart YOU SHOULD CHECK IF HASNT ALREADY BEEN ADDED
    private ArrayList<Cart> playersCarts;

    public Player(String name, ArrayList<Cart> playersCarts){
        this.namePlayer=name;
        //we are sure that the Array List given to this player is an array of 5 valid carts
        this.playersCarts=playersCarts;
    }

    public void addPoint(int ponitsToAdd){
        playersPoint+=ponitsToAdd;
        return;
    }

    public void removeCart(Cart beRemovedCart){
        for(int i=0; i<playersCarts.size(); i++){
            if(beRemovedCart.equals(playersCarts.get(i))){
                playersCarts.remove(i);

                return;
            }
        }
        //BADAN IN RO PAK KON, FAGHAT BARAYE MOTMAEN SHODN
        System.out.println("MOSHKEL TUYE FUNCTION RemoveCart az class Player, darE sai mikoni CartE ro remove koni ke tuye list cart haye in player nist");
    }

    public int getPlayersPoint(){
        playersPoint=0;
        for(int i=0; i<playersCarts.size(); i++){
            playersPoint+=playersCarts.get(i).getPoint();
        }
        return playersPoint;
    }

    public Cart playCart(Cart lastCartPlayed){
        int chosenCart=chose(lastCartPlayed);
        if(chosenCart==-1){
            //no carts available to play
            return null;
        }else{
            return playersCarts.get(chosenCart);
        }
    }

    private int numPlayableCarts(Cart lastPlayedCart){
        int canPlayNum=0;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i).canPlayCart(lastPlayedCart)){
                canPlayNum++;
            }
        }
        return canPlayNum;
    }

    private void printCarts(Cart lastPlayedCart){

        int numHandCarts = playersCarts.size();
        int numberOfRepetition = 0;
        int nullPrinted = 0;
        while (numHandCarts > 0) {
            //there will be printing 7 carts in each  segment of 10 rows

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (numHandCarts > 0 && numHandCarts < 7) {
                        //last segments
                        if ((7 - numHandCarts) % 2 == 0) {
                            //symmetric
                            if (j >= (7 - numHandCarts) / 2 && j < 7 - ((7 - numHandCarts) / 2)) {
                                playersCarts.get((j + numberOfRepetition * 7) - (7 - numHandCarts) / 2).printLineOfCart(i);
                            } else if (j < (7 - numHandCarts) / 2) {
                                playersCarts.get(0).printLineOfCart(-1);
                                nullPrinted++;
                            } else {
                                break;
                            }
                        } else {
                            //non-symmetric
                            if (j >= 1 + (7 - numHandCarts) / 2 && j < 7 - ((7 - numHandCarts) / 2)) {
                                playersCarts.get((j + numberOfRepetition * 7) - ((7 - numHandCarts) / 2) - 1).printLineOfCart(i);
                            } else if (j < 1 + ((7 - numHandCarts) / 2)) {
                                playersCarts.get(0).printLineOfCart(-1);
                                nullPrinted++;
                            } else {
                                break;
                            }

                        }
                    } else {
                        playersCarts.get(j + numberOfRepetition * 7).printLineOfCart(i);
                    }
                }
                System.out.println();
            }
            if (numHandCarts >= 7) {
                for (int k = 0; k < 7; k++) {
                    if ((k + 1 + (numberOfRepetition * 7)) < 10) {
                        if(playersCarts.get((k + 1 + (numberOfRepetition * 7))-1).canPlayCart(lastPlayedCart)){
                            System.out.printf("\033[0;35m");
                        }else{
                            System.out.printf("\033[0m");
                        }
                        System.out.printf("     " + (k + 1 + (numberOfRepetition * 7)) + "     ");
                    } else {
                        if(playersCarts.get(k + 1 + (numberOfRepetition * 7)).canPlayCart(lastPlayedCart)){
                            System.out.printf("\033[0;35m");
                        }else{
                            System.out.printf("\033[0m");
                        }
                        System.out.printf("    " + (k + 1 + (numberOfRepetition * 7)) + "     ");

                    }
                }
                System.out.println();
            } else {
                //was the last line
                //since nullPrinted was added 7 times (7 rows) per each adelanted cart, definitly is a mutiplier of 7
                for (int k = 0; k < (nullPrinted / 7); k++) {
                    System.out.printf("           ");
                }

                for (int k = 0; k < numHandCarts; k++) {
                    if ((k + 1 + (numberOfRepetition * 7)) < 10) {
                        System.out.printf("     " + (k + 1 + (numberOfRepetition * 7)) + "     ");
                    } else {
                        System.out.printf("    " + (k + 1 + (numberOfRepetition * 7)) + "     ");

                    }
                }
            }
            System.out.println();
            numHandCarts -= 7;
            numberOfRepetition++;
        }
    }

    private int chose(Cart lastPlayedCart) {//the else works perfectly
        //we have to make sure that this player has at least one cart in his/her hands
        int canPlayNum = numPlayableCarts(lastPlayedCart);
        System.out.println();
        if (canPlayNum == 0) {
            //cannot play
            System.out.println("you cannot choose any of your card's\nYour carts are:");
            printCarts(lastPlayedCart);
            return -1;
        }else{
            System.out.println("\033[0m"+"Chose one of your carts to play (the ones available for this round are in Magneta color)");
            int input;
            printCarts(lastPlayedCart);
            System.out.println("\033[0m"+"please choose the cart you want to play:");
            input = (new Scanner(System.in)).nextInt();
            input-=1;
            while(input>=playersCarts.size()||input<0 || !playersCarts.get(input).canPlayCart(lastPlayedCart)){
                System.out.println("\033[0m"+"Please enter a valid number, you have to chose between the purple numbers"+"\033[0;35m");
                for(int i=0; i<playersCarts.size(); i++){
                    if(playersCarts.get(i).canPlayCart(lastPlayedCart)) {
                        System.out.printf("%d ", i+1);
                    }
                }
                input = (new Scanner(System.in)).nextInt();
                input-=1;
            }
            return input;
        }
    }

    public int numberOfCarts(){
        return playersCarts.size();
    }

    public String getNamePlayer(){
        return namePlayer;
    }
}
