package ir.ac.aut;

public class WildDrawCart extends Cart {

    public WildDrawCart(COLOR colorOfCart){
        super(50, colorOfCart);
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof WildDrawCart|| lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }



}
