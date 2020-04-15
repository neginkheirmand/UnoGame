package ir.ac.aut;

public class WildCart extends Cart {

    public WildCart(){
        super(50, null);
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        //this method should be used within the method in the Player class called otherChoice()
        return true;
    }

}
