public class Hallway {
    Room room1, room2;
    long length;
    Hallway(long length, Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Hallway{" + room1.name + " to " + room2.name +
                ", " + length +
                '}';
    }
}
