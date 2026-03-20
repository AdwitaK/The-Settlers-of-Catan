public enum Colour {
    RED(1),
    BLUE(2),
    ORANGE(3),
    WHITE(4);

    private int playerID;
    private Colour(int playerID){
        this.playerID = playerID;
    }
    public static Colour getColour(int id){
        for(Colour colour: values()){
            if(colour.playerID==id){
                return colour;
            }
        }
        return null;
    }
}//end of Colour Enum
