package ir.ac.aut;

public enum COLOR {
    RED,
    BLUE,
    YELLOW,
    GREEN;
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
