package frc.robot.Utils;

import java.util.HashMap;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class NT4 {

    private static HashMap<String, ShuffleboardTab> instance = null;

    public static ShuffleboardTab getInstance(String table) {
        if (instance == null) instance = new HashMap<String, ShuffleboardTab>();
        if (!instance.containsKey(table)) instance.put(table, Shuffleboard.getTab(table));
        
        return instance.get(table);
    }

}
