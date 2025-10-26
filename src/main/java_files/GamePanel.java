package main.java_files;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 class GamePanel extends JPanel implements KeyListener , Runnable{

    ImageIcon youWinIcon = new ImageIcon("src\\main\\resources\\you_win.png");
    ImageIcon youLoseIcon = new ImageIcon("src\\main\\resources\\you_lose.png");
    boolean running = false;
    boolean isWin;
    //array represents map
    //0 - floor
    //1 - wall
    //2 - floor with dot
    //3 - big dot
    //4 - gate
   int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 1},
            {1, 2, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 2, 1},
            {1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {1, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 1},
            {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
            {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
  
    Pacman pacman = new Pacman(TILE_SIZE * 15,TILE_SIZE * 7, 'e');
    YellowGhost yellowGhost = new YellowGhost(TILE_SIZE * 30,TILE_SIZE * 1, pacman,map);
    RedGhost redGhost = new RedGhost(TILE_SIZE * 1, TILE_SIZE * 1, pacman, map);
    PurpleGhost purpleGhost = new PurpleGhost(TILE_SIZE * 30, TILE_SIZE * 13, pacman, map,redGhost);
    TurquiseGhost turquiseGhost = new TurquiseGhost(TILE_SIZE ,TILE_SIZE * 13, pacman, map);


    final static int TILE_FRAME_THICKNESS = 5; 
    final static int PAUSE_DURATION = 10;
    final static int SPEED = 2;
    final static int DIAMETER_PACMAN = 30;
    final static int TILE_SIZE = 40;
    final static int DOT_SIZE = TILE_SIZE / 5;
    final static int BIG_DOT_SIZE = TILE_SIZE / 2;

    GamePanel(){
        setBackground(Color.BLACK);
        setPreferredSize( new Dimension( map[0].length * TILE_SIZE, map.length * TILE_SIZE ) );
        addKeyListener(this);
        running = true;
        setFocusable(true);
        new Thread(this).start();
        

    }

    public boolean gameOver(){
       if(pacman.getX()/TILE_SIZE == redGhost.getX()/TILE_SIZE && pacman.getY()/TILE_SIZE == redGhost.getY()/TILE_SIZE){
        if(redGhost.mode == 2 || redGhost.isRespawning){
            redGhost.respawn();
            return false;
        }
        isWin = false;
        running = false;
        return true;
       }
       if(pacman.getX()/TILE_SIZE == purpleGhost.getX()/TILE_SIZE && pacman.getY()/TILE_SIZE == purpleGhost.getY()/TILE_SIZE){
            if(purpleGhost.mode == 2|| purpleGhost.isRespawning){
                purpleGhost.respawn();
                return false;
            }
           isWin = false;
           running = false;
            return true;
       }
       if(pacman.getX()/TILE_SIZE == yellowGhost.getX()/TILE_SIZE && pacman.getY()/TILE_SIZE == yellowGhost.getY()/TILE_SIZE){
            if(yellowGhost.mode == 2 || yellowGhost.isRespawning){
                yellowGhost.respawn();
                return false;
            }
            isWin = false;
            running = false;
           return true;
       }
       if(pacman.getX()/TILE_SIZE == turquiseGhost.getX()/TILE_SIZE && pacman.getY()/TILE_SIZE == turquiseGhost.getY()/TILE_SIZE){
            if(turquiseGhost.mode == 2|| turquiseGhost.isRespawning){
                turquiseGhost.respawn();
                return false;
            }
            isWin = false;
            running = false;
            return true;
       }
       for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length;j++){
                if(map[i][j] == 2 || map[i][j] == 3){
                    return false;
                }
            }
       }
       isWin = true;
       running = false;
       return true;
     
    }

    public void scaredGhostMovement(Ghost ghost){
        ghost.scaredGhostDirection();
        if(!checkTurn(ghost.nextDirection, ghost, Ghost.DIAMETER)){
            ghost.moveDirection = ghost.nextDirection;
        }
        if(!colision(ghost.moveDirection, ghost, Ghost.DIAMETER)) {
            if(ghost.moveDirection == 'e') {
                int x = ghost.getX();
                ghost.setX(x + SPEED);
            }
            if(ghost.moveDirection == 's') {
                int y = ghost.getY();
                ghost.setY(y + SPEED);
            }
            if(ghost.moveDirection == 'w') {
                int x = ghost.getX();
                ghost.setX(x - SPEED);
            }
            if(ghost.moveDirection == 'n') {
                int y = ghost.getY();
                ghost.setY(y - SPEED);
            }
    }
    }

    public void ghostMovement(Ghost ghost) {
        if(ghost.isRespawning){
            return;
        }
        ghost.moveDirection();
        if(!checkTurn(ghost.nextDirection, ghost, Ghost.DIAMETER)) {
            ghost.moveDirection = ghost.nextDirection;
        }
        if(!colision(ghost.moveDirection, ghost, Ghost.DIAMETER)) {
            if(ghost.moveDirection == 'e') {
                int x = ghost.getX();
                ghost.setX(x + SPEED);
            }
            if(ghost.moveDirection == 's') {
                int y = ghost.getY();
                ghost.setY(y + SPEED);
            }
            if(ghost.moveDirection == 'w') {
                int x = ghost.getX();
                ghost.setX(x - SPEED);
            }
            if(ghost.moveDirection == 'n') {
                int y = ghost.getY();
                ghost.setY(y - SPEED);
            }
        }
}

    public void collectDots(){
        int leftSide = pacman.getX();
        int upSide = pacman.getY();
        int rightSide = pacman.getX() + DIAMETER_PACMAN;
        int downSide = pacman.getY() + DIAMETER_PACMAN;

        boolean inXRange = false;
        boolean inYRange = false;

        int tileX = 0;
        int tileY = 0;

        for(int i = 0 ; i < map[0].length*TILE_SIZE; i++){
            if(i*TILE_SIZE+TILE_SIZE/2 > leftSide - DIAMETER_PACMAN/5 && i*TILE_SIZE+TILE_SIZE/2 < rightSide - DIAMETER_PACMAN/5){
                inXRange = true;
                tileX = i;
                break;
            }
        }
        for(int i = 0 ; i < map.length*TILE_SIZE; i++){
            if(i*TILE_SIZE+TILE_SIZE/2 > upSide - DIAMETER_PACMAN/5 && i*TILE_SIZE+TILE_SIZE/2 < downSide - DIAMETER_PACMAN/5){
                inYRange = true;
                tileY = i;
                break;
            }
        }
        if(inXRange && inYRange){
            if(map[tileY][tileX] == 3){
                redGhost.frightenedTimer();
                yellowGhost.frightenedTimer();
                purpleGhost.frightenedTimer();
                turquiseGhost.frightenedTimer();
            }
            map[tileY][tileX] = 0;
        }
    }

    public boolean colision(char direction, Character character, int size){
        int x = character.getX();
        int y = character.getY();
        switch (direction) {
            case 'e':
                if(x + size + SPEED > map[0].length*TILE_SIZE){
                    return true;
                }
                if(map[y/TILE_SIZE][(x + size + SPEED)/TILE_SIZE] == 1){
                    return true;
                }
                if(map[(y+size)/TILE_SIZE][(x + size + SPEED)/TILE_SIZE] == 1){
                    return true;
                }
                break;
            case 'w':
                if(x - SPEED < 0){
                    return true;
                }
                if(map[y/TILE_SIZE][(x - SPEED)/TILE_SIZE] == 1){
                    return true;
                }
                if(map[(y+size)/TILE_SIZE][(x - SPEED)/TILE_SIZE] == 1){
                    return true;
                }
                break;
                case 'n':
                    if(y - SPEED < 0){
                        return true;
                    }
                    if(map[(y-SPEED)/TILE_SIZE][x/TILE_SIZE] == 1){
                        return true;
                    }
                    if(map[(y-SPEED)/TILE_SIZE][(x + size)/TILE_SIZE] == 1){
                        return true;
                    }
                break;
                case 's':
                    if(y + SPEED + size > map.length*TILE_SIZE){
                        return true;
                    }
                    if(map[(y+size+SPEED)/TILE_SIZE][x/TILE_SIZE] == 1){
                        return true;
                    }
                    if(map[(y + size +SPEED)/TILE_SIZE][(x + size)/TILE_SIZE] == 1){
                        return true;
                    }
                break;
            
        }
        return false;

    }

    public boolean checkTurn(char direction, Character character, int size){
        int x = character.getX();
        int y = character.getY();
        switch (direction) {
            case 'e':
                if(map[(y+size)/TILE_SIZE][(x + size)/TILE_SIZE + 1] == 1){
                    return true;
                }
                if(map[y/TILE_SIZE][(x + size)/TILE_SIZE + 1] == 1){
                    return true;
                }
                break;
            case 'w':
                if(map[(y+size)/TILE_SIZE][x/TILE_SIZE - 1] == 1){
                    return true;
                }
                if(map[y/TILE_SIZE][x/TILE_SIZE - 1] == 1){
                    return true;
                }
            break;
            case 's':
                if(map[(y+size)/TILE_SIZE + 1][x/TILE_SIZE] == 1){
                    return true;
                }
                if(map[(y+size)/TILE_SIZE + 1][(x+size)/TILE_SIZE] == 1){
                    return true;
                }
            break;
            case 'n':
                if(map[y/TILE_SIZE - 1][x/TILE_SIZE] == 1){
                    return true;
                }
                if(map[y/TILE_SIZE - 1][(x+size)/TILE_SIZE] == 1){
                    return true;
                }
            break;
            default:
                break;
        }
        return false;
    }

    public void update(){
       if(!gameOver()){
            // pacman movement
            if(!checkTurn(pacman.nextDirection,pacman,Pacman.DIAMETER)){
                pacman.moveDirection = pacman.nextDirection;
                pacman.directionTimer.restart();;
            }
            if(!colision(pacman.moveDirection, pacman, Pacman.DIAMETER)){
                if(pacman.moveDirection == 'e'){
                    int x = pacman.getX();
                    pacman.setX(x + SPEED);
                }
                if(pacman.moveDirection == 's'){
                    int y = pacman.getY();
                    pacman.setY(y + SPEED);
                }
                if(pacman.moveDirection == 'w'){
                    int x = pacman.getX();
                    pacman.setX(x - SPEED);
                }
                if(pacman.moveDirection == 'n'){
                    int y = pacman.getY();
                    pacman.setY(y - SPEED);
                }
            }
            collectDots();
        }
        
        if(redGhost.mode == 1){
            ghostMovement(redGhost);
        }
        if(purpleGhost.mode == 1){
            ghostMovement(purpleGhost);
        }
        if(yellowGhost.mode == 1){
            ghostMovement(yellowGhost);
        }
        if(turquiseGhost.mode == 1){
            ghostMovement(turquiseGhost);
        }
        if(yellowGhost.mode == 2){
            scaredGhostMovement(yellowGhost);
        }
        if(redGhost.mode == 2){
            scaredGhostMovement(redGhost);
        }
        if(purpleGhost.mode == 2){
            scaredGhostMovement(purpleGhost);
        }
        if(turquiseGhost.mode == 2){
            scaredGhostMovement(turquiseGhost);
        }
        
        if(gameOver()){
            for(int i = 0; i < map.length; i++){
                for(int j = 0; j < map[0].length; j++){
                    map[i][j] = 0;
                }
            }
        }   
     }

    @Override
    public void run(){
        while(running){
            update();
            repaint();
            try{
                Thread.sleep(PAUSE_DURATION);
            }
            catch(Exception e){
                System.err.println("Katastrofa");
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                //drawing walls
                if(map[i][j] == 1){
                    g.setColor(Color.BLUE);
                    g.fillRect(j * TILE_SIZE + TILE_FRAME_THICKNESS, i * TILE_SIZE + TILE_FRAME_THICKNESS, TILE_SIZE - 2 * TILE_FRAME_THICKNESS, TILE_SIZE - 2 * TILE_FRAME_THICKNESS);
                }
                //drawing dots
                if(map[i][j] == 2){
                    g.setColor(Color.WHITE);
                    g.fillOval(j * TILE_SIZE + TILE_SIZE/2 - DOT_SIZE/2, i * TILE_SIZE + TILE_SIZE / 2 - DOT_SIZE/2, DOT_SIZE, DOT_SIZE);
                }
                //drawing big dots
                if(map[i][j] == 3){
                    g.setColor(Color.WHITE);
                    g.fillOval(j * TILE_SIZE + TILE_SIZE/2 - BIG_DOT_SIZE/2, i * TILE_SIZE + TILE_SIZE / 2 - BIG_DOT_SIZE/2, BIG_DOT_SIZE, BIG_DOT_SIZE);
                }
                // g.setColor(Color.MAGENTA);
                // g.drawRect(j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        if(!gameOver()){
            //draw pacman
            pacman.whichIcon().paintIcon(this, g, pacman.getX(),pacman.getY() );
            //drawing ghosts
            yellowGhost.ghost.paintIcon(this,g,yellowGhost.getX(),yellowGhost.getY());
            redGhost.ghost.paintIcon(this, g, redGhost.getX(), redGhost.getY());
            purpleGhost.ghost.paintIcon(this, g, purpleGhost.getX(), purpleGhost.getY());
            turquiseGhost.ghost.paintIcon(this, g, turquiseGhost.getX(), turquiseGhost.getY());
        }else{
            if(isWin){
                youWinIcon.paintIcon(this, g,TILE_SIZE,5*TILE_SIZE);
            }else{
                 youLoseIcon.paintIcon(this, g,TILE_SIZE,5*TILE_SIZE);
            }
        }
        

        
        
        

    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP){
            pacman.nextDirection = 'n';
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            pacman.nextDirection = 's';
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            pacman.nextDirection = 'e';
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            pacman.nextDirection = 'w';
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
    }
    @Override
    public void keyTyped(KeyEvent e){
    }

    
    public static void main(String[] args) {
        
    }
}
