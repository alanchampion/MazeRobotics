import java.util.ArrayList;

public class Robot {
    private Room loc;
    private ArrayList<Room> path;

    Robot(Room loc) {
        this.loc = loc;
        path = new ArrayList<>();
    }

    Room getLoc() {
        return loc;
    }

    void setLoc(Room loc) {
        this.loc = loc;
        Controller.addKnownRoom(loc);
    }

    void followPath() {
        this.loc = path.get(0);
        path.remove(0);
    }

    void followPath(ArrayList<Room> path) {
        this.path = path;
    }

    boolean followingPath() {
        return !path.isEmpty();
    }

    @Override
    public String toString() {
        return "Robot{" +
                "loc=" + loc.getName() +
                '}';
    }
}
