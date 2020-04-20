package ir.ac.aut;

public class WildCart extends Cart {

    public WildCart(){
        super(50, null);
    }

    /**
     * method returning a boolean specifying if the card can be played
     * @param lastCartPlayed the card in the center of the board
     * @return
     */
    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        //this method should be used within the method in the Player class called otherChoice()
        return true;
    }

}
