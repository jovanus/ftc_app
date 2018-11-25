package org.firstinspires.ftc.teamcode.Subsystems;

public class LocationStatus {
    private BlockPosition Loc;
    private int NumOfObjects;
    private boolean BOnScreen;

    public LocationStatus(BlockPosition Location, int NumberOfObjects, boolean BlockOnScreen) {
        Loc = Location;
        NumOfObjects = NumberOfObjects;
        BOnScreen = BlockOnScreen;
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
}
