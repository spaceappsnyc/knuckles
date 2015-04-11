package me.valour.knucklescontrol.helpers;

/**
 * Created by alice on 4/11/15.
 */
public class Commander {

    public static int HOTTER = 2;
    public static int COLDER = -2;

    public static int recognize(String command){
        if(command.contains("hot") || command.contains("warm")){
            return HOTTER;
        }

        else if(command.contains("cold")){
            return COLDER;
        }

        else {
            return 0;
        }
    }

}
