package ir.ac.aut;

public class WildDrawCard extends Cart {

    public WildDrawCard(COLOR colorOfCart){
        super(50, colorOfCart);
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof WildDrawCard|| lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }



}
