import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class MapLayout {
    private Scanner scanner;
    private ArrayList<Room> rooms;
    private ArrayList<Robot> robots;
    private ArrayList<Room> solution;
    private long robotScan;
    private Room[] goals;
    private Random rgen;

    /*MapLayout(String fileName) {
        this(fileName, 10);
    }*/

    /*MapLayout(String fileName, long width) {
        this(fileName, width, 10);
    }*/

    MapLayout(String fileName, long hallwayWidth, long scanAmount) {
        {
            robots = new ArrayList<>();
            robotScan = scanAmount;
            goals = new Room[2];
            rooms = new ArrayList<>();
            solution = new ArrayList<>();
            Controller.reset();

            long rgenseed = System.currentTimeMillis();
            rgen = new Random();
            // rgenseed = 1524127082862L;
            rgen.setSeed(rgenseed);
            // System.out.println("Seed: " + rgenseed);

            try {
                scanner = new Scanner(new File(fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }

            ArrayList<String> names = new ArrayList<>();
            ArrayList<Long> squareFootage = new ArrayList<>();
            ArrayList<String> hallways = new ArrayList<>();
            String line;

            // Get all the rooms
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                if(line.equals("") || line.equals(" ")) {
                    break;
                } else {
                    String[] room = line.split("\\s+");
                    names.add(room[0]);
                    squareFootage.add(Long.parseLong(room[1]));
                }
            }
            for(int i = 0; i < names.size(); i++) {
                rooms.add(new Room(names.get(i), squareFootage.get(i)));
            }

            // Get all the hallways
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                if(line.equals("") || line.equals(" ")) {
                    break;
                } else {
                    hallways.add(line);
                }
            }
            for(String hallway : hallways) {
                String[] splitHallway = hallway.split("\\s+");
                for (int i = 0; i < splitHallway.length-1; i = i+2) {
                    if(!splitHallway[i+1].equals("0")) {
                        String hallwayName = "Hallway_" + splitHallway[i] + "_" + splitHallway[i + 2];
                        Long hallwayLength = Long.parseLong(splitHallway[i + 1]);
                        Room newHallway = new Room(hallwayName, hallwayLength * hallwayWidth);
                        rooms.add(newHallway);
                        getRoom(splitHallway[i]).addConnection(newHallway);
                        newHallway.addConnection(getRoom(splitHallway[i + 2]));
                    } else {
                        getRoom(splitHallway[i]).addConnection(getRoom(splitHallway[i+2]));
                    }
                }
            }

            for(Room room: rooms) {
                Collections.shuffle(room.getConnections());
            }

            // Get the robot starting locations
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                if(line.equals("") || line.equals(" ")) {
                    break;
                } else {
                    Robot r = new Robot(getRoom(line));
                    Controller.addKnownRoom(getRoom(line));
                    robots.add(r);
                }
            }

            // Get your goal locations
            goals[0] = getRoom(scanner.next());
            goals[1] = getRoom(scanner.next());
            scanner.close();

        /*System.out.println(names);
        System.out.println(squareFootage);
        System.out.println(hallways);
        System.out.println(locations);*/

            /*for(Room room : rooms) {
                System.out.println(room);
            }*/
            // System.out.println(robots);
        }
    }

    boolean step() {
        /*for(Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("------------------------------------- Robots ---------------------------------");
        for(Robot robot : robots) {
            System.out.println(robot);
        }
        System.out.println("------------------------------------- Next --------------------------------------");*/
        for(Robot robot : robots) {
            Room currentLoc = robot.getLoc();
            boolean finished = false;
            if(currentLoc.isScanned()) {
                finished = checkCompletion();
                // if(finished) break;
                ArrayList<Connection> connections = currentLoc.getConnections();
                for(Connection connection : connections) {
                    if(!connection.getDestination().isScanned()) {
                        robot.setLoc(connection.getDestination());
                        break;
                    }
                }
                if(currentLoc == robot.getLoc()) {
                    if(robot.followingPath()) {
                        robot.followPath();
                    } else {
                        Collection<Room> unexplored = Controller.getUnscannedRooms();
                        if (unexplored.size() == 0) {
                            System.out.println("No unexplored areas");
                            return true;
                        }
                        Room goalLoc = (Room) unexplored.toArray()[rgen.nextInt(unexplored.size())];
                        ArrayList<Room> path = Controller.findPath(currentLoc, goalLoc);
                        if (path == null || path.isEmpty()) {
                            System.out.println("No path found");
                            for(Room room: Controller.getKnownRooms()) {
                                System.out.println(room);
                            }
                            return true;
                        }

                        robot.followPath(path);
                        robot.followPath();
                    }
                } else {
                    robot.followPath(new ArrayList<>());
                }
            } else {
                currentLoc.addScan(robotScan);
            }

            if(finished) {
                return true;
            }
        }

        return false;
    }

    String getSolution() {
        if(solution.isEmpty()) {
            return "No solution found";
        }
        StringBuilder solutionText = new StringBuilder();
        for(Room room : solution) {
            solutionText.append(room.getName()).append("\n");
        }
        return solutionText.toString();
    }

    private Room getRoom(String roomName) {
        return rooms.get(rooms.indexOf(new Room(roomName, 0)));
    }

    private boolean checkCompletion() {
        if(!goals[0].isScanned() || !goals[1].isScanned()) return false;
        for(Room room : rooms) {
            room.setVisited(false);
        }
        ArrayList<Room> path = new ArrayList<>();
        path.add(goals[0]);
        return dfs(goals[0], goals[1], path);
    }

    // TODO Implement A* instead of dfs algorithm
    /*private ArrayList<Room> aStar(Room start, Room goal) {
        ArrayList<Room> open = new ArrayList<>();
        ArrayList<Room> close = new ArrayList<>();
        open.add(start);

        while (!open.isEmpty()) {

        }
        return null;
    }*/

    private boolean dfs(Room currentRoom, Room goalRoom, ArrayList<Room> path) {
        // System.out.println("Running dfs on " + currentRoom.getName());
        /*System.out.println(currentRoom);
        System.out.println(goalRoom);
        System.out.println("");*/
        currentRoom.setVisited(true);

        if(currentRoom == goalRoom) {
            solution.addAll(path);
            return true;
        }

        for(Connection connection : currentRoom.getConnections()) {
            Room adjRoom = connection.getDestination();
            if(!adjRoom.isScanned()){
                continue;
            }

            if(adjRoom.notVisited()) {
                path.add(adjRoom);
                if (dfs(adjRoom, goalRoom, path)) {
                    return true;
                }
                path.remove(adjRoom);
            }
        }

        // currentRoom.setVisited(false);
        return false;
    }
}
