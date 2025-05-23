package main;

public class Player {
    
    //Position in the map
    public double x, y;
    // Direction vector (facing)
    public double dirX, dirY;
    // Plane vector (camera plane - controls FOV)
    public double planeX, planeY;
    // Movement speed
    public static final double MOVE_SPEED = 2.0; // tiles per second
    public static final double ROTATE_SPEED = 4.0; // radians per second
    public static final double SIZE = 0.5; // size in tiles

    private World world;

    public Player(World world) {

        this.world = world;

        this.x = 1.5;
        this.y = 1.5;

        // Facing down right
        this.dirX = Math.sqrt(2) / 2;
        this.dirY = Math.sqrt(2) / 2;

        // 2D camera plane for FOV (~66 degrees)
        this.planeX = 0.66;
        this.planeY = 0;

    }

    // Movement

    public void moveForward(double deltaTime) {
        double step = MOVE_SPEED * deltaTime;
        if (isWalkable(x + dirX * step, y)) x += dirX * step;
        if (isWalkable(x, y + dirY * step)) y += dirY * step;
    }

    public void moveBackward(double deltaTime) {
        double step = MOVE_SPEED * deltaTime;
        if (isWalkable(x - dirX * step, y)) x -= dirX * step;
        if (isWalkable(x, y - dirY * step)) y -= dirY * step;
    }

    // Rotation
    public void rotateLeft(double deltaTime) {
        rotate(-ROTATE_SPEED * deltaTime);
    }

    public void rotateRight(double deltaTime) {
        rotate(ROTATE_SPEED * deltaTime);
    }

    private void rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double oldDirX = dirX;
        dirX = dirX * cos - dirY * sin;
        dirY = oldDirX * sin + dirY * cos;

        double oldPlaneX = planeX;
        planeX = planeX * cos - planeY * sin;
        planeY = oldPlaneX * sin + planeY * cos;
    }


    private boolean isWalkable(double nextX, double nextY) {
        int[][] map = world.getMap();
        int tileX = (int) Math.floor(nextX);
        int tileY = (int) Math.floor(nextY);

        if (tileX < 0 || tileY < 0 || tileX >= map.length || tileY >= map[0].length) {
            return false;
        }
        return map[tileY][tileX] != 1;
    }

    // Return angle in radians
    public double getAngle() {
    	return Math.atan2(dirY, dirX);
    }


    public double getX() { return x; }
    public double getY() { return y; }
    public double getDirX() { return dirX; }
    public double getDirY() { return dirY; }



}
