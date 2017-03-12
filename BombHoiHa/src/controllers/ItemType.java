package controllers;

import java.util.Random;

/**
 * Created by QuanT on 3/12/2017.
 */
public enum ItemType {
    SPEED_UP,
    EXPAND_EXPLOSIVE,
    EXPAND_BOMB,
    FREEZE;
  //  THROUGH_WALL,
   // POWERUP_BOMB;

    private static final ItemType[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    public static ItemType getRandomItemType()  {
        return VALUES[RANDOM.nextInt(SIZE)];
    }

}
