package ir.ac.aut;

public class SkipCart extends Cart {

    public SkipCart(COLOR colorOfCart){
        //we are sure that the number given to thid method will be in range [0,9]
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
