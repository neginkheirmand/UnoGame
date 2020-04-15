package ir.ac.aut;

public class WildDrawCart extends Cart {

    public WildDrawCart(){
        super(50, null);
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        //this method should be used within the method in the Player class called otherChoice()
        return true;
    }



}
