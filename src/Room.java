import java.util.ArrayList;
import java.util.Objects;

public class Room {
    String name;
    long size;
    private ArrayList<Hallway> hallways;
    Room(String name, long footage) {
        this.name = name;
        this.size = footage;
        hallways = new ArrayList<>();
    }

    public void addConnection(Room nextRoom, long length) {
        Hallway hallway = new Hallway(length, this, nextRoom);
        hallways.add(hallway);
        nextRoom.addConnection(new Hallway(length, nextRoom, this));
    }

    public void addConnection(Hallway hallway) {
        hallways.add(hallway);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", hallways=" + hallways +
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
