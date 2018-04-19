import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Controller {
    private static Controller controller;
    private static Set<Room>  knownRooms;

    private Controller() {}

    static {
        knownRooms = new HashSet<>();
        controller = new Controller();
    }

    static void reset() {
        knownRooms = new HashSet<>();
        controller = new Controller();
    }

    static Controller getController() {
        return controller;
    }

    static void addKnownRoom(Room room) {
        knownRooms.add(room);
        for(Connection connection: room.getConnections()) {
            Room adjRoom = connection.getDestination();
            knownRooms.add(adjRoom);
        }
    }

    // TODO make this work
    static ArrayList<Room> findPath(Room currentRoom, Room goal) {
        /*System.out.println("Trying to find path from " + currentRoom.getName() + " to " + goal.getName());
        System.out.println("Known rooms:");
        for(Room room : knownRooms) {
            System.out.println(room.getName());
            room.setVisited(false);
        }*/

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

            if(!adjRoom.isVisited()) {
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

    public static Collection<Room> getKnownRooms() {
        return knownRooms;
    }

    public static Collection<Room> getScannedRooms() {
        Set<Room> scanned = new HashSet<>();

        for(Room room : knownRooms) {
            if(room.isScanned()) {
                scanned.add(room);
            }
        }

        return scanned;
    }
}
