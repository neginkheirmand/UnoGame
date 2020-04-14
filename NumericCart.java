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
        if(lastCartPlayed instanceof NumericCart || lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void action(Player player){
        //before calling this method we make sure that this cart can be played
        player.addPoint(number);
        player.removeCart(this);
        return;
    }

}
