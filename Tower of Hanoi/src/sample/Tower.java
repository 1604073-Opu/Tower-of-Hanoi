package sample;

public class Tower {
    double defaultX, defaultY;
    double towerX;
    double towerY;
    int[] stack = new int[10];
    int size = 0;

    public Tower(int towerNo) {
        if (towerNo == 1) towerNo = 0;
        else if (towerNo == 2) towerNo = 210;
        else if (towerNo == 3) towerNo = 425;
        towerX = 200 + towerNo;
        towerY = 463;
        defaultX = towerX;
        defaultY = towerY;
    }

    public double X() {
        return towerX;
    }

    public double Y() {
        return towerY;
    }

    public void add(int x) {
        size++;
        towerX = defaultX + x * 7;
        towerY = defaultY-size*20;
        stack[size] = x;
    }

    public void remove(int x) {
        if (stack[size] == x) {
            size--;
        }
    }

    public Boolean finished(int disk) {
        if(size==disk)return true;
        else return false;
    }

    public int lastDisc() {
        return stack[size];
    }
}
