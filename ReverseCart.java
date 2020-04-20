package ir.ac.aut;

public class ReverseCart extends Cart {


    /**
     * the constructor method
     * @param colorOfCart the color of the skip card
     */
    public ReverseCart(COLOR colorOfCart){
        super(20, colorOfCart);
    }


    /**
     * method returning a boolean specifying if the card can be played
     * @param lastCartPlayed the card in the center of the board
     * @return boolean specifying if the card can be played
     */
    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof ReverseCart || lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }

}
