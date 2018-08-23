package com.example.pc.mymaze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class mazeView {

    /**
     * 0 左 2 右
     * 1 上 3 下
     */
    public static int LEFT = 0, RIGHT = 2, UP = 1, DOWN = 3;

    static unionFindSets set;//并查集

    private int arrayNum;//迷宫是以一维数组组成的，这是数组长度
    int sideNum;
    int uniteLength;
    static int maxNumber = 64;
    room [][]maze;//带有地图信息的二维数组
    public mazeView(int sNum, int wPixels){
        sideNum = sNum;
        arrayNum = sNum*sNum;
        uniteLength = (int) Math.floor((wPixels + 122) /sideNum);
        set = new unionFindSets(arrayNum);
        maze = new room[sideNum][sideNum];
        for(int i = 0;i<sideNum;i++)
            for(int j = 0;j<sideNum;j++) {
                maze[i][j] = new room();
                maze[i][j].x = i;
                maze[i][j].y = j;
                maze[i][j].visited = false;
                maze[i][j].fx = -1;
                maze[i][j].fy = -1;
            }
        //以下是寻路内容
    }

    public void creatMaze(){
        Random random1 = new Random();//默认是以1970年到现在的毫秒数作为随机种子
        Random random2 = new Random();

        while(!set.allConnected()){
            int index = random1.nextInt(arrayNum);
            int direct = random2.nextInt(4);
            int adjacentIndex = -1;

            int row = index / sideNum;
            int col = index % sideNum;
            int row1 = 0,col1 = 0;
            switch(direct){
                case 1://左
                    if(col != 0) {
                        row1 = row;
                        col1 = col - 1;
                        adjacentIndex = row1 * sideNum + col1;
                    }
                    break;
                case 0://上
                    if(row != 0){
                        row1 = row - 1;
                        col1 = col;
                        adjacentIndex = row1 * sideNum + col1;
                    }
                    break;
                case 3://右
                    if(col != sideNum - 1) {
                        row1 = row;
                        col1 = col + 1;
                        adjacentIndex = row1 * sideNum + col1;
                    }
                    break;
                case 2://下
                    if(row != sideNum - 1){
                        row1 = row + 1;
                        col1 = col;
                        adjacentIndex = row1 * sideNum + col1;
                    }
                    break;
                default:
                    break;
            }
            if(adjacentIndex == -1 || set.isConnected(index,adjacentIndex))continue;
            set.union(index,adjacentIndex);
            int adjacentDirect = (direct + 2) % 4;
            maze[row][col].side[direct] = maze[row1][col1].side[adjacentDirect] = false;
        }
    }

    Stack<room> path = new Stack<>();

    public void findPath(){
        Queue<room> Q = new LinkedList<>();
        Q.add(maze[0][0]);
        maze[0][0].visited = true;

        while(!Q.isEmpty()){
            room test = Q.element();
            maze[test.x][test.y].visited = true;
            Q.remove();
            int tx = 0;
            int ty = 0;
            if(test.x == sideNum -1 && test.y == sideNum - 1)
                break;
            for(int i = 0; i<4; i++){
                if(test.side[i])continue;
                switch (i){
                    case 0:
                        tx = test.x - 1;
                        ty = test.y;
                        break;
                    case 1:
                        tx = test.x;
                        ty = test.y - 1;
                        break;
                    case 2:
                        tx = test.x + 1;
                        ty = test.y;
                        break;
                    case 3:
                        tx = test.x ;
                        ty = test.y + 1;
                        break;
                    default:
                        break;
                }
                if(tx<0||tx>=sideNum||ty<0||ty>=sideNum)continue;
                if(maze[tx][ty].visited)continue;
                maze[tx][ty].visited = true;
                maze[tx][ty].fx = test.x;
                maze[tx][ty].fy = test.y;
                Q.add(maze[tx][ty]);
            }
        }
        int sx = sideNum - 1,sy = sideNum - 1;
        while(!(sx==0&&sy==0)){
            path.push(maze[sx][sy]);
            sx = path.peek().fx;
            sy = path.peek().fy;
        }
    }
}
