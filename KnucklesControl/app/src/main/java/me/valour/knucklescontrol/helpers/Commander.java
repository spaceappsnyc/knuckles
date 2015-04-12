package me.valour.knucklescontrol.helpers;

/**
 * Created by alice on 4/11/15.
 */

import android.util.Log;
import org.apache.commons.codec.language.Soundex;

public class Commander {

    public static final int HOTTER = 1;
    public static final int COLDER = -1;
    public static final int LIGHTS = 2;

    public static int recognize(String command){
        Soundex soundex = new Soundex();
        String encoded = soundex.encode(command);

        Log.d("Soundex",encoded);

        if(command.contains("hot") || command.contains("warm")){
            return HOTTER;
        }
        else if(command.contains("cold")) {
            return COLDER;
        }
        else if(command.contains("light") || command.contains("ite")) {
            return LIGHTS;
        }

        else {
            return 0;
        }
    }

}
