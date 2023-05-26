abstract class BaseFruit {
    public int score;
    private Point position;
    String imagePath;
     public void Point_position(Point position){
        this.position = new Point(position);
    }

    public boolean checkCollision(){
         return true;
    }
}