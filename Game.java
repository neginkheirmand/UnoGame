package ir.ac.aut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Game {
    //the last cart added (las index cart) in this Array List is the last one added
    private ArrayList<Cart> carts;
    private ArrayList<Player> players;

    private boolean clockWise;
//        System.out.println("\u21BA");   (counter clock wise)
//        System.out.println("\u21BB");    (clock wise)


    public Game(int numPlayers){


        //first we create the carts

        //now we create the players
        //we are sure that the number given to this constructor is in range [2,4]


    }

    private void shuffle(){
        Collections.shuffle(carts);
        return;
    }

    public void reverseRotation(){
        clockWise=!clockWise;
        return;
    }

    public Cart getLastCart() {
        if (carts.size() != 0) {
            return carts.get(carts.size() - 1);
        }else{

            //            BAYAD BARAYE HANDLE KARDN IN HALAT KE CART HAYE STACK TAMUM SHODE BAYAD YE CHIZI DAR NAZAR GEREFT

            return null;
        }
    }


}
