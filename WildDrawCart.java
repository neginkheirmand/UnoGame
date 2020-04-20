package ir.ac.aut;

public class WildDrawCart extends Cart {

    public WildDrawCart(){
        super(50, null);
    }

    /**
     * method returning a boolean specifying if the Wild Draw+4 card can be played
     * @param lastCartPlayed the card in the center of the board
     * @return
     */
    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        //this method should be used within the method in the Player class called otherChoice()
        return true;
    }



}
