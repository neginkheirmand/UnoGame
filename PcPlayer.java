package ir.ac.aut;

import java.util.ArrayList;

public class PcPlayer extends Player{
    //the Color array which contains the index of every color
    private static COLOR[] nextPlayersColor={
        COLOR.RED, COLOR.BLUE, COLOR.YELLOW, COLOR.GREEN
    };
    /*
    Type of cards:
            -1)NumericCards
            0)SkipCards
            1)ReverseCards
            2)Draw2Cards
            3)WildDrawCards
            4)WildCards
    */

    //if a house of this array is 1 means that the right player of this PCPlayer doesn't have the specified Type of card
    private int[] rightPlayerTypeCards;
    //if a house of this array is 1 means that the right player of this PCPlayer doesn't have the specified Color of card
    private int[] rightPlayerColorCards;
    //if a house of this array is 1 means that the right player of this PCPlayer doesn't have the specified number of card (between numeric cards
    private int[] rightPlayerNumericCards;

    //if a house of this array is 1 means that the left player of this PCPlayer doesn't have the specified  Type of card
    private int[] leftPlayerTypeCards;
    //if a house of this array is 1 means that the left player of this PCPlayer doesn't have the specified  Color of card
    private int[] leftPlayerColorCards;
    //if a house of this array is 1 means that the left player of this PCPlayer doesn't have the specified number of card (between numeric cards
    private int[] leftPlayerNumericCards;

    /**
     * the consturctor of this class
     * @param name the name of the player
     * @param pcPlayersCards the array list containing the cards of this PCPlayer
     */
    public PcPlayer(String name, ArrayList<Cart> pcPlayersCards){
        super(name, pcPlayersCards);

        rightPlayerTypeCards = new int[5];
        rightPlayerColorCards = new int[4];
        rightPlayerNumericCards = new int[10];

        leftPlayerTypeCards = new int[5];
        leftPlayerColorCards = new int[4];
        leftPlayerNumericCards = new int[10];
        for(int i=0 ; i<4 ; i++){
            rightPlayerColorCards[i]=0;
            leftPlayerColorCards[i]=0;
        }
        for(int i=0; i<5; i++){
            leftPlayerTypeCards[i]=0;
            rightPlayerTypeCards[i]=0;
        }
        for(int i=0; i<10; i++){
            leftPlayerNumericCards[i]=0;
            rightPlayerNumericCards[i]=0;
        }
        //since at the start of the game we dont know anything about the players at first its holds only zero numbers
    }

    /**
     * a function that returns the number of wild Cards
     * @return
     */
    private int numWildCards(){
        int wildCarts = 0;
        for(int i=0; i<playersCarts.size(); i++){
            if( playersCarts.get(i) instanceof WildCart){
                wildCarts++;
            }
        }
        return wildCarts;
    }

