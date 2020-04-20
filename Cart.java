package ir.ac.aut;

public class Cart implements Action{
    protected int point ;
    protected COLOR color;

    /**
     * the constructor of this class
     * @param pointCart the points of the card created
     * @param colorOfCart the color of the card(if its a wild or a wild draw card has the color as null)
     */
    public Cart(int pointCart, COLOR colorOfCart){
        point=pointCart;
        color = colorOfCart;
    }

    /**
     * the getter method for the color
     * @return the Color of the card
     */
    public COLOR getColor(){
        return color;
    }

    /**
     * a method to see if this card can be played by looking at the last card played
     * @param lastCartPlayed the card in the center of the board
     * @return boolean specifier if can be played
     */
    public boolean canPlayCart(Cart lastCartPlayed){
        if(lastCartPlayed.getColor().equals(this.color)){
            return true;
        }
        return false;
    }

    /**
     * a getter method to get the points of the card
     * @return the points of the card
     */
    public int getPoint(){
        return point;
    }

    /**
     * a method to print each line of a card in the consule
     * @param line the line
     */
    public void printLineOfCart(int line){
        if(line==-1){
            System.out.printf("           ");
            return;
        }

        //every cart will have 9 lines of high and 9 chars of width
        if(line==0||line==6){
            if(this.getColor()==null) {
                //its wild -kinded- cart
                for(int i=0; i<10; i++) {
                    String colorTemp = COLOR.getBackGroundColorByIndex(i%4);
                    System.out.printf(colorTemp+"+");
                }
                System.out.printf("\033[0m"+" ");
                return;
            }else {
                String colorOutput = COLOR.getBackGroundColor(this.getColor());
                System.out.printf(colorOutput+"++++++++++");
                System.out.printf("\033[0m"+" ");
                return;
            }
        }else if(line==1||line==5){
            if(this.getColor()==null){
                //its wild cart
                System.out.printf(COLOR.getBackGroundColor(COLOR.BLUE)+"+"+"\033[0m"+"        "+COLOR.getBackGroundColor(COLOR.YELLOW)+"+");
                System.out.printf("\033[0m"+" ");
                return;
                //agar ke tamiz nabud bayad bargardi va beynesh ye reset bezarE
            }else {
                String tempColor= COLOR.getBackGroundColor(this.getColor());
                System.out.printf(tempColor+"+");
                System.out.printf("\033[0m");
                System.out.printf("        "+tempColor+"+");
                System.out.printf("\033[0m"+" ");
                return;
            }
        }else if( line==2){
            if(this instanceof Draw2Cart|| this instanceof SkipCart||this instanceof NumericCart){
                String tempColor= COLOR.getBackGroundColor(this.getColor());
                System.out.printf(tempColor+"+");
                System.out.printf("\033[0m"+"        "+tempColor+"+");
                System.out.printf("\033[0m"+" ");
                return;
            }else if(this instanceof ReverseCart){
                String tempColor= COLOR.getBackGroundColor(this.getColor());
                System.out.printf(tempColor+ "+");
                System.out.printf("\033[0m"+"  ");
                System.out.printf(COLOR.getColor(this.getColor())+"<-->  "+tempColor+"+"+"\033[0m ");
                return;
            }else if(this instanceof WildDrawCart){
                System.out.printf(COLOR.getBackGroundColor(COLOR.YELLOW)+"+");
                System.out.printf("\033[0m");
                System.out.printf("  ");
                String wild=COLOR.getColor(COLOR.YELLOW)+"W"+COLOR.getColor(COLOR.GREEN)+"i"+COLOR.getColor(COLOR.BLUE)+"l"+COLOR.getColor(COLOR.RED)+"d";
                System.out.printf(wild+"  "+COLOR.getBackGroundColor(COLOR.GREEN)+"+"+"\033[0m"+" ");
                return;
            }else if(this instanceof WildCart ){
                System.out.printf(COLOR.getBackGroundColor(COLOR.YELLOW)+"+");
                System.out.printf("\033[0m");
                System.out.printf("        "+COLOR.getBackGroundColor(COLOR.GREEN)+"+");
                System.out.printf("\033[0m"+" ");
                return;
            }
        }else if(line==3){
            if(this instanceof WildCart){
                System.out.printf(COLOR.getBackGroundColor(COLOR.GREEN)+"+");
                System.out.printf("\033[0m  ");
                String wild=COLOR.getColor(COLOR.YELLOW)+"W"+COLOR.getColor(COLOR.GREEN)+"i"+COLOR.getColor(COLOR.BLUE)+"l"+COLOR.getColor(COLOR.RED)+"d";
                System.out.printf(wild+"  "+COLOR.getBackGroundColor(COLOR.RED)+"+"+"\033[0m ");
                return;
            }else if(this instanceof ReverseCart){
                String temp= COLOR.getBackGroundColor(this.getColor());
                System.out.printf(temp+"+"+"\033[0m"+COLOR.getColor(this.getColor())+" Reversi"+temp+"+"+"\033[0m ");
                return;
            }else if( this instanceof WildDrawCart){
                String draw=COLOR.getColor(COLOR.RED)+"D"+COLOR.getColor(COLOR.BLUE)+"r"+COLOR.getColor(COLOR.GREEN)+"a"+COLOR.getColor(COLOR.YELLOW)+"w";;
                System.out.printf(COLOR.getBackGroundColor(COLOR.GREEN)+"+"+"\033[0m  "+draw+"  "+COLOR.getBackGroundColor(COLOR.RED)+"+"+"\033[0m ");
                return;

            }else if(this instanceof Draw2Cart ){
                String temp = COLOR.getBackGroundColor(this.getColor());
                System.out.printf(temp+"+"+COLOR.getColor(this.getColor())+"  Draw  "+temp+"+"+"\033[0m ");
                return;
            }else if(this instanceof SkipCart){
                String temp= COLOR.getBackGroundColor(this.getColor());
                System.out.printf(temp+"+"+COLOR.getColor(this.getColor())+"  Skip  "+temp+"+"+"\033[0m ");
                return;
            }else if(this instanceof  NumericCart){
                String temp = COLOR.getBackGroundColor(this.getColor());
                System.out.printf(temp+"+"+COLOR.getColor(this.getColor())+"    %d   "+temp+"+"+"\033[0m ", ((NumericCart) this).getNumber());
                return;
            }

        }else if(line==4){
            if(this instanceof ReverseCart){
                String tempColor= COLOR.getBackGroundColor(this.getColor());
                System.out.printf(tempColor+ "+");
                System.out.printf("\033[0m");
                System.out.printf("  ");
                System.out.printf(COLOR.getColor(this.getColor())+"<-->  "+tempColor+"+"+"\033[0m ");
                return;
            }else if(this instanceof WildDrawCart){
                System.out.printf(COLOR.getBackGroundColor(COLOR.YELLOW)+"+"+COLOR.getColor(COLOR.YELLOW)+"   +4   "+COLOR.getBackGroundColor(COLOR.GREEN)+"+"+"\033[0m ");
                return;
            }else if(this instanceof NumericCart|| this instanceof SkipCart){
                String temp= COLOR.getBackGroundColor(this.getColor());
                System.out.printf(temp+"+"+"\033[0m"+"        "+temp+"+"+ "\033[0m ");
                return;
            }else if(this instanceof WildCart){
                System.out.printf(COLOR.getBackGroundColor(COLOR.YELLOW)+"+"+"\033[0m"+"        "+COLOR.getBackGroundColor(COLOR.GREEN)+"+"+ "\033[0m ");
                return;
            }else if(this instanceof Draw2Cart){
                String temp= COLOR.getBackGroundColor(this.getColor());
                System.out.printf(temp+"+"+COLOR.getColor(this.getColor())+"   +2   "+temp+"+"+"\033[0m ");
                return;
            }
        }
        return;
    }

    /**
     * a method for printing the card
     */
    public void printCart(){
        for(int i=0; i<7; i++){
            printLineOfCart(i);
            System.out.println();
        }
        return;
    }
}
