package main.java_files;

import javax.swing.*;
import java.awt.event.*;

class Pacman extends Character{
    static{
        DIAMETER = 31;
    }
    public ImageIcon tupac_up = new ImageIcon("src\\main\\resources\\pacman_up.png");
    public ImageIcon tupac_down = new ImageIcon("src\\main\\resources\\pacman_down.png");
    public ImageIcon tupac_right = new ImageIcon("src\\main\\resources\\pacman_right.png");
    public ImageIcon tupac_left = new ImageIcon("src\\main\\resources\\pacman_left.png");
    
    public char nextDirection = 'e';

    public Timer directionTimer;
    
    public void initDirectionTimer() {
        directionTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextDirection = moveDirection;
            }
        });
        directionTimer.start();
    }

    Pacman(int x, int y , char moveDirection){
        this.moveDirection = moveDirection;
        this.x = x;
        this.y = y;
        initDirectionTimer();
    }
    ImageIcon whichIcon(){
        if(moveDirection == 'e'){
            return tupac_right;
        }
        if(moveDirection == 's'){
            return tupac_down;
        }
        if(moveDirection == 'w'){
            return tupac_left;
        }
        if(moveDirection == 'n'){
            return tupac_up;
        }
        return null;
    }
    public static void main(String[] args) {
        
    }
}