package main.java_files;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class RedGhost extends Ghost {
    

    public ImageIcon ghost = new ImageIcon("src\\main\\resources\\red_ghost.png");

    public RedGhost(int x, int y, Pacman pacman, int[][] map) {
        super(x,y,pacman,map);
        iconPath = "src\\main\\resources\\red_ghost.png";
        moveDirection = 'e';
        nextDirection = 'e';
    }
    

    public void moveDirection() {

        if(!canChangeDirection) {
            return;
        }

        int currentX = x/GamePanel.TILE_SIZE;
        int currentY = y/GamePanel.TILE_SIZE;
        int pacmanX = pacman.getX()/GamePanel.TILE_SIZE;
        int pacmanY = pacman.getY()/GamePanel.TILE_SIZE;
        int currentDistance = Math.abs(currentX - pacmanX) + Math.abs(currentY - pacmanY);
        List<String> possibleDirections = new ArrayList<>();
        boolean isStuck = true;
        char oppositeDirection = ' ';

        if(moveDirection == 'e') oppositeDirection = 'w';
        if(moveDirection == 's') oppositeDirection = 'n';
        if(moveDirection == 'w') oppositeDirection = 'e';
        if(moveDirection == 'n') oppositeDirection = 's';

        if(map[currentY][currentX + 1] != 1) {
            possibleDirections.add("e");
            int distance = Math.abs(currentX + 1 - pacmanX) + Math.abs(currentY - pacmanY);
            if(currentDistance > distance && 'e' != oppositeDirection) {
                currentDistance = distance;
                nextDirection = 'e';
                isStuck = false;
            }
        }

        if(map[currentY][currentX - 1] != 1) {
            possibleDirections.add("w");
            int distance = Math.abs(currentX - 1 - pacmanX) + Math.abs(currentY - pacmanY);
            if(currentDistance > distance && 'w' != oppositeDirection) {
                currentDistance = distance;
                nextDirection = 'w';
                isStuck = false;
            }
        }

        if(map[currentY + 1][currentX] != 1) {
            possibleDirections.add("s");
            int distance = Math.abs(currentX - pacmanX) + Math.abs(currentY + 1 - pacmanY);
            if(currentDistance > distance && 's' != oppositeDirection) {
                currentDistance = distance;
                nextDirection = 's';
                isStuck = false;
            }
        }

        if(map[currentY - 1][currentX] != 1) {
            possibleDirections.add("n");
            int distance = Math.abs(currentX - pacmanX) + Math.abs(currentY - 1 - pacmanY);
            if(currentDistance > distance && 'n' != oppositeDirection) {
                currentDistance = distance;
                nextDirection = 'n';
                isStuck = false;
            }
        }



        if(isStuck) {
            Random rand = new Random();
            while(true){
                int randomIndex = rand.nextInt(possibleDirections.size());
                nextDirection = possibleDirections.get(randomIndex).charAt(0);
                if(nextDirection != oppositeDirection || possibleDirections.size() == 1){
                    break;
                }
            }
            
        }
        canChangeDirection = false;
        directionTimer.restart();
    }

    public javax.swing.Timer frightenedTimer;

    public void frightenedTimer() {
        ghost = scaredGhost;
        mode = 2;

        frightenedTimer = new javax.swing.Timer(FRIGHTENED_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ghost = new ImageIcon(iconPath);
                mode = 1;
                frightenedTimer.stop();
            }
        });

        frightenedTimer.setRepeats(false);
        frightenedTimer.start();


    }
}