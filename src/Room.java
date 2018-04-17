import java.util.ArrayList;
import java.util.Objects;

public class Room {
    String name;
    long size;
    private ArrayList<Connection> connections;
    long scanned;

    Room(String name, long footage) {
        this.name = name;
        this.size = footage;
        connections = new ArrayList<>();
        scanned = 0;
    }

    public void addConnection(Room nextRoom) {
        Connection connection = new Connection(this, nextRoom);
        this.connections.add(connection);
        nextRoom.addConnection(new Connection(nextRoom, this));
    }

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", connections=" + connections +
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
