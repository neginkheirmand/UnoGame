package ir.ac.aut;

public class WildDrawCart extends Cart {

    public WildDrawCart(){
        super(50, null);
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
