import java.util.*;

class Utils {
    static ArrayList<Room> aStar(Room start, Room goal, Collection<Room> allRooms) {
        PriorityQueue<Room> openSet = new PriorityQueue<>(1, new RoomComparator());
        ArrayList<Room> closedSet = new ArrayList<>();
        Map<Room, Room> cameFrom = new HashMap<>();

        for(Room room : allRooms) {
            room.setfScore(Long.MAX_VALUE);
            room.setgScore(Long.MAX_VALUE);
        }
        start.setfScore(heuristicCalculation(start, goal));
        start.setgScore(0L);
        openSet.add(start);

        while(!openSet.isEmpty()) {
            Room current = openSet.remove();
            if(current == goal)
                return reconstructPath(cameFrom, current);

            openSet.remove(current);
            closedSet.add(current);

            for(Connection connection : current.getConnections()) {
                Room neighbor = connection.getDestination();
                if(!neighbor.isScanned()) continue;
                if(closedSet.contains(neighbor)) continue;

                if(!openSet.contains(neighbor)) openSet.add(neighbor);

                long tentativeGScore = current.getgScore() + 1;
                if(tentativeGScore >= neighbor.getgScore()) continue;

                cameFrom.put(neighbor, current);
                neighbor.setgScore(tentativeGScore);
                neighbor.setfScore(neighbor.getgScore() + heuristicCalculation(neighbor, goal));
            }
        }

        return new ArrayList<>();
    }

    static ArrayList<Room> unscannedAStar(Room start, Room goal, Collection<Room> allRooms) {
        PriorityQueue<Room> openSet = new PriorityQueue<>(1, new RoomComparator());
        ArrayList<Room> closedSet = new ArrayList<>();
        Map<Room, Room> cameFrom = new HashMap<>();

        for(Room room : allRooms) {
            room.setfScore(Long.MAX_VALUE);
            room.setgScore(Long.MAX_VALUE);
        }
        start.setfScore(heuristicCalculation(start, goal));
        start.setgScore(0L);
        openSet.add(start);

        while(!openSet.isEmpty()) {
            Room current = openSet.remove();
            if(current == goal)
                return reconstructPath(cameFrom, current);

            openSet.remove(current);
            closedSet.add(current);

            for(Connection connection : current.getConnections()) {
                Room neighbor = connection.getDestination();
                if(closedSet.contains(neighbor)) continue;

                if(!openSet.contains(neighbor)) openSet.add(neighbor);

                long tentativeGScore = current.getgScore() + 1;
                if(tentativeGScore >= neighbor.getgScore()) continue;

                cameFrom.put(neighbor, current);
                neighbor.setgScore(tentativeGScore);
                neighbor.setfScore(neighbor.getgScore() + heuristicCalculation(neighbor, goal));
            }
        }

        return new ArrayList<>();
    }

    private static long heuristicCalculation(Room start, Room goal) {
        if(start.getConnections().contains(new Connection(start, goal))) return 0;
        else return 1;
    }

    private static ArrayList<Room> reconstructPath(Map<Room, Room> cameFrom, Room current) {
        ArrayList<Room> path = new ArrayList<>();
        path.add(current);
        while(cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }
        return path;
    }

    private static class RoomComparator implements Comparator<Room> {

        @Override
        public int compare(Room o1, Room o2) {
            if(o1.getfScore() == o2.getfScore()) return 0;
            return o1.getfScore() < o2.getfScore() ? -1 : 1;
        }
    }

    /*static ArrayList<Room> dfs(Room currentRoom, Room goalRoom, ArrayList<Room> path) {
        // System.out.println("Running dfs on " + currentRoom.getName());

        if(currentRoom == goalRoom) {
            return path;
        }

        for(Connection connection : currentRoom.getConnections()) {
            Room adjRoom = connection.getDestination();
            if(!adjRoom.isScanned()){
                continue;
            }

            if(adjRoom.notVisited()) {
                path.add(adjRoom);
                if (!dfs(adjRoom, goalRoom, path).isEmpty()) {
                    return path;
                }
                path.remove(adjRoom);
            }
        }

        // currentRoom.setVisited(false);
        return new ArrayList<>();
    }*/
}
