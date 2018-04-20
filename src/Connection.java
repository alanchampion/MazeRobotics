public class Connection {
    private Room room1, room2;
    Connection(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
    }

    private Room getOwner() {
        return room1;
    }

    Room getDestination() {
        return room2;
    }

    @Override
    public String toString() {
        return "Connection{" + room1.getName() + " to " + room2.getName() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection connection = (Connection) o;
        return ((room1 == connection.getOwner() && room2 == connection.getDestination()) ||
                (room2 == connection.getOwner() && room1 == connection.getDestination()));
    }
}
