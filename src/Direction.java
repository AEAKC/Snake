public enum Direction {
    UP (1),
    DOWN(-1),
    LEFT(2),
    RIGHT(-2);
    private int val;
    Direction(int val){
        this.val = val;
    }
    public int getVal(){
        return val;
    }
}
