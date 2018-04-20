import java.util.ArrayList;
import java.util.Objects;

public class Room {
    private String name;
    private long size;
    private ArrayList<Connection> connections;
    private long scanned, fScore, gScore;
    // private boolean visited;

    Room(String name, long footage) {
        this.name = name;
        this.size = footage;
        connections = new ArrayList<>();
        scanned = 0;
        // visited = false;
        fScore = Long.MAX_VALUE;
        gScore = Long.MAX_VALUE;
    }

    void addConnection(Room nextRoom) {
        Connection connection = new Connection(this, nextRoom);
        this.connections.add(connection);
        nextRoom.addConnection(new Connection(nextRoom, this));
    }

    private void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    String getName() {
        return name;
    }

    /*public long getScanned() {
        return scanned;
    }*/

    ArrayList<Connection> getConnections() {
        return connections;
    }

    void addScan(long amount) {
        scanned += amount;
        if(scanned >= size) {
            scanned = size;
            Controller.addFinishedRoom(this);
        }
    }

    boolean isScanned() {
        return scanned >= size;
    }

    /*void setVisited(boolean visited) {
        this.visited = visited;
    }

    boolean notVisited() {
        return !visited;
    }*/

    long getfScore() {
        return fScore;
    }

    void setfScore(long fScore) {
        this.fScore = fScore;
    }

    long getgScore() {
        return gScore;
    }

    void setgScore(long gScore) {
        this.gScore = gScore;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", connections=" + connections +
                ", scanned=" + scanned +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
