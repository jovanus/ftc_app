package org.firstinspires.ftc.teamcode.Subsystems;

public class LocationStatus {
    private BlockPosition Loc;
    private int NumOfObjects;
    private boolean BOnScreen;

    public LocationStatus(BlockPosition Location, int NumberOfObjects, boolean BlockOnScreen) {
        setLoc(Location);
        setNumOfObjects(NumberOfObjects);
        setBOnScreen(BlockOnScreen);
    }

    public BlockPosition getLoc() {
        return Loc;
    }

    public int getNumOfObjects() {
        return NumOfObjects;
    }

    public boolean isBOnScreen() {
        return BOnScreen;
    }

    @Override
    public String toString() {
        return "Block Pos: " + Loc.toString() + " # Objects: " + NumOfObjects +
                " Yellow Seen: " + BOnScreen;
    }

    public void setLoc(BlockPosition loc) {
        Loc = loc;
    }

    public void setNumOfObjects(int numOfObjects) {
        NumOfObjects = numOfObjects;
    }

    public void setBOnScreen(boolean BOnScreen) {
        this.BOnScreen = BOnScreen;
    }
}
