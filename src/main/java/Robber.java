public class Robber {
    private HexTile currentTile;
    //private int currLandTileID;

    public Robber(HexTile startTile){
        this.currentTile = startTile;
        //this.currLandTileID = 16;
    }


    public boolean moveRobber(HexTile newTile){
        //check if the robber is already on this tile
        if (currentTile.equals(newTile)){
            return false; //you can't place the robber in the same tile again
        }

        //removes robber from old tile
        if (currentTile != null){
            currentTile.setRobber(false);
        }

        //moves robber to new tile
        this.currentTile = newTile;
        newTile.setRobber(true);

        return true; //robber changed successful
    }
    public HexTile getCurrentTile(){
        return currentTile;
    }
//    public int getCurrentTile() {
//        return currLandTileID;
//    }
}