package main;

public class Player {
    
    //Position in the map

    public double x, y;

    // Movement speed
    public final double MOVE_SPEED = 3.0; // tiles per second
    public final double ROTATE_SPEED = 2.0; // radians per second

    // long lastTime = System.nanoTime();
    // double deltaTime;

    // while (running) {
    //     long now = System.nanoTime();
    //     deltaTime = (now - lastTime) / 1_000_000_000.0; // convert to seconds
    //     lastTime = now;
    
    //     // Pass deltaTime to player movement
    //     handleInput(deltaTime);
    //     updateGame(deltaTime);
    //     render();
    // }
    

    public Player(){
        this.x = 1.5;
        this.y = 1.5;
    }

    public void moveForward(){
        //code
    }

    public void moveBackward(){
        //code
    }

    public void moveLeft(){
        //code
    }

    public void moveRight(){
        //code
    }


}