    /**
     * a method to get the number of Wild Draw crads
     * @return number of wild draw cards
     */
    private int numWildDrawCards(){
        int wildCarts = 0;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof WildDrawCart ){
                wildCarts++;
            }
        }
        return wildCarts;
    }

    /**
     * this method should choose a card between its card and return it after removing it
     * @param lastCartPlayed the last played cart in the game
     * @param clockWise the type of rotation in the game
     * @return the Cart choosen
     */
    public Cart aiPlayCart(Cart lastCartPlayed, boolean clockWise, int numNextPlayersCards){
        System.out.println("the "+namePlayer+" carts:");
        printCarts(lastCartPlayed);
        System.out.println("info ai:");
        int chosenCart=aiChose(lastCartPlayed, clockWise, numNextPlayersCards);
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
     * this is the ai method which using the information plays a draw+2 card
     * @return
     */
    @Override
    public Cart playDraw2Cart(){
        //this method is a defense method for the player to defend itself against a draw+2 cart
        //and its called only if we are sure that there actually exists a draw+2 cart in his hand, but anyway
        if(numDraw2Carts() == 0){
            return null;
        }
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof Draw2Cart){
                Cart thisCard = playersCarts.get(i);
                playersCarts.remove(i);
                return thisCard;
            }
        }
        return null;
    }

    /**
     * this method is for this player to play with specifically WildDraw cards
     * @return
     */
    @Override
    public Cart playWildDrawCart(){
        //this method is a defense method for the player to defend itself against a draw+2 cart
        //and its called only if we are sure that there actually exists a WildDraw+4 cart in his hand, but anyway
        if(numWildDrawCarts()==0){
            return null;
        }
        Cart temp;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof WildDrawCart){
                temp = playersCarts.get(i);
                playersCarts.remove(i);
                return temp;
            }
        }
        return null;
    }


    /**
     * this is the main ai method for choosing the next card
     * @param lastPlayedCart
     * @param clockWise
     * @return
     */
    private int aiChose(Cart lastPlayedCart, boolean clockWise, int numNextPlayersCards) {
        int canPlayNum = numPlayableNormalCarts(lastPlayedCart);
        if (canPlayNum == 0) {
            canPlayNum = numWildCards() + numWildDrawCards();
            if (canPlayNum == 0) {
                System.out.println("this (bot) player can't play");
                return -1;
            } else {
                //has to play a wild kinded cart
                return choseWildKindCard( numNextPlayersCards);
            }
        }
        System.out.println();
        int[] container = new int[playersCarts.size()];
        int exp = 0;
        for (int i = 0; i < playersCarts.size(); i++) {
            if ((playersCarts.get(i) instanceof WildDrawCart || playersCarts.get(i) instanceof WildCart)) {
                continue;
            } else if (playersCarts.get(i).canPlayCart(lastPlayedCart)) {
                container[exp] = i;
                //holds the index of the available cards
                exp++;
            }
        }
        //now we have an array of the cards(index) we are able to play with the size of exp
        int[] points= new int[exp];
        for(int i=0; i<exp; i++){
            points[i]=playersCarts.get(container[i]).getPoint();
            if(playersCarts.get(container[i]) instanceof Draw2Cart){
                if(numNextPlayersCards<4){
                    points[i]+=10;
                }
                points[i]+=probabilityNextPlayer(clockWise, 2, playersCarts.get(container[i]).getColor().ordinal(), -1);
            }else if(playersCarts.get(container[i]) instanceof NumericCart){
                points[i]+=probabilityNextPlayer(clockWise, -1, playersCarts.get(container[i]).getColor().ordinal(), ((NumericCart) playersCarts.get(container[i])).getNumber());
            }else if(playersCarts.get(container[i]) instanceof ReverseCart){
                if(numNextPlayersCards<3){
                    points[i]+=10;
                }
                points[i]+=probabilityNextPlayer(clockWise, 1, playersCarts.get(container[i]).getColor().ordinal(),-1);
            }else if(playersCarts.get(container[i]) instanceof SkipCart){
                if(numNextPlayersCards<3){
                    points[i]+=10;
                }
                points[i]+=probabilityNextPlayer(clockWise, 0, playersCarts.get(container[i]).getColor().ordinal(),-1);
            }
        }
        int maxPoint=-1;
        int indexMaxPoint=-1;
        for(int i=0; i<exp; i++){
            if(maxPoint<points[i]){
                maxPoint=points[i];
                indexMaxPoint=i;
            }
        }
        return container[indexMaxPoint];
    }

    /**
     *  a method to choose a wild kind card if available in case you cannot play any other card of your hand
     * @param numNextPlayersCards the number of the next players cards
     * @return
     */
    private int choseWildKindCard(int numNextPlayersCards){
        //this method should choose the index of the wild kinded card choosen card
        if(numWildDrawCards()==0){
            //wild cards
            for(int i=0; i<playersCarts.size(); i++){
                if(playersCarts.get(i) instanceof WildCart){
                    return i;
                }
            }
        }else if(numWildCards()==0){
            // Wild Draw cards
            for(int i=0; i<playersCarts.size(); i++){
                if(playersCarts.get(i) instanceof WildDrawCart){
                    return i;
                }
            }
        }else {
            //can play both
            if (numNextPlayersCards < 7 ){
                for (int i = 0; i < playersCarts.size(); i++) {
                    if (playersCarts.get(i) instanceof WildDrawCart) {
                        return i;
                    }
                }
            }
            for (int i = 0; i < playersCarts.size(); i++) {
                if (playersCarts.get(i) instanceof WildCart) {
                    return i;
                }
            }

        }
        //will never come this far because we are sure we have Wild-kinded carts in our hand
        return -1;
    }

    /**
     * the probability of the next player to not have a card(Type, color, number-if numeric)
     * @param clockWise kind of rotation in the game
     * @param typeCard the type of card
     * @param colorCard the color of the card played
     * @param indexNumber the number -in case its a numeric card-
     * @return this method will return a number(0, 5, 10) : the probability of not having a card
     */
    private int probabilityNextPlayer(boolean clockWise, int typeCard, int colorCard, int indexNumber){
        int answer =0;

        //the type
        if(indexNumber==-1) {
            //not a numeric card
            if (clockWise) {
                //left player is the next player
                if (leftPlayerTypeCards[typeCard] > 0) {
                    //the left player doesnt have this type of card
                    answer+=5;
                }
            } else {
                //the right player is the next player
                if (rightPlayerTypeCards[typeCard] > 0) {
                    //the right player doesnt have this type of card
                    answer+=5;
                }
            }
        }else{
            //its a numeric card
            if(clockWise){
                //left player is the next player
                if(leftPlayerNumericCards[indexNumber]>0){
                    //the left player doesnt have this type of card
                    answer+=5;
                }
            }else{
                //right player is the next player
                if(rightPlayerNumericCards[indexNumber]>0){
                    //the left player doesnt have this type of card
                    answer+=5;
                }
            }
        }
        if(colorCard==-1){
            //was a wild kinded card
            if(answer==5){
                return 10;
            }
            return 0;
        }else {
            //the color
            if (clockWise) {
                //the left player is the next player
                if (leftPlayerColorCards[colorCard] > 0) {
                    answer += 5;
                }
            } else {
                //the right player is the next player
                if (rightPlayerColorCards[colorCard] > 0) {
                    answer += 5;
                }
            }
            return answer;
        }
    }

    /**
     * a method for updating the information about the right player
     * @param lastCard the last played card
     * @param lastPlayed a boolean to specify if the last player played a card
     * @param hasWild a boolean to specify if the player has wild kind card
     */
    public void updateInfoRight(Cart lastCard, boolean lastPlayed, boolean hasWild){
        //when a player doesn't have anything to play after a cart means that player doesn't have that Type of card and that color and doesn't have Wild kind cards either
        if(lastCard instanceof Draw2Cart){
            //doesnt have that type of card
            rightPlayerTypeCards[2]=1;
            if(!hasWild) {
                //doesnt have any wild kind card
                rightPlayerTypeCards[3] = 1;
                rightPlayerTypeCards[4] = 1;
            }
            rightPlayerColorCards[lastCard.getColor().ordinal()]=1;
        }else if(lastCard instanceof NumericCart){
            rightPlayerNumericCards[((NumericCart) lastCard).getNumber()]=1;
            if(!hasWild) {
                //doesnt have any wild kind card
                rightPlayerTypeCards[3] = 1;
                rightPlayerTypeCards[4] = 1;
            }
            rightPlayerColorCards[lastCard.getColor().ordinal()]=1;
        }else if(lastCard instanceof ReverseCart){
            rightPlayerTypeCards[1]=1;
            if(!hasWild) {
                //doesnt have any wild kind card
                rightPlayerTypeCards[3] = 1;
                rightPlayerTypeCards[4] = 1;
            }
            rightPlayerColorCards[lastCard.getColor().ordinal()]=1;
        }else if(lastCard instanceof SkipCart){
            //the second player after the one who played the skip card
            rightPlayerTypeCards[0]=1;
            if(!hasWild) {
                //doesnt have any wild kind card
                rightPlayerTypeCards[3] = 1;
                rightPlayerTypeCards[4] = 1;
            }
            rightPlayerColorCards[lastCard.getColor().ordinal()]=1;
        }else if(lastCard instanceof WildCart){
            if(!hasWild) {
                //doesnt have any wild kind card
                rightPlayerTypeCards[3] = 1;
                rightPlayerTypeCards[4] = 1;
            }
            rightPlayerColorCards[Game.getBaseColor().ordinal()]=1;
        }else if(lastCard instanceof WildDrawCart) {
            if(!hasWild) {
                //doesnt have any wild kind card
                rightPlayerTypeCards[3] = 1;
                rightPlayerTypeCards[4] = 1;
            }
            //the next statement is only for the second player after the one who played
            if (!lastPlayed){
                rightPlayerColorCards[Game.getBaseColor().ordinal()] = 1;
            }
        }
        return;
    }

    /**
     * a method for updating the information about the left player
     * @param lastCard the last played card
     * @param lastPlayed a boolean to specify if the last player played a card
     * @param hasWild a boolean to specify if the player has wild kind card
     */
    public void updateInfoLeft(Cart lastCard, boolean lastPlayed, boolean hasWild){
        //when a player doesn't have anything to play after a cart means that player doesn't have that Type of card and that color and doesn't have Wild kind cards either
        if(lastCard instanceof Draw2Cart){
            //doesnt have that type of card
            leftPlayerTypeCards[2]=1;
            if(hasWild==false) {
                //doesnt have any wild kind card
                leftPlayerTypeCards[3] = 1;
                leftPlayerTypeCards[4] = 1;
            }
            leftPlayerColorCards[lastCard.getColor().ordinal()]=1;
        }else if(lastCard instanceof NumericCart){
            leftPlayerNumericCards[((NumericCart) lastCard).getNumber()]=1;
            if(hasWild==false) {
                //doesnt have any wild kind card
                leftPlayerTypeCards[3] = 1;
                leftPlayerTypeCards[4] = 1;
            }
            leftPlayerColorCards[lastCard.getColor().ordinal()]=1;
        }else if(lastCard instanceof ReverseCart){
            leftPlayerTypeCards[1]=1;
            if(hasWild==false) {
                //doesnt have any wild kind card
                leftPlayerTypeCards[3] = 1;
                leftPlayerTypeCards[4] = 1;
            }
            leftPlayerColorCards[lastCard.getColor().ordinal()]=1;
        }else if(lastCard instanceof SkipCart){
            //the second player after the one who played the skip card
            leftPlayerTypeCards[0]=1;
            if(hasWild==false) {
                //doesnt have any wild kind card
                leftPlayerTypeCards[3] = 1;
                leftPlayerTypeCards[4] = 1;
            }
            leftPlayerColorCards[lastCard.getColor().ordinal()]=1;
        }else if(lastCard instanceof WildCart){
            if(hasWild==false) {
                //doesnt have any wild kind card
                leftPlayerTypeCards[3] = 1;
                leftPlayerTypeCards[4] = 1;
            }
            leftPlayerColorCards[Game.getBaseColor().ordinal()]=1;
        }else if(lastCard instanceof WildDrawCart) {
            if(hasWild==false) {
                //doesnt have any wild kind card
                leftPlayerTypeCards[3] = 1;
                leftPlayerTypeCards[4] = 1;
            }
            //the next statement is only for the second player after the one who played
            if (!lastPlayed){
                leftPlayerColorCards[Game.getBaseColor().ordinal()] = 1;
            }
        }
        return;
    }

    /**
     * a method for deleting all the info about a player because the specified player has new cards in his hands
     * for the right player
     */
    public void addedCardToRightPlayer(){
        for(int i=0; i<4; i++){
            rightPlayerColorCards[i]=0;
        }
        for(int i=0; i<10; i++){
            rightPlayerNumericCards[i]=0;
        }
        for(int i=0; i<5; i++){
            rightPlayerTypeCards[i]=0;
        }
    }

    /**
     * a method for deleting all the info about a player because the specified player has new cards in his hands
     * for the left player
     */
    public void addedCardToLeftPlayer(){
        for(int i=0; i<4; i++){
            leftPlayerColorCards[i]=0;
        }
        for(int i=0; i<10; i++){
            leftPlayerNumericCards[i]=0;
        }
        for(int i=0; i<5; i++){
            leftPlayerTypeCards[i]=0;
        }
    }

    /**
     * a method to choose the color of the board after playing a wild card
     * @param clockWise the kind of rotation in the game
     * @return the color chosen
     */
    public COLOR getColorAi(boolean clockWise){
        double[] choice =new double[4];

        for(int i=0; i<4; i++){
            //if the player has that color better is for that player to chose the base color of the board that color
            choice[i]=getAiColorRepetition(COLOR.getColorByIndex(i));
            if(clockWise) {
                choice[i] +=leftPlayerColorCards[i];
            }else{
                choice[i] +=rightPlayerColorCards[i];
            }
        }
        double max=-1;
        COLOR color= null;
        for(int i=0; i<4; i++){
            if(max<choice[i]){
                color=nextPlayersColor[i];
                max=choice[i];
            }
        }
        return color;
    }

    /**
     * get the number of repetition of each color in probability
     * @param color the color
     * @return the probability in double
     */
    private double getAiColorRepetition(COLOR color){
        double num = 0;
        double total = (double) playersCarts.size();
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i) instanceof WildDrawCart || playersCarts.get(i) instanceof WildCart){
                continue;
            }
            if(playersCarts.get(i).getColor().equals(color)){
                num++;
            }
        }
        return (num/total);
    }

}
