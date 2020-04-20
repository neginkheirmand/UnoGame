package ir.ac.aut;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    //the name of the player
    protected final String namePlayer;

    //the array list of the cards of the player
    protected ArrayList<Cart> playersCarts;

    /**
     * the constructor of the class
     * @param name the name of the player
     * @param playersCarts the array list of players cards
     */
    public Player(String name, ArrayList<Cart> playersCarts){
        this.namePlayer=name;
        //we are sure that the Array List given to this player is an array of 5 valid carts
        this.playersCarts=playersCarts;
    }

    /**
     * method to add new card
     * @param newCart
     */
    public void addCart(Cart newCart){
        playersCarts.add(newCart);
        return;
    }

    /**
     * getter method (size of the arraylist of cards)
     * @return size of the arraylist of cards
     */
    public int getNumberCartsLeft(){
        return playersCarts.size();
    }

    /**
     * getter method (point of the player)
     * @return the sum of the point of each card in players hand
     */
    public int getPlayersPoint(){
        int playersPoint=0;
        for(int i=0; i<playersCarts.size(); i++){
            playersPoint+=playersCarts.get(i).getPoint();
        }
        return playersPoint;
    }

    /**
     * method to be called when is this players turn to play
     * @param lastCartPlayed the last card played
     * @return this players Card to be played
     */
    public Cart playCart(Cart lastCartPlayed){
        int chosenCart=chose(lastCartPlayed);
        if(chosenCart==-1){
            //no carts available to play
            return null;
        }else{
            Cart playedCart = playersCarts.get(chosenCart);
            playersCarts.remove(chosenCart);
            return playedCart;
        }
    }

    /**
     * method to be called when is this players turn to play a Draw +2 card
     * @return the choosen Draw+2 card
     */
    public Cart playDraw2Cart(){
        //this method is a defense method for the player to defend itself against a draw+2 cart
        //and its called only if we are sure that there actually exists a draw+2 cart in his hand, but anyway
        if(numDraw2Carts() == 0){
            return null;
        }
        System.out.println("\nyou can answer with a Draw+2 cart, which one do you want to play:");
        int numDraw2Cart = chooseDraw2Cart();
        Cart temp = getDraw2CartByNum(numDraw2Cart);
        for (int i=0; i<playersCarts.size(); i++){
            if(temp == playersCarts.get(i)){
                //since temp is alse a reference to one the carts arraylist we can use the ( == ) operation
                playersCarts.remove(i);
            }
        }
        return temp;
    }

    /**
     * method to be called when is this players turn to play a wild draw card
     * @return the choosen wild Draw card
     */
    public Cart playWildDrawCart(){
        //this method is a defense method for the player to defend itself against a draw+4 cart
        //and its called only if we are sure that there actually exists a WildDraw+4 cart in his hand, but anyway
        if(numWildDrawCarts()==0){
            return null;
        }
        int numWildDrawCart = chooseWildDrawCart();
        Cart temp = getWildDrawCartByNum(numWildDrawCart);
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) == temp){
                playersCarts.remove(i);
            }
        }
        return temp;
    }

    /**
     * get method for wild draw cards in players hands by its number
     * @param number number of the wild Draw card in players hands
     * @return the card with the specified number
     */
    private Cart getWildDrawCartByNum(int number){
        for(int i=0, num=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof WildDrawCart){
                num++;
                if(number == num){
                    return playersCarts.get(i);
                }
            }
        }
        return  null;
    }

    /**
     * get method for draw cards in players hands by its number
     * @param number number of the Draw card in players hands
     * @return the card with the specified number
     */
    private Cart getDraw2CartByNum(int number){
        for(int i=0, num=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof Draw2Cart){
                num++;
                if(number == num){
                    return playersCarts.get(i);
                }
            }
        }
        return  null;
    }

    /**
     * a method for the player to choose a wild draw card
     * @return the index of the chosen wild draw card
     */
    private int chooseWildDrawCart(){
        //we are sure that this player has Wild Draw +4 carts in his/her hand
        int numberOfWildDrawCarts = numWildDrawCarts();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < numberOfWildDrawCarts; j++) {
                getWildDrawCartByNum(j + 1).printLineOfCart(i);
            }
            System.out.println();
        }
        System.out.println("\033[0;35m");
        //since we will be able to use the Wild Draw +4 cart
        for (int i = 0; i < numberOfWildDrawCarts; i++) {
            System.out.printf("     " + (i+1) + "     ");
        }

        int choice = (new Scanner(System.in). nextInt());
        while(choice<1||choice>numberOfWildDrawCarts){
            System.out.printf("Please enter a valid number");
            choice = (new Scanner(System.in). nextInt());
        }
        return choice;

    }

    /**
     * a method for the player to choose a draw card
     * @return the index of the chosen draw card
     */
    private int chooseDraw2Cart() {
        //we are sure that this player has Draw +2 carts in his/her hand
        int numberOfDraw2Carts = numDraw2Carts();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < numberOfDraw2Carts; j++) {
                getDraw2CartByNum(j + 1).printLineOfCart(i);
            }
            System.out.println();
        }
        System.out.println("\033[0;35m");
        //since we will be able to use the Draw+2 cart
        for (int i = 0; i < numberOfDraw2Carts; i++) {
            System.out.printf("     " + (i+1) + "     ");
        }
        System.out.println();
        int choice = (new Scanner(System.in). nextInt());
        while(choice<1||choice>numberOfDraw2Carts){
            System.out.printf("Please enter a valid number");
            choice = (new Scanner(System.in). nextInt());
        }
        return choice;
    }

    /**
     * this method returns the number of playable cards (not counting the wild and wild draw ones)
     * @param lastPlayedCart the last played card in the game
     * @return the number of playable cards
     */
    public int numPlayableNormalCarts(Cart lastPlayedCart){
        //this method is public because it will be ussed in the Game Class for the miniRun method
        int canPlayNum = 0;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof WildDrawCart || playersCarts.get(i) instanceof WildCart){
                continue;
            }
            else if(playersCarts.get(i).canPlayCart(lastPlayedCart)){
                canPlayNum++;
            }
        }
        return canPlayNum;
    }

    /**
     * this method returns the number of draw+2 cards
     * @return the number of Draw+2 cards
     */
    public int numDraw2Carts(){
        int num =0;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof Draw2Cart){
                num++;
            }
        }
        return num;
    }

    /**
     * this method returns the number of Wild cards
     * @return the number of wild cards
     */
    private int numWildKindedCarts(){
        int wildCarts = 0;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof WildDrawCart || playersCarts.get(i) instanceof WildCart){
                wildCarts++;
            }
        }
        return wildCarts;
    }

    /**
     * this method returns the number of wild draw cards
     * @return the number of wild Draw+4 cards
     */
    public int numWildDrawCarts(){
        int num=0;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof WildDrawCart) {
                num++;
            }
        }
        return num;
    }

    /**
     * method for printing the carts of the player
     * @param lastPlayedCart the last played card in the center of the game board
     */
    public void printCarts(Cart lastPlayedCart){

        int numHandCarts = playersCarts.size();
        int numberOfRepetition = 0;
        int nullPrinted = 0;
        while (numHandCarts > 0) {
            //there will be printing 7 carts in each  segment of 7+1 rows
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
                    if(playersCarts.get(k + (numberOfRepetition * 7)) instanceof WildCart || playersCarts.get(k + (numberOfRepetition * 7)) instanceof WildDrawCart){
                        if(numPlayableNormalCarts(lastPlayedCart)==0){
                            System.out.printf("\033[0;35m");
                        }else{
                            System.out.printf("\033[0m");
                        }
                    }else if(playersCarts.get(k + (numberOfRepetition * 7)).canPlayCart(lastPlayedCart)){
                        System.out.printf("\033[0;35m");
                    }else{
                        System.out.printf("\033[0m");
                    }
                    //the color of this next printed number was choosen in the last if and else statements
                    if ((k + 1 + (numberOfRepetition * 7)) < 10) {
                        System.out.printf("     " + (k + 1 + (numberOfRepetition * 7)) + "     ");
                    } else {
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
                    if(playersCarts.get(k  + (numberOfRepetition * 7)) instanceof WildCart || playersCarts.get(k  + (numberOfRepetition * 7)) instanceof WildDrawCart){
                        if(numPlayableNormalCarts(lastPlayedCart)==0){
                            System.out.printf("\033[0;35m");
                        }else{
                            System.out.printf("\033[0m");
                        }
                    }else if(playersCarts.get(k  + (numberOfRepetition * 7)).canPlayCart(lastPlayedCart)){
                        System.out.printf("\033[0;35m");
                    }else{
                        System.out.printf("\033[0m");
                    }
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
        System.out.printf("\033[0m");
        if(playersCarts.size()==1){
            System.out.println("UNO!");
        }
        return;
    }

    /**
     * this method is called when is this players turn to play
     * @param lastPlayedCart the last played card in the center of the game
     * @return the index of the chosen card in the array list of this players card
     */
    private int chose(Cart lastPlayedCart) {
        //we have to make sure that this player has at least one cart in his/her hands
        int canPlayNum = numPlayableNormalCarts(lastPlayedCart);
        if(canPlayNum == 0){
            canPlayNum = numWildKindedCarts();
        }
        System.out.println();
        if (canPlayNum == 0) {
            //cannot play
            System.out.println("you cannot choose any of your cart's\nYour carts are:");
            printCarts(lastPlayedCart);
            return -1;
        }else{
            System.out.println("\033[0m"+"Chose one of your carts (the ones available for this round are in purple)");
            int input;
            printCarts(lastPlayedCart);
            System.out.println("\033[0m"+"please choose the cart");
            input = (new Scanner(System.in)).nextInt();
            input-=1;
            int[] container= new int[playersCarts.size()];
            int exp=0;
            for(int i=0 ; i<playersCarts.size(); i++){
                if((playersCarts.get(i) instanceof WildDrawCart || playersCarts.get(i) instanceof WildCart) ){
                    if(numPlayableNormalCarts(lastPlayedCart)==0) {
                        container[exp]=i+1;
                        exp++;
//                        System.out.printf("\033[1;95m" + "%d ", i + 1);
                    }
                }else if(playersCarts.get(i).canPlayCart(lastPlayedCart)) {
//                    System.out.printf("\033[1;94m"+"%d ", i+1);
                    container[exp]=i+1;
                    exp++;
                }
            }
            while(input>=playersCarts.size()||input<0 || !existsInArr(container, exp, input+1)){
                System.out.println("\033[0m"+"Please enter a valid number, you have to chose between the purple numbers"+"\033[0;35m");
                for(int i=0; i<exp; i++){
                    System.out.printf("\033[1;35m"+"%d ", container[i]);
                }
                System.out.println( "\033[0m");
                input = (new Scanner(System.in)).nextInt();
                input-=1;
            }
            return input;
        }
    }

    /**
     * method for searching in a given array -with the given size- and find out if a passed parameter exist in that array
     * @param container the array
     * @param size the size of the array
     * @param input the specified parameter
     * @return boolean containing the answer
     */
    private boolean existsInArr(int [] container, int size, int input){
        for(int i=0; i<size; i++){
            if(container[i]==input){
                return true;
            }
        }
        return false;
    }

    /**
     * @return the size of the array list containig all the cards of the player
     */
    public int numberOfCarts(){
        return playersCarts.size();
    }

    /**
     * getter method for the name field of this class
     * @return the name of the player
     */
    public String getNamePlayer(){
        return namePlayer;
    }

}
