public class Robot {
    private Room loc;
    Robot(Room loc) {
        this.loc = loc;
    }

    Room getLoc() {
        return loc;
    }

    void setLoc(Room loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "loc=" + loc.getName() +
                '}';
    }
}
