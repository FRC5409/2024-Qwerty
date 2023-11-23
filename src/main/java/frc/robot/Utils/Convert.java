package frc.robot.Utils;

import java.util.Arrays;

public class Convert {
    /**
     * Converts a int array to a Long Array
     */
    public static long[] intToLong(int[] arr) {
        return Arrays.stream(arr).mapToLong(i -> i).toArray();
    }

    /**
     * Converts a Long array to a int Array
     */
    public static int[] longToInt(long[] arr) {
        return Arrays.stream(arr).mapToInt(i -> (int) i).toArray();
    }
}
