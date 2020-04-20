package ir.ac.aut;

public class NumericCart extends Cart{
//    is the number of the numeric card
    private final int number;

    /**
     * the constructor method
     * @param number is the number of the numeric card
     * @param colorOfCart the color of the skip card
     */
    public NumericCart(int number, COLOR colorOfCart){
        //we are sure that the number given to thid method will be in range [0,9]
        super(number, colorOfCart);
        this.number=number;
    }

    /**
     * getter method for tnumber field of the class
     * @return the number of the numeric card
     */
    public int getNumber(){
        return number;
    }


    /**
     * method returning a boolean specifying if the card can be played
     * @param lastCartPlayed the card in the center of the board
     * @return boolean specifying if the card can be played
     */
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
