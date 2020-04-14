package ir.ac.aut;

public class SkipCart extends Cart {

    public SkipCart(COLOR colorOfCart){
        super(20, colorOfCart);
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof SkipCart || lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }

}
