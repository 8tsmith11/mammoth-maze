package main;

import java.awt.geom.Line2D;
import java.awt.Rectangle;
import java.util.*;

public class Mammoth {

    public double x, y; // tile position
    private Player player;
    private World world;
    private List<Node> path = new ArrayList<>();

    private double moveCooldown = 0;
    private final double MOVE_DELAY = 0.25; // seconds between path recalcs
    
    // target positions for interpolation
    private Double targetX = null, targetY = null;
    // Speed in tiles per second
    private double speed = 3.0; 

    private boolean active = true;
    private double respawnTimer = 0;
    private final double DESPAWN_DURATION = 10.0;
    private Random rand = new Random();

    public Mammoth(World world, Player player, double startX, double startY) {
        this.world = world;
        this.player = player;
        this.x = startX;
        this.y = startY;
    }

    public void update(double deltaTime, Line2D.Double[] rays) {
        if (!active) {
            respawnTimer -= deltaTime;
            if (respawnTimer <= 0) {
                active = true;
            }
            return;
        }

        if (!isVisibleToPlayer(rays)) {
            // 10% chance per second to despawn when hidden
            if (rand.nextDouble() < 0.1 * deltaTime) {
                active = false;
                respawnTimer = DESPAWN_DURATION;
                return;
            }
        }

        moveCooldown -= deltaTime;
        if (moveCooldown <= 0 && (targetX == null || targetY == null)) {
            int playerX = (int) player.getX();
            int playerY = (int) player.getY();
            path = findPath((int)x, (int)y, playerX, playerY);
            if (path != null && path.size() > 1) {
                Node next = path.get(1);
                targetX = (double)next.x;  
                targetY = (double)next.y;
            }
            moveCooldown = MOVE_DELAY;
        }
        
        if (targetX != null && targetY != null) {
            double dx = targetX - x;
            double dy = targetY - y;
            double dist = Math.hypot(dx, dy);
            if (dist < 0.05) { // target reached
                x = targetX;
                y = targetY;
                targetX = null;
                targetY = null;
            } else {
                double moveDist = Math.min(speed * deltaTime, dist);
                x += (dx / dist) * moveDist;
                y += (dy / dist) * moveDist;
            }
        }
    }

    private List<Node> findPath(int startX, int startY, int goalX, int goalY) {
        int[][] map = world.getMap();
        int rows = map.length, cols = map[0].length;

        PriorityQueue<Node> open = new PriorityQueue<>();
        Map<String, Node> allNodes = new HashMap<>();
        Set<String> closed = new HashSet<>();

        Node start = new Node(startX, startY, null, 0, heuristic(startX, startY, goalX, goalY));
        open.add(start);
        allNodes.put(start.key(), start);

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }

            closed.add(current.key());

            for (int[] dir : dirs) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];

                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || map[ny][nx] != 0) continue;

                String key = nx + "," + ny;
                if (closed.contains(key)) continue;

                double g = current.g + 1;
                double h = heuristic(nx, ny, goalX, goalY);

                Node neighbor = allNodes.getOrDefault(key, new Node(nx, ny));
                if (g < neighbor.g) {
                    neighbor.g = g;
                    neighbor.h = h;
                    neighbor.parent = current;
                    open.add(neighbor);
                    allNodes.put(key, neighbor);
                }
            }
        }

        return null; // No path found
    }

    private double heuristic(int x1, int y1, int x2, int y2) {
        return Math.hypot(x2 - x1, y2 - y1); // Euclidean distance
    }

    private List<Node> reconstructPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public boolean isVisibleToPlayer(Line2D.Double[] rays) {
        Rectangle mammothTile = new Rectangle((int)x, (int)y, 1, 1);
        for (Line2D.Double ray : rays) {
            if (mammothTile.intersectsLine(ray)) {
                return true;
            }
        }
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public boolean collidesWith(Player player) {
        return ((int) player.getX() == x && (int) player.getY() == y);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    // A* pathfinding node
    private static class Node implements Comparable<Node> {
        int x, y;
        Node parent;
        double g = Double.MAX_VALUE;
        double h = 0;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Node(int x, int y, Node parent, double g, double h) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.g = g;
            this.h = h;
        }

        String key() {
            return x + "," + y;
        }

        double f() {
            return g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.f(), other.f());
        }
    }

    // 4-direction movement
    private static final int[][] dirs = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };
}
