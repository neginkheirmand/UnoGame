package ir.ac.aut;

public class Cart implements Action{
    protected int point ;
    protected COLOR color;

    public Cart(int pointCart, COLOR colorOfCart){
        point=pointCart;
        color = colorOfCart;
    }

    public void action(Player player){

    }

//    public boolean Play(Cart lastCartInPlay, Player player){
//        return true;
//    }

    public COLOR getColor(){
        return color;
    }

    public boolean canPlayCart(Cart lastCartPlayed){
        return false;
    }
}
