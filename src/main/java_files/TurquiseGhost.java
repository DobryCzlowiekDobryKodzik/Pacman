package main.java_files;

import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class TurquiseGhost extends Ghost {
    
    public ImageIcon ghost = new ImageIcon("src\\main\\resources\\turquise_ghost.png");

    public TurquiseGhost(int x, int y, Pacman pacman, int[][] map) {
        super(x,y,pacman,map);
        iconPath = "src\\main\\resources\\turquise_ghost.png";
        moveDirection = 'e';
        nextDirection = 'e';
    }
    

    int[] adjustVectorLength(int length){
        int[] coordinates = new int[2];
        int startX = pacman.getX()/GamePanel.TILE_SIZE;
        int startY = pacman.getY()/GamePanel.TILE_SIZE;
        switch (pacman.moveDirection) {
            case 'e':
                while(length >= 0){
                    if(startX + length >= map[0].length){
                        length--;
                        continue;
                    }
                    if(map[startY][startX + length] == 1){//1 means wall
                        length--;
                        continue;
                    }
                    break;
                }
                coordinates[1] = startX + length;
                coordinates[0] = startY;
            break;
            case 's':
                while(length >= 0){
                    if(startY + length >= map.length){
                        length--;
                        continue;
                    }
                    if(map[startY + length][startX] == 1){
                        length--;
                        continue;
                    }
                    break;
                }
                coordinates[1] = startX;
                coordinates[0] = startY + length;
            break;
            case 'w':
                while(length >= 0){
                    if(startX - length < 0){
                        length--;
                        continue;
                    }
                    if(map[startY][startX - length] == 1){
                        length--;
                        continue;
                    }
                    break;
                }
                coordinates[1] = startX - length;
                coordinates[0] = startY;
            break;
            case 'n':
                while(length >= 0){
                    if(startY - length < 0){
                        length--;
                        continue;
                    }
                    if(map[startY - length][startX] == 1){
                        length--;
                        continue;
                    }
                    break;
                }
                coordinates[1] = startX;
                coordinates[0] = startY - length;
            break;
            default:
                break;
            }
            return coordinates;
    }


    
    public void moveDirection() {
        if(!canChangeDirection) {
            return;
        }

        int currentX = x/GamePanel.TILE_SIZE;
        int currentY = y/GamePanel.TILE_SIZE;
        
        int[] coordinates = new int[2];
        coordinates = adjustVectorLength(4);
        int destinationY = coordinates[0];
        int destinationX = coordinates[1];
        

        int currentDistance = Math.abs(currentX - destinationX) + Math.abs(currentY - destinationY);

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