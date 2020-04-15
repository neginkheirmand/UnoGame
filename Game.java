package ir.ac.aut;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class Game {
    //the last cart added (las index cart) in this Array List is the last one added
    private ArrayList<Cart> carts;
    private ArrayList<Player> players;

    private boolean clockWise;
//        System.out.println("\u21BA");   (counter clock wise)
//        System.out.println("\u21BB");    (clock wise)


    public Game(int numPlayers){

        carts = new ArrayList<>();
            //first we create the carts
        //Numeric Carts first
        for(int k=0; k<2; k++) {
            //2 segments with the only difference of the zero numeric cart
            for (int i = k; i<10; i++){
                //the numbers
                for(int j=0; j<4; j++){
                    carts.add(new NumericCart(i, COLOR.getColorByIndex(j)));
                }
            }
        }
        System.out.println("the size of carts in this game after adding the numeric carts is:"+carts.size()+"  (should be 76)");

        //now the Skip carts (there are 8 of these carts in the game)
        for(int i=0; i<8; i++){
            carts.add(new SkipCart(COLOR.getColorByIndex(i%4)));
        }
        System.out.println("the size of carts in this game after adding the Skip carts is:"+carts.size()+"  (should be 76 + 8 = 84)");

        //now the Reverse carts (there are 8 of these carts in the game)
        for(int i=0; i<8; i++){
            carts.add(new ReverseCart(COLOR.getColorByIndex(i%4)));
        }
        System.out.println("the size of carts in this game after adding the Reverse carts is:"+carts.size()+"  (should be 76 + 8 +8 = 92)");

        //now the Draw +2 carts (there are 8 of these carts in the game)
        for(int i=0; i<8; i++){
            carts.add(new Draw2Cart(COLOR.getColorByIndex(i%4)));
        }
        System.out.println("the size of carts in this game after adding the Draw +2 carts is:"+carts.size()+"  (should be 76 + 8 + 8 + 8 = 100)");

        //now the Wild carts (there are 4 of these carts in the game)
        for(int i=0; i<4; i++){
            carts.add(new WildCart());
        }
        System.out.println("the size of carts in this game after adding the Wild carts is:"+carts.size()+"  (should be 76 + 8 + 8 + 4 = 104)");

        //now the Wild Draw +4 carts (there are 4 of these carts in the game)
        for(int i=0; i<8; i++){
            carts.add(new WildDrawCart());
        }
        System.out.println("the size of carts in this game after adding the Draw +2 carts is:"+carts.size()+"  (should be 76 + 8 + 8 + 4 + 4 = 108)");

        //shuffle the carts
        shuffle();


        //now we create the players
        //we are sure that the number given to this constructor is in range [2,10] (10 player each one start with 7 carts there will be left only 38 carts in the center)
        players= new ArrayList<>();
        for(int i=0; i<numPlayers; i++){
            //number of players
            ArrayList<Cart> firstCartPlayer = new ArrayList<>();
            for(int j=0; j<7; j++){
                firstCartPlayer.add(carts.get(j));
                carts.remove(j);
            }
            players.add(new Player("Player number "+(i+1), firstCartPlayer));
        }
        //the last cart in the list should be of type other than Wild cart

        if(carts.get(carts.size()-1) instanceof WildDrawCart || carts.get(carts.size()-1) instanceof WildCart){
            int index=carts.size()-2;
            while (carts.get(index) instanceof WildDrawCart || carts.get(index) instanceof WildCart){
                index--;
            }
            Collections.swap(carts, carts.size()-1, index);
        }
        //now we are sure that the last cart in the array list is definitely a colorful cart

        clockWise = true;
        run();

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

    private int miniRunDraw2(int indexPlayer){
        //if the last cart played before this player (indexPlayer) was a draw +2 cart the game continues its course in this method
        //this method returns the number of round advanced

        int roundsAdvanced = 1;
        while (true) {
            Cart lastPlayedCart = getLastCart();
            Cart newCart = players.get(indexPlayer).playCart(lastPlayedCart);


            if (newCart == null) {
                players.get(indexPlayer).addCart(carts.get(0));
                carts.remove(0);
                newCart = players.get(indexPlayer).playCart(lastPlayedCart);
                if (newCart == null) {
                    System.out.printf("No carts available for this player to play with, " + players.get(indexPlayer).getNamePlayer() + "loses turn (draw +2 carts)\n carts in your hand:");
                    for (int i = 0; i < 2 * roundsAdvanced; i++) {
                        players.get(indexPlayer).addCart(carts.get(0));
                        carts.remove(0);
                    }
                    players.get(indexPlayer).printCarts(lastPlayedCart);
                    return roundsAdvanced;
                } else if (newCart instanceof Draw2Cart) {
                    roundsAdvanced++;
                    System.out.println(" next player should get +" + 2 * roundsAdvanced + "carts");
                    carts.add(newCart);
                } else {
                    carts.add(newCart);
                    System.out.println(" Draw " + (2 * roundsAdvanced) + " carts");
                    for (int i = 0; i < 2 * roundsAdvanced; i++) {
                        players.get(indexPlayer).addCart(carts.get(0));
                        carts.remove(0);
                    }
                    players.get(indexPlayer).printCarts(lastPlayedCart);
                    return roundsAdvanced;
                }
            } else {
                //player has carts available to play with
                if (newCart instanceof Draw2Cart) {
                    roundsAdvanced++;
                    carts.add(newCart);
                    System.out.println("since the player, played a draw+2 cart, the draw consequence is for the next player");
                } else {
                    System.out.println("the player draw " + 2 * roundsAdvanced + "carts");
                    for (int i = 0; i < 2 * roundsAdvanced; i++) {
                        players.get(indexPlayer).addCart(carts.get(0));
                        carts.remove(0);
                    }
                    carts.add(newCart);
                    return roundsAdvanced;
                }
                //if the cursor is here then it means that the player has played a draw cart
                //check if the game is over or if this player still has carts left in his hands
                if (players.get(indexPlayer).getNumberCartsLeft() == 0) {
                    //Game over
                    if (clockWise) {
                        for (int i = 0; i < 2 * roundsAdvanced; i++) {
                            players.get((indexPlayer + 1) % players.size()).addCart(carts.get(0));
                            carts.remove(0);
                        }
                    } else {
                        if (indexPlayer - 1 >= 0) {
                            for (int i = 0; i < 2 * roundsAdvanced; i++) {
                                players.get(indexPlayer - 1).addCart(carts.get(0));
                                carts.remove(0);
                            }
                        } else {
                            for (int i = 0; i < 2 * roundsAdvanced; i++) {
                                players.get(players.size() - 1).addCart(carts.get(0));
                                carts.remove(0);
                            }
                        }
                    }
                    printEndGame();
                    System.exit(0);
                }

            }

            //age reCde inja yani in player(ham) cart Draw+2 estefade karde va bayad did 2*roundAdvanced cart ro bayad be ki dad
            //ta inja karhayi ke bayad baraye un player mikard hal shod ama hichkarE baraye player baD nakarDm
            //agar player baD masaln
            .
            if (newCart instanceof Draw2Cart) {
                playDar2CartForPlayer(indexPlayerInTurn);
            } else if (newCart instanceof ReverseCart) {
                indexPlayerInTurn++;
                if (indexPlayerInTurn == players.size()) {
                    indexPlayerInTurn = indexPlayerInTurn % players.size();
                }
                reverseRotation();
            } else if (newCart instanceof SkipCart) {
                indexPlayerInTurn--;
                if (indexPlayerInTurn < 0) {
                    indexPlayerInTurn += players.size();
                }
            }


        }
    }

    private void run(){
        int indexPlayerInTurn = (new Random()).nextInt(players.size());
        System.out.println("the game begins with the player number "+(indexPlayerInTurn+1)+" name of player:"+players.get(indexPlayerInTurn).getNamePlayer());
        Cart lastCart=getLastCart();
        //first we print the cart in the center
        printRoundInfo();
        lastCart.PrintCart();
        //first round:
        if(lastCart instanceof Draw2Cart){
            miniRunDraw2(indexPlayerInTurn);
        }else if(lastCart instanceof ReverseCart){
            indexPlayerInTurn++;
            if(indexPlayerInTurn==players.size()) {
                indexPlayerInTurn = indexPlayerInTurn % players.size();
            }
            reverseRotation();
        }else if(lastCart instanceof SkipCart){
            indexPlayerInTurn--;
            if(indexPlayerInTurn<0){
                indexPlayerInTurn+=players.size();
            }
        }
        while (true){
            lastCart=getLastCart();
            Cart newCart = players.get(indexPlayerInTurn).playCart(lastCart);
            boolean cartPlayed = false;
            if(newCart == null){
                players.get(indexPlayerInTurn).addCart(carts.get(0));
                carts.remove(0);
                newCart = players.get(indexPlayerInTurn).playCart(lastCart);
                if(newCart == null){
                    System.out.printf("No carts available for this player to play, "+players.get(indexPlayerInTurn).getNamePlayer()+"loses turn");
                }else{
                    carts.add(newCart);
                    cartPlayed = true;
                }
            }else{
                carts.add(newCart);
                cartPlayed = true;
                //check if the game is over
                if(players.get(indexPlayerInTurn).getNumberCartsLeft()==0){
                    printEndGame();
                    System.exit(0);
                }

            }
            if(cartPlayed == true){
                if(newCart instanceof  Draw2Cart){
                    playDar2CartForPlayer(indexPlayerInTurn);
                }else if(newCart instanceof ReverseCart){
                    indexPlayerInTurn++;
                    if(indexPlayerInTurn==players.size()) {
                        indexPlayerInTurn = indexPlayerInTurn % players.size();
                    }
                    reverseRotation();
                }else if(newCart instanceof SkipCart){
                    indexPlayerInTurn--;
                    if(indexPlayerInTurn<0){
                        indexPlayerInTurn+=players.size();
                    }
                }
            }

            //now we move to the next Player
            if( clockWise ){
                indexPlayerInTurn++;
                if(indexPlayerInTurn==players.size()) {
                    indexPlayerInTurn = indexPlayerInTurn % players.size();
                }
            }else{
                indexPlayerInTurn--;
                if(indexPlayerInTurn<0){
                    indexPlayerInTurn+=players.size();
                }
            }


        }
    }

    private void printEndGame(){
        System.out.println("the game is over but stil this part of the programm is not completed");
    }

    private void printBoard(){
        String rotationUniCode;
        if(clockWise==true){
            System.out.println("kind of rotation is Clock-wise");
            rotationUniCode ="\u21BB";
        }else{
            System.out.println("kind of rotation is Counter Clock-wise");
            rotationUniCode = "\u21BB";
        }

        if(players.size()==2){
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("   ----");
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("//      \\\\");
            System.out.printf(players.get(0).getNamePlayer()+"    "+rotationUniCode+"    "+players.get(1).getNamePlayer());
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("\\\\      //");

            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("   ----");
        }else if(players.size()==3){

            for(int i=0; i<players.get(0).getNamePlayer().length()+1; i++){
                System.out.printf(" ");
            }
            System.out.println(players.get(1).getNamePlayer());
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("//   "+rotationUniCode+"  \\\\");
            System.out.printf(players.get(0).getNamePlayer()+"----------"+players.get(2).getNamePlayer());
        }else if(players.size()>=4){
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("  "+players.get(1).getNamePlayer());
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.printf("//");
            for(int i=0; i<(players.get(1).getNamePlayer().length()+players.get(3).getNamePlayer().length())/2; i++){
                if(i==(players.get(1).getNamePlayer().length()+players.get(3).getNamePlayer().length())/4){
                    System.out.printf(rotationUniCode);
                }else {
                    System.out.printf(" ");
                }
            }
            System.out.println("\\\\");
            System.out.printf(players.get(0).getNamePlayer());
            int max;
            if(players.get(1).getNamePlayer().length()<players.get(3).getNamePlayer().length()){
                max = players.get(3).getNamePlayer().length();
            }else{
                max = players.get(1).getNamePlayer().length();
            }
            for(int i=0; i<max; i++){
                System.out.printf(" ");
            }
            System.out.println(players.get(2).getNamePlayer());
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            if(players.size()==4) {
                System.out.printf("\\\\");
            }else{
                System.out.printf("...");

            }
            for(int i=0; i<(players.get(1).getNamePlayer().length()+players.get(3).getNamePlayer().length())/2; i++){
                System.out.printf(" ");
            }
            System.out.println("//");

            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("  "+players.get(3).getNamePlayer());

        }
    }

    //badan bayad in method ro update koni be tori ke be tartib neshun bede emtiad haro
    private void printPlayersInfo(){
        int maxName = 8;
        for(int i=0; i<players.size(); i++){
            if(maxName<players.get(i).getNamePlayer().length()){
                maxName = players.get(i).getNamePlayer().length();
            }
        }

        System.out.println();
        System.out.printf(COLOR.getColor(COLOR.BLUE)+"Names:");
        for(int i=0; i<maxName-6; i++){
            System.out.printf(" ");
        }
        System.out.printf(COLOR.getColor(COLOR.RED)+"|"+COLOR.getColor(COLOR.BLUE)+"  points:  "+COLOR.getColor(COLOR.RED)+"|");
        for(int i=0; i<maxName+10; i++){
            System.out.printf("-");
        }
        System.out.println(COLOR.getColor(COLOR.BLUE));
        for(int i=0; i<players.size(); i++){
            System.out.printf(players.get(i).getNamePlayer());
            for(int j=0; j<maxName-players.get(i).getNamePlayer().length(); j++){
                System.out.printf(" ");
            }
            System.out.println(COLOR.getColor(COLOR.RED)+"|"+COLOR.getColor(COLOR.BLUE)+players.get(i).getPlayersPoint());
        }
        for(int i=0; i<maxName+10; i++){
            System.out.printf("-");
        }
        return;
    }

    private void printRoundInfo(){
        printBoard();
        printPlayersInfo();
        return;
    }


}
