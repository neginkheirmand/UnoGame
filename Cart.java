package ir.ac.aut;

public class Cart implements Action{
    protected int point ;
    protected COLOR color;

    public Cart(int pointCart, COLOR colorOfCart){
        point=pointCart;
        color = colorOfCart;
    }

    public void action(Player player){
        //before calling this method we make sure that this cart can be played
        player.addPoint(point);
        player.removeCart(this);
        return;
    }

//    public boolean Play(Cart lastCartInPlay, Player player){
//        return true;
//    }

    public COLOR getColor(){
        return color;
    }

    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed.getColor().equals(this.color)){
            return true;
        }
        return false;
    }
}
