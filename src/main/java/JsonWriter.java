import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter {
    private String fileName;

    public JsonWriter(String fileName){
        this.fileName = fileName;
    }

    public void write(Game game){
        System.out.println("debug: updating state now");//
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

        try(FileWriter file = new FileWriter(fileName)){
            file.write(gameState.toString(2));
        }
        catch(IOException e){
            System.err.println("Something went wrong while update game state file: " + e.getMessage());
        }
    }
}
