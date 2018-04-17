import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLayout {
    private Scanner scanner;
    private String file;
    ArrayList<Room> rooms;
    ArrayList<String> robotLocations;
    long hallwayWidth;

    MapLayout(String fileName) {
        this(fileName, 10);
    }

    MapLayout(String fileName, long width) {
        file = fileName;
        robotLocations = new ArrayList<>();
        hallwayWidth = width;

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

        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            if(line.equals("") || line.equals(" ") || line.equals(null)) {
                break;
            } else {
                String[] room = line.split("\\s+");
                names.add(room[0]);
                squareFootage.add(Long.parseLong(room[1]));
            }
        }
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            if(line.equals("") || line.equals(" ") || line.equals(null)) {
                break;
            } else {
                hallways.add(line);
            }
        }
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            robotLocations.add(line);
        }

        /*System.out.println(names);
        System.out.println(squareFootage);
        System.out.println(hallways);
        System.out.println(locations);*/

        rooms = new ArrayList<>();

        for(int i = 0; i < names.size(); i++) {
            rooms.add(new Room(names.get(i), squareFootage.get(i)));
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

        for(Room room : rooms) {
            System.out.println(room);
        }
        System.out.println(robotLocations);
    }

    public boolean step() {
        for(String robot : robotLocations) {

        }
        return false;
    }

    private Room getRoom(String roomName) {
        return rooms.get(rooms.indexOf(new Room(roomName, 0)));
    }
}
