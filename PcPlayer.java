package ir.ac.aut;

import java.util.ArrayList;

public class PcPlayer extends Player{
    private static COLOR[] nextPlayersColor={
        COLOR.RED, COLOR.BLUE, COLOR.YELLOW, COLOR.GREEN
    };
    private static Cart[] nextPlayersTypeCards={
            new SkipCart(null), new ReverseCart(null), new Draw2Cart(null), new WildDrawCart(), new WildCart()
    };

    //this two arrays hold the probability of the next player to not have a certain type of card
    private int[] rightPlayeTypeCards;
    private int[] rightPlayeColorCards;
    private int[] rightPlayeNumericCards;

    //the closer these numbers are to 1 the better is to play a card with a characteristic of this type
    private int[] leftPlayeTypeCards;
    private int[] leftPlayeColorCards;
    private int[] leftPlayeNumericCards;


    public PcPlayer(String name, ArrayList<Cart> pcPlayersCards){
        super(name, pcPlayersCards);

        rightPlayeTypeCards = new int[5];
        rightPlayeColorCards = new int[4];
        leftPlayeNumericCards = new int[10];

        leftPlayeTypeCards = new int[5];
        leftPlayeColorCards = new int[4];
        leftPlayeNumericCards = new int[10];
        for(int i=0 ; i<4 ; i++){
            rightPlayeColorCards[i]=0;
            leftPlayeColorCards[i]=0;
        }
        for(int i=0; i<5; i++){
            leftPlayeTypeCards[i]=0;
            rightPlayeTypeCards[i]=0;
        }
        for(int i=0; i<10; i++){
            leftPlayeNumericCards[i]=0;
            rightPlayeNumericCards[i]=0;
        }
        //since at the start of the game we dont know anything about the players at first its holds only zero numbers
    }

    private int numWildCards(){
        int wildCarts = 0;
        for(int i=0; i<playersCarts.size(); i++){
            if( playersCarts.get(i) instanceof WildCart){
                wildCarts++;
            }
        }
        return wildCarts;
    }

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
     * this
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
                return choseWildKindedcard(clockWise, numNextPlayersCards);
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


    private int choseWildKindedcard(boolean clockWise, int numNextPlayersCards){
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

    //this method will return a number(0, 5, 10) : the probability of not having a card
    private int probabilityNextPlayer(boolean clockWise, int typeCard, int colorCard, int indexNumber){
        int answer =0;

        //the type
        if(indexNumber==-1) {
            //not a numeric card
            if (clockWise) {
                //left player is the next player
                if (leftPlayeTypeCards[typeCard] > 0) {
                    //the left player doesnt have this type of card
                    answer+=5;
                }
            } else {
                //the right player is the next player
                if (rightPlayeTypeCards[typeCard] > 0) {
                    //the right player doesnt have this type of card
                    answer+=5;
                }
            }
        }else{
            //its a numeric card
            if(clockWise){
                //left player is the next player
                if(leftPlayeNumericCards[indexNumber]>0){
                    //the left player doesnt have this type of card
                    answer+=5;
                }
            }else{
                //right player is the next player
                if(rightPlayeNumericCards[indexNumber]>0){
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
                if (leftPlayeColorCards[colorCard] > 0) {
                    answer += 5;
                }
            } else {
                //the right player is the next player
                if (rightPlayeColorCards[colorCard] > 0) {
                    answer += 5;
                }
            }
            return answer;
        }
    }


    //for after playing a wild kinded cart
    public COLOR getColorAi(boolean clockWise){
        double[] choice =new double[4];

        for(int i=0; i<4; i++){
            //if the player has that color better is for that player to chose the base color of the board that color
            choice[i]=getAiColorRepetition(COLOR.getColorByIndex(i));
            if(clockWise) {
                choice[i] +=leftPlayeColorCards[i];
            }else{
                choice[i] +=rightPlayeColorCards[i];
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

    private double getAiColorRepetition(COLOR color){
        double num = 0;
        double total = (double) playersCarts.size();
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i).getColor().equals(color)){
                num++;
            }
        }
        return (num/total);
    }

}
