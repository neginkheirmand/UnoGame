package ir.ac.aut;

public class PcPlayer extends Player{
    static COLOR[] nextPlayersColor={
        COLOR.RED, COLOR.YELLOW, COLOR.GREEN, COLOR.BLUE
    };
    static Cart[] nextPlayersTypeCards={
            new SkipCart(null), new ReverseCart(null), new Draw2Cart(null), new WildDrawCart(), new WildCart()
    };
    static Cart[] nextPlayersNumericCards={
            new NumericCart(0, null),
            new NumericCart(1, null),
            new NumericCart(2, null),
            new NumericCart(3, null),
            new NumericCart(4, null),
            new NumericCart(5, null),
            new NumericCart(6, null),
            new NumericCart(7, null),
            new NumericCart(8, null),
            new NumericCart(9, null),
    };


    public PcPlayer(){

    }


}
