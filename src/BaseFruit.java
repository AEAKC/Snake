abstract class BaseFruit {

    public int score;
    protected Point position;

    String imagePath;
     public void Point_position(Point position){
        this.position = new Point(position);
    }

    public Point getPosition() {
        return position;
    }

    public boolean checkCollision(){

        if(SnakeHead.getInstance().getPosition().equals(position)){
            return true;
        }else{
            return false;
        }

    }
}