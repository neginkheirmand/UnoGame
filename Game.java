package ir.ac.aut;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    //the last Card added (las index Card) in this Array List is the last one added
    private ArrayList<Cart> carts;
    private ArrayList<Player> players;

    private static COLOR baseColor;
    private boolean clockWise;

    public Game(int numPlayers, int numPcPlayers){

        carts = new ArrayList<>();
            //first we create the Cards
        //Numeric Carts first
        for(int k=0; k<2; k++) {
            for (int i = k; i<10; i++){
                //the numbers
                for(int j=0; j<4; j++){
                    carts.add(new NumericCart(i, COLOR.getColorByIndex(j)));
                }
            }
        }

        //now the Skip carts (there are 8 of these carts in the game)
        for(int i=0; i<8; i++){
            carts.add(new SkipCart(COLOR.getColorByIndex(i%4)));
        }

        //now the Reverse carts (there are 8 of these carts in the game)
        for(int i=0; i<8; i++){
            carts.add(new ReverseCart(COLOR.getColorByIndex(i%4)));
        }

        //now the Draw +2 carts (there are 8 of these carts in the game)
        for(int i=0; i<8; i++){
            carts.add(new Draw2Cart(COLOR.getColorByIndex(i%4)));
        }

        //now the Wild carts (there are 4 of these carts in the game)
        for(int i=0; i<4; i++){
            carts.add(new WildCart());
        }

        //now the Wild Draw +4 carts (there are 4 of these carts in the game)
        for(int i=0; i<8; i++){
            carts.add(new WildDrawCart());
        }

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
            players.add(new Player(inputNamePlayer(i+1), firstCartPlayer));
        }

        for(int i=0; i<numPcPlayers; i++){
            //number of Pcplayers
            ArrayList<Cart> firstCartPlayer = new ArrayList<>();
            for(int j=0; j<7; j++){
                firstCartPlayer.add(carts.get(j));
                carts.remove(j);
            }
            players.add(new PcPlayer("bot"+(i+1), firstCartPlayer));
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
        printAllPlayersCarts();

        baseColor = carts.get(0).getColor();
        run();

    }

    private String inputNamePlayer(int number){
        System.out.println("Please enter yout name Player "+number);
        String namePlayer = (new Scanner(System.in)).next();
        return namePlayer;
    }

    private void shuffle(){
        Collections.shuffle(carts);
        return;
    }

    public static COLOR getBaseColor(){
        return baseColor;
    }

    private void reverseRotation(){
        clockWise=!clockWise;
        return;
    }

    private Cart getLastCart() {
        if (carts.size() != 0) {
            return carts.get(carts.size() - 1);
        }else{

            //            BAYAD BARAYE HANDLE KARDN IN HALAT KE CART HAYE STACK TAMUM SHODE BAYAD YE CHIZI DAR NAZAR GEREFT

            return null;
        }
    }

    private void updateInfoPcPlayer(int indexPlayer, Cart lastCart, boolean lastPlayed, boolean hasWild){
        if(players.get(  (indexPlayer+1)%players.size()) instanceof PcPlayer){
            ((PcPlayer) players.get(  (indexPlayer+1)%players.size())).updateInfoRight(lastCart, lastPlayed, hasWild);
        }
        if(players.get( (players.size() + (indexPlayer-1))%players.size()) instanceof PcPlayer){
            ((PcPlayer) players.get((players.size() + (indexPlayer-1))%players.size())).updateInfoLeft(lastCart, lastPlayed, hasWild);
        }
        return;
    }

    //we only call this method when the number of cards of the player is increased
    private void updateInfoAIAfterAddCart(int indexPlayer){
        if(players.get(  (indexPlayer+1)%players.size()) instanceof PcPlayer){
            ((PcPlayer) players.get(  (indexPlayer+1)%players.size())).addedCardToRightPlayer();
        }
        if(players.get( (players.size() + (indexPlayer-1))%players.size()) instanceof PcPlayer){
            ((PcPlayer) players.get((players.size() + (indexPlayer-1))%players.size())).addedCardToLeftPlayer();
        }
        return;
    }

    /**
     * this method returns the number of round advanced (the number of players who played in this mini run)
     *  only if the player with the turn has draw+2 carts can use them, else will draw 2*(number of punishmen) carts and LOOSE THE TURN
     * @param indexPlayer is the index player who has to answer with a Draw +2 cart or will have to take the punishment
     * @return the number of players which played with Draw +2 carts in this mini run
     */
    private int miniRunDraw2(int indexPlayer) {

        int roundsAdvanced = 1;
        while (true) {
            if (players.get(indexPlayer).numDraw2Carts() == 0) {
                System.out.println("player" + players.get(indexPlayer).getNamePlayer() + " doesnt have any draw+2 carts\n draw+" + 2 * roundsAdvanced + " carts");
                //now we know at this point this player doesnt have any Draw+2 cards in his hands but after this will add 2 * advanced cards to his hands so we cant be
                //sure any more
                for (int i = 0; i < 2 * roundsAdvanced; i++) {
                    players.get(indexPlayer).addCart(carts.get(0));
                    carts.remove(0);
                }
                updateInfoAIAfterAddCart(indexPlayer);
                return roundsAdvanced;
            }

            //player has Draw +2 carts available to play with
            Cart newCart;
            if(players.get(indexPlayer) instanceof PcPlayer){
                newCart =((PcPlayer) players.get(indexPlayer)).playDraw2Cart();
            }else{
                newCart = players.get(indexPlayer).playDraw2Cart();
            }
            newCart.printCart();
            //we are sure that the cart newCart is a draw +2 cart
            roundsAdvanced++;
            carts.add(newCart);
            System.out.println("since the player "+players.get(indexPlayer).getNamePlayer()+", played a draw+2 cart, the draw consequence is for the next player");

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

            if(clockWise==true){
                indexPlayer++;
                if(indexPlayer==players.size()){
                    indexPlayer=0;
                }
            }else {
                indexPlayer--;
                if (indexPlayer == -1) {
                    indexPlayer = players.size() - 1;
                }
            }
        }
    }

    private void miniWildCartRun(){
        baseColor = getColorFromPlayer();
        return;
    }

    private void wildCardRunAI(int indexPcPlayer){
        baseColor = ((PcPlayer)players.get(indexPcPlayer)).getColorAi(clockWise);
        System.out.println("the chosen color by The player "+ players.get(indexPcPlayer).getNamePlayer()+" is "+COLOR.getColor(baseColor)+baseColor.name()+"\033[0m");
        return;
    }

    private int miniRunWildDrawCart(int indexPlayer){
        //so that the color in the center is updated
        int advanced=1;
        int indexBefore;
        while (true) {
            if(clockWise){
                indexBefore=indexPlayer-1;
                if(indexBefore==-1){
                    indexBefore=players.size()-1;
                }
            }else{
                indexBefore=indexPlayer+1;
                if(indexBefore==players.size()){
                    indexBefore=0;
                }
            }
            if(players.get(indexBefore) instanceof PcPlayer){
                wildCardRunAI(indexBefore);
            }else {
                miniWildCartRun();
            }
            if (players.get(indexPlayer).numWildDrawCarts() == 0) {
                System.out.println("since the player " + players.get(indexPlayer).getNamePlayer() + " doesnt have any Wild Draw+4 carts\n has to draw+" + (4 * advanced ) + " carts");
                //updateInfoPcPlayer( indexPlayer, getLastCart(), true);
                //we dont need to add the information that this player doesnt have any Wild card cause after this, will get 4*advance card more and
                //probably will get one
                updateInfoAIAfterAddCart(indexPlayer);
                for (int i = 0; i < 4 * advanced; i++) {
                    players.get(indexPlayer).addCart(carts.get(0));
                    carts.remove(0);
                }
                return advanced;
            }

            //player has Wild Draw +4 carts available to play with
            Cart newCart;
            if(players.get(indexPlayer) instanceof PcPlayer){
                newCart = ((PcPlayer)players.get(indexPlayer)).playWildDrawCart();
            }else {
                newCart = players.get(indexPlayer).playWildDrawCart();
            }
            newCart.printCart();
            //we are sure that the cart newCart is a draw +2 cart
            advanced++;
            carts.add(newCart);
            System.out.println("since the player, played a Wild Draw+4 cart, the draw consequence is for the next player");

            //if the cursor is here then it means that the player has played a Wild draw cart
            //check if the game is over or if this player still has carts left in his hands
            if (players.get(indexPlayer).getNumberCartsLeft() == 0) {
                //Game over
                if (clockWise) {
                    for (int i = 0; i < 4 * advanced; i++) {
                        players.get((indexPlayer + 1) % players.size()).addCart(carts.get(0));
                        carts.remove(0);
                    }
                } else {
                    if (indexPlayer - 1 >= 0) {
                        for (int i = 0; i < 4 * advanced; i++) {
                            players.get(indexPlayer - 1).addCart(carts.get(0));
                            carts.remove(0);
                        }
                    } else {
                        for (int i = 0; i < 4 * advanced; i++) {
                            players.get(players.size() - 1).addCart(carts.get(0));
                            carts.remove(0);
                        }
                    }
                }
                printEndGame();
                System.exit(0);
            }

            if(clockWise==true){
                indexPlayer++;
                if(indexPlayer==players.size()){
                    indexPlayer=0;
                }
            }else {
                indexPlayer--;
                if (indexPlayer == -1) {
                    indexPlayer = players.size() - 1;
                }
            }
        }

    }

    private COLOR getColorFromPlayer(){

        String c =  COLOR.getBackGroundColor(COLOR.RED);
        String r = "\033[0m";
        System.out.println(COLOR.getColor(COLOR.RED)+"Select " + COLOR.getColor(COLOR.BLUE) + "A " + COLOR.getColor(COLOR.GREEN) +"Color"+r);
        System.out.println("1)");
        System.out.println("    "+c+"RRR"+r+"       "+c+"EEEEE"+r+"      "+c+"D"+r+"       ");
        System.out.println("    "+c+"R"+r+"  "+c+"R"+r+"      "+c+"E"+r+"         "+c+"D"+r+"   "+c+"D"+r+"    ");
        System.out.println("    "+c+"RRR"+r+"       "+c+"EEEEE"+r+"     "+c+"D"+r+"    "+c+"D"+r+"   ");
        System.out.println("    "+c+"R"+r+" "+c+"R"+r+"       "+c+"E"+r+"         "+c+"D"+r+"  "+c+"D"+r+"     ");
        System.out.println("    "+c+"R"+r+"  "+c+"R"+r+"      "+ c+"EEEEE"+r+"     "+c+"DD"+r+"      ");
        System.out.println();

        c= COLOR.getBackGroundColor(COLOR.BLUE);
        System.out.println("2)");
        System.out.println("    "+c+"BBB"+r+"      "+c+"L"+r+"       "+c+"U"+r+"   "+c+"U"+r+"     "+c+"EEEEE"+r);
        System.out.println("    "+c+"B"+r+"  "+c+"B"+r+"     "+c+"L"+r+"       "+c+"U"+r+"   "+c+"U"+r+"     "+c+"E"+r);
        System.out.println("    "+c+"BBB"+r+"      "+c+"L"+r+"       "+c+"U"+r+"   "+c+"U"+r+"     "+c+"EEEEE"+r);
        System.out.println("    "+c+"B"+r+"  "+c+"B"+r+"     "+c+"L"+r+"       "+c+"U"+r+"   "+c+"U"+r+"     "+c+"E"+r);
        System.out.println("    "+c+"BBB"+r+"      "+c+"LLLLL"+r+"    "+c+"UUU"+r+"      "+c+"EEEEE"+r);
        System.out.println();

        c= COLOR.getBackGroundColor(COLOR.YELLOW);
        System.out.println("3)");
        System.out.println("    "+c+"YYY"+r+"   "+c+"YYY"+r+"    "+c+"EEEEE"+r+"    "+c+"L"+r+"        "+c+"L"+r+"       "+c+"OOO"+r+"    "+c+"WWW"+r+"   "+c+"WWWW"+r+"    "+c+"WWW"+r);
        System.out.println("     "+c+"Y"+r+"     "+c+"Y"+r+"     "+c+"E"+r+"        "+c+"L"+r+"        "+c+"L"+r+"      "+c+"O"+r+"   "+c+"O"+r+"     "+c+"W"+r+"    "+c+"WW"+r+"     "+c+"W"+r);
        System.out.println("       "+c+"YYY"+r+"       "+c+"EEEEE"+r+"    "+c+"L"+r+"        "+c+"L"+r+"      "+c+"O"+r+"   "+c+"O"+r+"      "+c+"W"+r+"  "+c+"W"+r+"  "+c+"W"+r+"   "+c+"W"+r);
        System.out.println("        "+c+"Y"+r+"        "+c+"E"+r+"        "+c+"L"+r+"        "+c+"L"+r+"      "+c+"O"+r+"   "+c+"O"+r+"       "+c+"W"+r+" "+c+"W"+r+"   "+c+"W"+r+" "+c+"W"+r);
        System.out.println("        "+c+"Y"+r+"        "+c+"EEEEE"+r+"    "+c+"LLLLL"+r+"    "+c+"LLLLL"+r+"   "+c+"OOO"+r+"        "+c+"WWW"+r+"   "+c+"WWW"+r);
        System.out.println();

        c= COLOR.getBackGroundColor(COLOR.GREEN);
        System.out.println("4)");
        System.out.println("     "+c+"GGG"+r+"        "+c+"RRR"+r+"       "+c+"EEEEE"+r+"     "+c+"EEEEE"+r+"     "+c+"N"+r+"     "+c+"N"+r);
        System.out.println("    "+c+"G"+r+"           "+c+"R"+r+"  "+c+"R"+r+"      "+c+"E"+r+"         "+c+"E"+r+"         "+c+"N"+r+" "+c+"N"+r+"   "+c+"N"+r);
        System.out.println("    "+c+"G"+r+"  "+c+"GG"+r+"       "+c+"RRR"+r+"       "+c+"EEEEE"+r+"     "+c+"EEEEE"+r+"     "+c+"N"+r+"  "+c+"N"+r+"  "+c+"N"+r);
        System.out.println("    "+c+"G"+r+"   "+c+"G"+r+"       "+c+"R"+r+" "+c+"R"+r+"       "+c+"E"+r+"         "+c+"E"+r+"         "+c+"N"+r+"   "+c+"N"+r+" "+c+"N"+r);
        System.out.println("     "+c+"GGG"+r+"        "+c+"R"+r+"  "+c+"R"+r+"      "+c+"EEEEE"+r+"     "+c+"EEEEE"+r+"     "+c+"N"+r+"    "+c+"NN"+r);
        System.out.println();

        int choice = (new Scanner(System.in)).nextInt();
        while(choice <1 ||choice>4){
            System.out.println(COLOR.getColor(COLOR.RED)+"Enter " + COLOR.getColor(COLOR.BLUE) + "A " + COLOR.getColor(COLOR.GREEN) +"Valid "+COLOR.getColor(COLOR.YELLOW)+"Number"+r);
            choice = (new Scanner(System.in)).nextInt();
        }
        choice--;
        return COLOR.getColorByIndex(choice);
    }

    private void run(){
//        printEndGame();
        int indexPlayerInTurn = (new Random()).nextInt(players.size());
        System.out.println("the game begins with the player number "+(indexPlayerInTurn+1)+" name of player:  "+players.get(indexPlayerInTurn).getNamePlayer());
        Cart lastCart = getLastCart();
        //first we print the cart in the center
//        printRoundInfo();
        //first round:
        if(lastCart instanceof Draw2Cart){
            //its the start of the game and we know that the rotation is clock-wise kinded so we will just add the advanced times;
            int advanceRounds = miniRunDraw2(indexPlayerInTurn) +1;
            indexPlayerInTurn += advanceRounds;
            if(indexPlayerInTurn >= players.size()){
                indexPlayerInTurn = indexPlayerInTurn% players.size();
            }
        }else if(lastCart instanceof ReverseCart){
            //if the game starts with a reverse cart the player choosen to start can play but the rotation after that player will be Counter Clock-Wise
            reverseRotation();
        }else if(lastCart instanceof SkipCart){
            indexPlayerInTurn++;
            if(indexPlayerInTurn==players.size()){
                indexPlayerInTurn=0;
            }
        }
        while (true){
//            printEndGame();
            printRoundInfo();
            lastCart=getLastCart();

            System.out.println("the cart in the center is:");
            lastCart.printCart();

            if(lastCart instanceof WildDrawCart){
                System.out.println(" and base color chosen was :"+COLOR.getColor(baseColor)+baseColor.name()+"\033[0m");
                lastCart = new Cart(0, baseColor);
            }else if(lastCart instanceof WildCart){
                System.out.println(" and base color chosen was :"+COLOR.getColor(baseColor)+baseColor.name()+"\033[0m");
                lastCart = new Cart(0, baseColor);
            }

            System.out.println("Turn of "+ players.get(indexPlayerInTurn).getNamePlayer());
            Cart newCart;
            if(players.get(indexPlayerInTurn) instanceof PcPlayer){
                newCart = ((PcPlayer) players.get(indexPlayerInTurn)).aiPlayCart(lastCart, clockWise, getNumberCardsNextPlayer(indexPlayerInTurn));
            }else{
                newCart = players.get(indexPlayerInTurn).playCart(lastCart);
            }
            boolean cartPlayed = false;

            if(newCart == null){
                players.get(indexPlayerInTurn).addCart(carts.get(0));
                carts.remove(0);
                if(players.get(indexPlayerInTurn) instanceof PcPlayer){
                    newCart = ((PcPlayer) players.get(indexPlayerInTurn)).aiPlayCart(lastCart, clockWise, getNumberCardsNextPlayer(indexPlayerInTurn));
                }else {
                    newCart = players.get(indexPlayerInTurn).playCart(lastCart);
                }
                if(newCart == null){
                    //every information we have should be deleted
                    updateInfoAIAfterAddCart(indexPlayerInTurn);
                    updateInfoPcPlayer(indexPlayerInTurn, getLastCart(), false, false);
                    System.out.println("No carts available for player "+ players.get(indexPlayerInTurn).getNamePlayer()+" to play");
                }else{
                    System.out.println("the cart "+players.get(indexPlayerInTurn).getNamePlayer()+"played:");
                    newCart.printCart();
                    carts.add(newCart);
                    cartPlayed = true;
                }
            }else{
                System.out.println("the cart "+players.get(indexPlayerInTurn).getNamePlayer()+" played:");
                newCart.printCart();
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
                    if(clockWise){
                        int advance = miniRunDraw2((indexPlayerInTurn+1)%players.size());
                        indexPlayerInTurn += advance;
                        indexPlayerInTurn = indexPlayerInTurn % players.size();
                    }else{
                        int indexNext = indexPlayerInTurn -1;
                        if(indexNext == -1){
                            indexNext = players.size()-1;
                        }
                        int advance = miniRunDraw2(indexNext);
                        indexPlayerInTurn -= advance;
                        while(indexPlayerInTurn<0){
                            indexPlayerInTurn = players.size() + indexPlayerInTurn;
                        }
                    }
                }else if(newCart instanceof ReverseCart){
                    reverseRotation();
                }else if(newCart instanceof SkipCart){
                    if(clockWise) {
                        indexPlayerInTurn++;
                        indexPlayerInTurn= indexPlayerInTurn % players.size();
                    }else{
                        indexPlayerInTurn--;
                        if(indexPlayerInTurn==-1){
                            indexPlayerInTurn=players.size()-1;
                        }
                    }
                    System.out.println("the player " + players.get(indexPlayerInTurn).getNamePlayer() +" lost turn");
                }else if(newCart instanceof WildCart){
                    //since we can only use this kind of card when we dont have any other card with the given characteristics of the one the center
                    //means that the player with index: indexPlayerInTurn doesnt have any cards
                    updateInfoPcPlayer(indexPlayerInTurn, carts.get(carts.size()-2), false, true);
                    if(players.get(indexPlayerInTurn) instanceof PcPlayer){
                        wildCardRunAI(indexPlayerInTurn);
                    }else {
                        miniWildCartRun();
                    }


                }else if(newCart instanceof WildDrawCart){
                    updateInfoPcPlayer(indexPlayerInTurn, carts.get(carts.size()-2), false, true);
                    if(clockWise){
                        int advance = miniRunWildDrawCart((indexPlayerInTurn+1)%players.size());
                        indexPlayerInTurn += advance;
                        indexPlayerInTurn = indexPlayerInTurn % players.size();
                    }else{
                        int indexNext = indexPlayerInTurn -1;
                        if(indexNext == -1){
                            indexNext = players.size()-1;
                        }
                        int advance = miniRunWildDrawCart(indexNext);
                        indexPlayerInTurn -= advance;
                        while(indexPlayerInTurn<0){
                            indexPlayerInTurn = players.size() + indexPlayerInTurn;
                        }
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

    private int getNumberCardsNextPlayer(int indexPlayer){
        if(clockWise){
            indexPlayer++;
            indexPlayer=indexPlayer%players.size();
        }else{
            indexPlayer--;
            if(indexPlayer==-1){
                indexPlayer=players.size()-1;
            }
        }
        return players.get(indexPlayer).getNumberCartsLeft();
    }

    private void printEndGame(){

        printAllPlayersCarts();

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
        System.out.println(COLOR.getColor(COLOR.RED)+"|"+COLOR.getColor(COLOR.BLUE)+"  points:  "+COLOR.getColor(COLOR.RED)+"|"+COLOR.getColor(COLOR.BLUE)+" Number Of Carts: "+COLOR.getColor(COLOR.RED)+"");
        for(int i=0; i<maxName+28; i++){
            if(i==8||i==19){
                System.out.printf("+");
            }
            System.out.printf("-");
        }
        System.out.println(COLOR.getColor(COLOR.BLUE));

        //azinja bayad be tartib print koni:
        int[][] tempScores = new int[players.size()][2];
//        [i][0] = index
//        [i][1] = point

        for(int i=0; i<players.size(); i++){
            tempScores[i][1]=players.get(i).getPlayersPoint();
            tempScores[i][0]=i;
        }
        int minScore=500000;
        int index=0;

        for(int i=0; i < players.size()-1; i++) {
            minScore = tempScores[i][1];
            int anotherIndex = tempScores[i][0];
            index = i;
            for (int j = i + 1; j < players.size(); j++) {
                if (minScore > tempScores[j][1]) {
                    minScore = tempScores[j][1];
                    index = j;
                    anotherIndex = tempScores[j][0];
                }
            }
            if (index != i) {
                int temp = tempScores[i][1];
                int indexTemp = tempScores[i][0];
                tempScores[i][1] = minScore;
                tempScores[i][0] = anotherIndex;
                tempScores[index][1] = temp;
                tempScores[index][0] = indexTemp;
            }
        }

        for(int i=0; i<players.size(); i++){
            System.out.printf(players.get(tempScores[i][0]).getNamePlayer());
            for(int j=0; j<maxName-players.get(tempScores[i][0]).getNamePlayer().length(); j++){
                System.out.printf(" ");
            }
            System.out.printf(COLOR.getColor(COLOR.RED)+"|  "+COLOR.getColor(COLOR.BLUE)+players.get(tempScores[i][0]).getPlayersPoint());
            for(int j=digit(players.get(tempScores[i][0]).getPlayersPoint()); j< 9; j++){
                System.out.printf(" ");
            }

            System.out.println(COLOR.getColor(COLOR.RED)+"| "+COLOR.getColor(COLOR.BLUE)+players.get(tempScores[i][0]).numberOfCarts());

        }
        System.out.printf(COLOR.getColor(COLOR.RED));
        for(int i=0; i<maxName+28; i++){
            if(i==8 || i==19){
                System.out.printf("+");
            }
            System.out.printf("-");
        }
        System.out.println("\033[0m");
        return;


    }

    private int digit(int a){
        int digitNum=1;
        while(a/10!=0){
            a/=10;
            digitNum++;
        }
        return digitNum;
    }

    private void printBoard(){
        String rotationUniCode;
        if(clockWise==true){
            System.out.println("kind of rotation is Clock-wise");
            rotationUniCode ="\u21BB";
        }else{
            System.out.println("kind of rotation is Counter Clock-wise");
            rotationUniCode = "\u21BA";
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
            System.out.println(players.get(0).getNamePlayer()+"    "+rotationUniCode+"    "+players.get(1).getNamePlayer());
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("\\\\      //");

            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.println("   ----");
        }else if(players.size()==3){

            for(int i=0; i<players.get(0).getNamePlayer().length()+3; i++){
                System.out.printf(" ");
            }
            System.out.println(players.get(1).getNamePlayer());
            for(int i=0; i<players.get(0).getNamePlayer().length(); i++){
                System.out.printf(" ");
            }
            System.out.printf("//");
            for(int i=0; i<players.get(1).getNamePlayer().length(); i++){
                if(i==players.get(1).getNamePlayer().length()/2){
                    System.out.printf(rotationUniCode);
                }
                System.out.printf(" ");
            }
            System.out.println("\\\\");


            System.out.printf(players.get(0).getNamePlayer());
            for(int i=0; i<players.get(1).getNamePlayer().length()+6; i++){
                System.out.printf("-");
            }
            System.out.println(players.get(2).getNamePlayer());

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
                System.out.printf(" ");
            }
            System.out.println("\\\\");
            System.out.printf(players.get(0).getNamePlayer());
            int max;
            if(players.get(1).getNamePlayer().length()<players.get(3).getNamePlayer().length()){
                max = players.get(3).getNamePlayer().length();
            }else{
                max = players.get(1).getNamePlayer().length();
            }
            for(int i=0; i<max+3; i++){
                if(i-2==max/2){
                    System.out.printf(rotationUniCode);
                }
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
        System.out.println(COLOR.getColor(COLOR.RED)+"|"+COLOR.getColor(COLOR.BLUE)+"  points:  "+COLOR.getColor(COLOR.RED)+"| "+COLOR.getColor(COLOR.BLUE)+" Number Of Carts:"+COLOR.getColor(COLOR.RED));
        for(int i=0; i<maxName+28; i++){
            if(i==8||i==19){
                System.out.printf("+");
            }
            System.out.printf("-");
        }
        System.out.println(COLOR.getColor(COLOR.BLUE));
        for(int i=0; i<players.size(); i++){
            System.out.printf(players.get(i).getNamePlayer());
            for(int j=0; j<maxName-players.get(i).getNamePlayer().length(); j++){
                System.out.printf(" ");
            }
            System.out.printf(COLOR.getColor(COLOR.RED)+"|  "+COLOR.getColor(COLOR.BLUE)+players.get(i).getPlayersPoint());
            for(int j=digit(players.get(i).getPlayersPoint()); j<9 ; j++){
                System.out.printf(" ");
            }
            System.out.println(COLOR.getColor(COLOR.RED)+"|  "+COLOR.getColor(COLOR.BLUE)+players.get(i).numberOfCarts());

        }
        System.out.printf(COLOR.getColor(COLOR.RED));
        for(int i=0; i<maxName+28; i++){
            if(i==8||i==19){
                System.out.printf("+");
            }
            System.out.printf("-");
        }
        System.out.println("\033[0m");
        return;
    }

    private void printRoundInfo(){
        printBoard();
        printPlayersInfo();
        return;
    }

    private void printAllPlayersCarts(){
        for(int i=0; i<players.size(); i++){
            System.out.println("Player number "+(i+1)+"s carts:");
            if(players.get(i).getNumberCartsLeft()==0){
                System.out.println("the Player "+players.get(i).getNamePlayer()+" Won the game-has no cards left-");
            }
            players.get(i).printCarts(new Cart(10, COLOR.BLUE));
        }
        System.out.println();
    }

}
