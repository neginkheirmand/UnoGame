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
}
