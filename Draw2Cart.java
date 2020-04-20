package ir.ac.aut;

public class Draw2Cart extends Cart {

    /**
     * the constructor method
     * @param colorOfCart the color of the Draw+2 card
     */
    public Draw2Cart(COLOR colorOfCart){
        super(20, colorOfCart);
    }


    /**
     * method returning a boolean specifying if the card can be played
     * @param lastCartPlayed the card in the center of the board
     * @return boolean specifying if the card can be played
     */
    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof Draw2Cart || lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }
}
