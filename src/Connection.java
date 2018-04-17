public class Connection {
    Room room1, room2;
    Connection(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
    }

    @Override
    public String toString() {
        return "Connection{" + room1.name + " to " + room2.name + '}';
    }
}
