package com.example.pc.mymaze;

/**
 * Created by PC on 2017/9/28.
 */

public class room {

    public boolean side[] = new boolean[4];


    int x, y;
    int fx, fy;
    boolean visited;

    public room() {
        for (int i = 0; i < 4; i++)
            side[i] = true;
    }
}
