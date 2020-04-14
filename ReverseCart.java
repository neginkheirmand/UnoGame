package ir.ac.aut;

public class ReverseCart extends Cart {

    public ReverseCart(COLOR colorOfCart){
        super(20, colorOfCart);
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof ReverseCart || lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }

}
