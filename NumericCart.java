package ir.ac.aut;

public class NumericCart extends Cart{
    private final int number;

    public NumericCart(int number, COLOR colorOfCart){
        //we are sure that the number given to thid method will be in range [0,9]
        super(number, colorOfCart);
        this.number=number;
    }

    public int getNumber(){
        return number;
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof NumericCart && ((NumericCart) lastCartPlayed).getNumber()==this.number){
            return true;
        }else if(lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }

}
