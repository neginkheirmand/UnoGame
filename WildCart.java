package ir.ac.aut;

public class WildCart extends Cart {

    public WildCart(COLOR colorOfCart){
        super(50, colorOfCart);
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof WildCart|| lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }

}
