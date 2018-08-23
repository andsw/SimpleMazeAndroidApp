package com.example.pc.mymaze;

/**
 * Created by PC on 2017/9/28.
 */

class unionFindSets {

    int len;

    private int parent[];

    unionFindSets(int num){
        len = num;
        parent = new int[num];
        for(int i = 0;i < len; i++){
            parent[i] = i;
        }
    }

    private int find(int i){
        if(parent[i] == i)
            return i;
        parent[i] = find(parent[i]);
        return parent[i];
    }

    void union(int i, int j){
        int a = find(i);
        int b = find(j);
        if(a != b)//刚刚修改
            parent[a] = b;
    }

    public boolean isConnected(int i, int j){
        return find(i) == find(j);
    }

    public boolean allConnected(){
        int parNum = 1;
        int oriPar = find(0);
        for(int i = 1;i < len; i++){
            if(find(i) != oriPar)parNum++;
        }
        if(parNum == 1)
            return true;
        return false;
    }

    public boolean canArive(){
        return find(0) == find(len - 1);
    }
}
