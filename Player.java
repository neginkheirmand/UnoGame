package ir.ac.aut;

import sun.plugin2.gluegen.runtime.CPU;

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

    private int numPlayableCarts(Cart lastPlayedCart){
        int canPlayNum=0;
        for(int i=0; i<playersCarts.size(); i++){
            if(playersCarts.get(i).canPlayCart(lastPlayedCart)){
                canPlayNum++;
            }
        }
        return canPlayNum;
    }

    public int show(Cart lastPlayedCart){
        //we have to make sure that this player has at least one cart in his/her hands
        int canPlayNum=numPlayableCarts(lastPlayedCart);
        if(canPlayNum==0){
            //cannot play
            //give a new cart to this player

            //check if this new cart can be played
            //if can be played
            return 0;
            //return typesh ham mohem chon gharare indexE bashe ke user vared mikone
        }else{
            int numHandCarts=playersCarts.size();
            int numberOfRepetition=0;
            while (numHandCarts>0){
                //there will be printing 7 carts in each  segment of 10 rows

                for(int i=0;i<7; i++){
                    for(int j=0; j<7; j++) {
                        if(numHandCarts>0&&numHandCarts<7){
                            //last segments
                            if((7-numberOfRepetition)%2==0){
                                //symmetric
                                if(j==(7-numberOfRepetition)/2){
                                    printLineOfCart(i, playersCarts.get((j + numberOfRepetition * 7 )- (7-numberOfRepetition)/2));

                                }
                                else if(j<(7-numberOfRepetition)/2){
                                    printLineOfCart(0, null);
                                }else{
                                    break;
                                }
                            }else{
                                //non-symmetric
                                if(j==1+(7-numberOfRepetition)/2){
                                    printLineOfCart(i, playersCarts.get((j + numberOfRepetition * 7 )- ((7-numberOfRepetition)/2) -1));
                                }
                                else if(j<1+((7-numberOfRepetition)/2)){
                                    printLineOfCart(0, null);
                                }else{
                                    break;
                                }

                            }
                        } else {
                            printLineOfCart(i, playersCarts.get(j + numberOfRepetition * 7));
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                numHandCarts-=7;
                numberOfRepetition++;
            }

        }
        return 1;
    }

    private void printLineOfCart(int line, Cart cartToPrint){
        if(cartToPrint==null){
            System.out.printf("           ");
            return;
        }
        //every cart will have 9 lines of high and 9 chars of width
        if(line==0||line==6){
            if(cartToPrint.getColor()==null) {
                //its wild -kinded- cart
                for(int i=0; i<10; i++) {
                    String colorTemp = COLOR.getBackGroundColorByIndex(i%4);
                    System.out.printf(colorTemp+"+");
                }
                System.out.printf("\033[0m"+" ");
                return;
            }else {
                String colorOutput = COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(colorOutput+"++++++++++");
                System.out.printf("\033[0m"+" ");
                return;
            }
        }else if(line==1||line==5){
            if(cartToPrint.getColor()==null){
                //its wild cart
                String tempColor= COLOR.getBackGroundColor(COLOR.BLUE);
                System.out.printf(tempColor+"+"+"\033[0m"+"        "+tempColor+"+");
                System.out.printf("\033[0m"+" ");
                return;
                //agar ke tamiz nabud bayad bargardi va beynesh ye reset bezarE
            }else {
                String tempColor= COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(tempColor+"+");
                System.out.printf("\033[0m");
                System.out.printf("        "+tempColor+"+");
                System.out.printf("\033[0m"+" ");
                return;
            }
        }else if( line==2){
            if(cartToPrint instanceof Draw2Cart|| cartToPrint instanceof SkipCart||cartToPrint instanceof NumericCart){
                String tempColor= COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(tempColor+"+");
                System.out.printf("\033[0m"+"        "+tempColor+"+");
                System.out.printf("\033[0m"+" ");
                return;
            }else if(cartToPrint instanceof ReverseCart){
                String tempColor= COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(tempColor+ "+");
                System.out.printf("\033[0m"+"  ");
                System.out.printf(COLOR.getColor(cartToPrint.getColor())+"<-->  "+tempColor+"+"+"\033[0m");
                return;
            }else if(cartToPrint instanceof WildDrawCart){
                String tempColor= COLOR.getBackGroundColor(COLOR.YELLOW);
                System.out.printf(tempColor+"+");
                System.out.printf("\033[0m");
                System.out.printf("  ");
                String wild=COLOR.getColor(COLOR.YELLOW)+"W"+COLOR.getColor(COLOR.GREEN)+"i"+COLOR.getColor(COLOR.BLUE)+"l"+COLOR.getColor(COLOR.RED)+"d";
                System.out.printf(wild+"  "+tempColor+"+"+"\033[0m"+" ");
                return;
            }else if(cartToPrint instanceof WildCart ){
                String tempColor= COLOR.getBackGroundColor(COLOR.YELLOW);
                System.out.printf(tempColor+"+");
                System.out.printf("\033[0m");
                System.out.printf("        "+tempColor+"+");
                System.out.printf("\033[0m"+" ");
                return;
            }
        }else if(line==3){
            if(cartToPrint instanceof WildCart){
                String tempColor= COLOR.getBackGroundColor(COLOR.GREEN);
                System.out.printf(tempColor+"+");
                System.out.printf("\033[0m  ");
                String wild=COLOR.getColor(COLOR.YELLOW)+"W"+COLOR.getColor(COLOR.GREEN)+"i"+COLOR.getColor(COLOR.BLUE)+"l"+COLOR.getColor(COLOR.RED)+"d";
                System.out.printf(wild+"  "+tempColor+"+"+"\033[0m ");
                return;
            }else if(cartToPrint instanceof ReverseCart){
                String temp= COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(temp+"+"+"\033[0m"+COLOR.getColor(cartToPrint.getColor())+" Reversi"+temp+"+"+"\033[0m ");
                return;
            }else if( cartToPrint instanceof WildDrawCart){
                String draw=COLOR.getColor(COLOR.RED)+"D"+COLOR.getColor(COLOR.BLUE)+"r"+COLOR.getColor(COLOR.GREEN)+"a"+COLOR.getColor(COLOR.YELLOW)+"w";
                String temp = COLOR.getBackGroundColor(COLOR.GREEN);
                System.out.printf(temp+"+"+"\033[0m"+draw+"  "+temp+"+"+"\033[0m ");
                return;

            }else if(cartToPrint instanceof Draw2Cart ){
                String temp = COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(temp+"+"+COLOR.getColor(cartToPrint.getColor())+"  Draw  "+temp+"+"+"\033[0m ");
                return;
            }else if(cartToPrint instanceof SkipCart){
                String temp= COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(temp+"+"+COLOR.getColor(cartToPrint.getColor())+"  Skip  "+temp+"+"+"\033[0m ");
                return;
            }else if(cartToPrint instanceof  NumericCart){
                String temp = COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(temp+"+"+COLOR.getColor(cartToPrint.getColor())+"    %d   "+temp+"+"+"\033[0m ", ((NumericCart) cartToPrint).getNumber());
                return;
            }

        }else if(line==4){
            if(cartToPrint instanceof ReverseCart){
                String tempColor= COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(tempColor+ "+");
                System.out.printf("\033[0m");
                System.out.printf("  ");
                System.out.printf(COLOR.getColor(cartToPrint.getColor())+"<-->  "+tempColor+"+"+"\033[0m ");
                return;
            }else if(cartToPrint instanceof WildDrawCart){
                String temp= COLOR.getBackGroundColor(COLOR.YELLOW);
                System.out.println(temp+"+"+COLOR.getColor(COLOR.YELLOW)+"   +4   "+temp+"+"+"\033[0m ");
                return;
            }else if(cartToPrint instanceof NumericCart|| cartToPrint instanceof SkipCart){
                String temp= COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(temp+"+"+"\033[0m"+"        "+temp+"+"+ "\033[0m ");
                return;
            }else if(cartToPrint instanceof WildCart){
                String temp= COLOR.getBackGroundColor(COLOR.YELLOW);
                System.out.printf(temp+"+"+"\033[0m"+"        "+temp+"+"+ "\033[0m ");
                return;
            }else if(cartToPrint instanceof Draw2Cart){
                String temp= COLOR.getBackGroundColor(cartToPrint.getColor());
                System.out.printf(temp+"+"+COLOR.getColor(cartToPrint.getColor())+"   +2   "+temp+"+"+"\033[0m ");
                return;
            }
        }
        return;
    }

    public int numberOfCarts(){
        return playersCarts.size();
    }
}
