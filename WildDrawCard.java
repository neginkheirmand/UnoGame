package ir.ac.aut;

public class WildDrawCard extends Cart {

    public WildDrawCard(COLOR colorOfCart){
        //we are sure that the number given to thid method will be in range [0,9]
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
