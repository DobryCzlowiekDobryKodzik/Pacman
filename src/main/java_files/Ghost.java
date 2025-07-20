package main.java_files;

import javax.swing.*;
import javax.swing.Timer;

import java.util.*;
import java.awt.event.*;

public abstract class Ghost extends Character {

    protected static final int DIRECTION_CHANGE_DELAY = 400;
    protected static final int FRIGHTENED_TIME = 5000;
    protected static final int RESPAWN_DELAY = 3000;
    


    static {
        DIAMETER = 30;
    }
    public String iconPath;
    public ImageIcon scaredGhost = new ImageIcon("src\\main\\resources\\blue_ghost.png");
    public ImageIcon ghost;
    protected Pacman pacman;
    int[][] map;
    public int initialX,initialY;
    public int mode = 1;//if ghost is not scared mode = 1
    public boolean isRespawning = false;
    protected javax.swing.Timer respawnTimer;
    

    char nextDirection;
    protected javax.swing.Timer directionTimer;
    protected boolean canChangeDirection = false;


    public Ghost(int x, int y, Pacman pacman, int[][] map) {
        this.map = map;
        this.pacman = pacman;
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        initDirectionTimer();
    }
    void respawn(){
        x = initialX;
        y = initialY;
        mode = 1;
        ghost = new ImageIcon(iconPath);
        moveDirection = 'e';
        this.isRespawning = true;
        
        respawnTimer = new Timer(RESPAWN_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRespawning = false;
                respawnTimer.stop();
            }
        });
        respawnTimer.setRepeats(false);
        respawnTimer.start();
    }

    public void initDirectionTimer() {
        directionTimer = new javax.swing.Timer(DIRECTION_CHANGE_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canChangeDirection = true;
            }
        });
        directionTimer.start();
    }
     

    public void scaredGhostDirection(){
        int currentX = x/GamePanel.TILE_SIZE;
        int currentY = y/GamePanel.TILE_SIZE;
        List<String> foundBetterDirection = new ArrayList<>();
        char oppositeDirection = ' ';

        if(moveDirection == 'e') oppositeDirection = 'w';
        if(moveDirection == 's') oppositeDirection = 'n';
        if(moveDirection == 'w') oppositeDirection = 'e';
        if(moveDirection == 'n') oppositeDirection = 's';

        if(map[currentY][currentX + 1] != 1) {
            foundBetterDirection.add("e");
        }

        if(map[currentY][currentX - 1] != 1) {
            foundBetterDirection.add("w");
        }

        if(map[currentY + 1][currentX] != 1) {
            foundBetterDirection.add("s");
        }

        if(map[currentY - 1][currentX] != 1) {
            foundBetterDirection.add("n");
        }



        
        Random rand = new Random();
        while(true){
            int randomIndex = rand.nextInt(foundBetterDirection.size());
            nextDirection = foundBetterDirection.get(randomIndex).charAt(0);
            if(nextDirection != oppositeDirection || foundBetterDirection.size() == 1){
                break;
            }
        }
    }


    public abstract void moveDirection();
}