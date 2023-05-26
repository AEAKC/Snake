abstract class BaseFruit {

    public int Score;
    protected Point position;

    String imagePath;
     public void Point_position(Point position){
        this.position = new Point(position);
    }

    public boolean checkCollision(){

        if(SnakeHead.getInstance().getPosition().equals(position)){
            return true;
        }else{
            return false;
        }

    }
}