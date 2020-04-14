package ir.ac.aut;

public class ReverseCart extends Cart {

    public ReverseCart(int number, COLOR colorOfCart){
        //we are sure that the number given to thid method will be in range [0,9]
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
