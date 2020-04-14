package ir.ac.aut;

public class WildCart extends Cart {

    public WildCart(){
        super(50, null);
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
