import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class Controller {
    private static Set<Room>  knownRooms;

    private Controller() {}

    static {
        knownRooms = new HashSet<>();
    }

    static void reset() {
        knownRooms = new HashSet<>();
    }

    static void addKnownRoom(Room room) {
        knownRooms.add(room);
        for(Connection connection: room.getConnections()) {
            Room adjRoom = connection.getDestination();
            knownRooms.add(adjRoom);
        }
    }

    static ArrayList<Room> findPath(Room currentRoom, Room goal) {
        return Utils.unscannedAStar(currentRoom, goal, knownRooms);
    }

    static void addFinishedRoom(Room room) {
        knownRooms.add(room);
    }

    static Collection<Room> getUnscannedRooms() {
        Set<Room> unscanned = new HashSet<>();

        for(Room room : knownRooms) {
            if(!room.isScanned()) {
                unscanned.add(room);
            }
        }

        return unscanned;
    }

    /*static Collection<Room> getKnownRooms() {
        return knownRooms;
    }*/

    /*public static Collection<Room> getScannedRooms() {
        Set<Room> scanned = new HashSet<>();

        for(Room room : knownRooms) {
            if(room.isScanned()) {
                scanned.add(room);
            }
        }

        return scanned;
    }*/
}
