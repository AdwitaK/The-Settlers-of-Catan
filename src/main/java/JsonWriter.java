import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class JsonWriter {
    private String stateFile;
    private String baseMapFile;

    public JsonWriter(String stateFile, String baseMapFile){
        this.stateFile = stateFile;
        this.baseMapFile = baseMapFile;
        setBaseMap();
    }

    public void write(Game game){
        JSONObject gameState = new JSONObject();

        JSONArray roads = new JSONArray();
        JSONArray buildings = new JSONArray();

        for(Trader agent : game.getAgents()){
            for(Infrastructure infra: ((Agent) agent).getInfrastructure()){
                if(infra==null){
                    break;
                }
                if(infra instanceof Road){
                    roads.put(infra.toJSON());
                }
                else{
                    buildings.put(infra.toJSON());
                }
            }
        }

        gameState.put("roads", roads);
        gameState.put("buildings", buildings);

        try(FileWriter file = new FileWriter(stateFile)){
            file.write(gameState.toString(2));
        }
        catch(IOException e){
            System.err.println("Something went wrong while update game state file: " + e.getMessage());
        }
    }

    public void moveRobber(int destination){
        try{
            String content = Files.readString(Path.of(baseMapFile));
            JSONObject board = new JSONObject(content);
            JSONArray tiles = board.getJSONArray("tiles");

            //search for robber's location
            int oldRobberIndex;
            for(int i = 0; i<tiles.length(); i++){
                if(tiles.getJSONObject(i).isNull("number")){
                    oldRobberIndex = i;
                    tiles.getJSONObject(oldRobberIndex).put("number", MapLayout.tokens[oldRobberIndex]);
                    break;
                }
            }

            //Move robber to new tile
            tiles.getJSONObject(destination).put("number", JSONObject.NULL);
            Files.writeString(Path.of(baseMapFile), board.toString(2));

            //Update state.json timestamp to trigger new render
            Files.setLastModifiedTime( Path.of(stateFile), FileTime.fromMillis(System.currentTimeMillis()));

        }
        catch(IOException e){
            System.err.println("Something went wrong while updating the base map file");
        }



    }

    public void setBaseMap(){
        try{
            String content = Files.readString(Path.of(baseMapFile));
            JSONObject board = new JSONObject(content);
            JSONArray tiles = board.getJSONArray("tiles");
            int token;

            //search for robber's location
            int oldRobberIndex;
            for(int i = 0; i<tiles.length(); i++){
                token = MapLayout.tokens[i];
                if(token==7){
                    tiles.getJSONObject(i).put("number", JSONObject.NULL);
                }
                else{
                    tiles.getJSONObject(i).put("number", token);
                }
            }
            Files.writeString(Path.of(baseMapFile), board.toString(2));

            //Update state.json timestamp to trigger new render
            Files.setLastModifiedTime( Path.of(stateFile), FileTime.fromMillis(System.currentTimeMillis()));

        }
        catch(IOException e){
            System.err.println("Something went wrong while updating the base map file");
        }
    }
}
