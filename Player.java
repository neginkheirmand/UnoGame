package ir.ac.aut;

import java.util.ArrayList;

public class Player {
    private final String namePlayer;
    private int playersPoint;
    private ArrayList<Cart> playersCarts;

    public Player(String name, ArrayList<Cart> playersCarts){
        this.namePlayer=name;
        //we are sure that the Array List given to this player is an array of 5 valid carts
        this.playersCarts=playersCarts;
    }

    public void addPoint(int ponitsToAdd){
        playersPoint+=ponitsToAdd;
        return;
    }

    public void removeCart(Cart beRemovedCart){
        for(int i=0; i<playersCarts.size(); i++){
            if(beRemovedCart.equals(playersCarts.get(i))){
                playersCarts.remove(i);

                return;
            }
        }
        //BADAN IN RO PAK KON, FAGHAT BARAYE MOTMAEN SHODN
        System.out.println("MOSHKEL TUYE FUNCTION RemoveCart az class Player, darE sai mikoni CartE ro remove koni ke tuye list cart haye in player nist");
    }

//    public Cart playCart(Cart lastCartPlayed){
//
//
//    }

    private int Show(Cart lastPlayedCart){
        //we have to make sure that this player has at least one cart in his/her hands
        int canPlayNum=0;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i).canPlayCart(lastPlayedCart)){
                canPlayNum++;
            }

            //here is the part of printing -still not completed
        }

        if(canPlayNum==0){
            //cannot play
            //give a new cart to this player

            //check if this new cart can be played
            //if can be played
        }
        return 0;
    }

    private void printLineOfCart(int line, Cart cartToPrint){
        //every cart will have 9 lines of high and 9 chars of width
        if(line==0||line==8){
            String colorOutput= COLOR.getBackGroundColor(cartToPrint.getColor());
            System.out.printf("+-----+ ");
            return;
        }


    }

    public int numberOfCarts(){
        return playersCarts.size();
    }
}
