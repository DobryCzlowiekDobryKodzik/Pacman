package main.java_files;



class Character {
    static int DIAMETER;
    public char moveDirection;
    protected int x;
    protected int y;

    

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int newX){
        this.x = newX;
    }
    public void setY(int newY){
        this.y = newY;
    }
    
}