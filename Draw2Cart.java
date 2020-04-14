package ir.ac.aut;

public class Draw2Cart extends Cart {
    public Draw2Cart(COLOR colorOfCart){
        super(20, colorOfCart);
    }

    @Override
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed instanceof Draw2Cart || lastCartPlayed.getColor().equals(this.color)){
            return true;
        }else{
            return false;
        }
    }
}
