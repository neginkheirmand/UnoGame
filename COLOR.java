package ir.ac.aut;

public enum COLOR {
    //the red color
    RED,
    //the blue color
    BLUE,
    //the yellow color
    YELLOW,
    //the green color
    GREEN;

    /**
     * getter method for the String containing the color changer for print methpd
     * @param color the color
     * @return the string of color changer
     */
    public static String getColor(COLOR color){
        if(color.equals(COLOR.RED)){
            return "\033[1;91m";
        }else if(color.equals(COLOR.BLUE)){
            return "\033[1;96m";
        }else if(color.equals(COLOR.YELLOW)){
            return "\033[1;93m";
        }else if(color.equals(COLOR.GREEN)){
            return "\033[1;92m";
        }
        return null;
    }

    /**
     * a method to get the Color by the index of that color in this Enum
     * @param indexColor index of the color
     * @return the Color
     */
    public static COLOR getColorByIndex(int indexColor){
        if(indexColor==COLOR.RED.ordinal()){
            return RED;
        }else if(indexColor==COLOR.BLUE.ordinal()){
            return BLUE;
        }else if(indexColor==COLOR.YELLOW.ordinal()){
            return YELLOW;
        }else if(indexColor==COLOR.GREEN.ordinal()){
            return GREEN;
        }
        return null;
    }

    /**
     * getter method for the String containing the Background color changer for print methpd
     * @param color the color
     * @return the string of color changer
     */
    public static String getBackGroundColor(COLOR color){
        if(color.equals(COLOR.RED)){
            return "\u001b[48;5;101m";
        }else if(color.equals(COLOR.BLUE)){
            return "\u001b[48;5;104m";
        }else if(color.equals(COLOR.YELLOW)){
            return "\u001b[48;5;103m";
        }else if(color.equals(COLOR.GREEN)){
            return "\u001b[48;5;102m";
        }
        return null;
    }

    /**
     * a geetter method to get the background color by the index of the color in this enum
     * @param indexColor the index of color
     * @return the String containing the background color
     */
    public static String getBackGroundColorByIndex(int indexColor){
        if(indexColor==COLOR.RED.ordinal()){
            return "\u001b[48;5;101m";
        }else if(indexColor==COLOR.BLUE.ordinal()){
            return "\u001b[48;5;104m";
        }else if(indexColor==COLOR.YELLOW.ordinal()){
            return "\u001b[48;5;103m";
        }else if(indexColor==COLOR.GREEN.ordinal()){
            return "\u001b[48;5;102m";
        }
        return null;
    }
}
