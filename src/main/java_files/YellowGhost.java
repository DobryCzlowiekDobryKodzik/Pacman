package main.java_files;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class YellowGhost extends Ghost {
    

    public ImageIcon ghost = new ImageIcon("src\\main\\resources\\yellow_ghost.png");

    public YellowGhost(int x, int y, Pacman pacman, int[][] map) {
        super(x,y,pacman,map);
        iconPath = "src\\main\\resources\\yellow_ghost.png";
        nextDirection = 'w';
        moveDirection = 'w';
    }
    


    public void moveDirection() {
        if(!canChangeDirection) {
            return;
        }
        int destinationX;
        int destinationY;
        int currentX = x/GamePanel.TILE_SIZE;
        int currentY = y/GamePanel.TILE_SIZE;
        int pacmanX = pacman.getX()/GamePanel.TILE_SIZE;
        int pacmanY = pacman.getY()/GamePanel.TILE_SIZE;
        int currentDistance = Math.abs(currentX - pacmanX) + Math.abs(currentY - pacmanY);
        if(currentDistance <= 8){
            destinationX = initialX/GamePanel.TILE_SIZE;
            destinationY = initialY/GamePanel.TILE_SIZE;
        }else{
            destinationX = pacmanX;
            destinationY = pacmanY;
        }
        List<String> possibleDirections = new ArrayList<>();
        boolean isStuck = true;
        char oppositeDirection = ' ';

        if(moveDirection == 'e') oppositeDirection = 'w';
        if(moveDirection == 's') oppositeDirection = 'n';
        if(moveDirection == 'w') oppositeDirection = 'e';
        if(moveDirection == 'n') oppositeDirection = 's';

        if(map[currentY][currentX + 1] != 1) {
            possibleDirections.add("e");
            int distance = Math.abs(currentX + 1 - destinationX) + Math.abs(currentY - destinationY);
            if(currentDistance > distance) {
                currentDistance = distance;
                nextDirection = 'e';
                isStuck = false;
            }
        }

        if(map[currentY][currentX - 1] != 1) {
            possibleDirections.add("w");
            int distance = Math.abs(currentX - 1 - destinationX) + Math.abs(currentY - destinationY);
            if(currentDistance > distance) {
                currentDistance = distance;
                nextDirection = 'w';
                isStuck = false;
            }
        }

        if(map[currentY + 1][currentX] != 1) {
            possibleDirections.add("s");
            int distance = Math.abs(currentX - destinationX) + Math.abs(currentY + 1 - destinationY);
            if(currentDistance > distance) {
                currentDistance = distance;
                nextDirection = 's';
                isStuck = false;
            }
        }

        if(map[currentY - 1][currentX] != 1) {
            possibleDirections.add("n");
            int distance = Math.abs(currentX - destinationX) + Math.abs(currentY - 1 - destinationY);
            if(currentDistance > distance) {
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
        //System.out.println("możliwe kierunki: " + possibleDirections + "  nextDirection-> " + nextDirection + "  moveDirection->" + moveDirection + " desX->" + destinationX +" desY->"+ destinationY +" distance->" + currentDistance +"\n");

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