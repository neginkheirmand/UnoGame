package ir.ac.aut;

public class ReverseCart extends Cart {
    Game unoGame;

    public ReverseCart(int number, COLOR colorOfCart, Game unoGame){
        //we are sure that the number given to thid method will be in range [0,9]
        super(20, colorOfCart);
        this.unoGame=unoGame;
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof ReverseCart || lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void action(Player player){
        //before calling this method we make sure that this cart can be played
        unoGame.reverseRotation();
        player.addPoint(point);
        player.removeCart(this);
        return;
    }

}
