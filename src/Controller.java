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
        for(Room room: knownRooms) {
            room.setVisited(false);
        }
        ArrayList<Room> path = new ArrayList<>();
        path.add(currentRoom);
        return dfs(currentRoom, goal, path);
    }

    private static ArrayList<Room> dfs(Room currentRoom, Room goalRoom, ArrayList<Room> path) {
        /*System.out.println(currentRoom);
        System.out.println(goalRoom);
        System.out.println("");*/
        currentRoom.setVisited(true);

        if(currentRoom == goalRoom) {
            // System.out.println("Found path!");
            return path;
        }

        for(Connection connection : currentRoom.getConnections()) {
            Room adjRoom = connection.getDestination();

            if(adjRoom.notVisited()) {
                path.add(adjRoom);
                if (!dfs(adjRoom, goalRoom, path).isEmpty()) {
                    // System.out.println("Returning found path.");
                    return path;
                }
                path.remove(adjRoom);
            }
        }

        // currentRoom.setVisited(false);
        /*System.out.print("Didn't find path: ");
        for(Room room : path) {
            System.out.print(room.getName() + ", ");
        }
        System.out.println("");*/
        return new ArrayList<>();
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
