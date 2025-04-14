import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MessageRoom {
    private static MessageRoom instance;
    List<JSONObject> listamsg;

    MessageRoom() {
        listamsg = new ArrayList<>();
    }

    public static void setInstance() {
        if (instance == null) {
            instance = new MessageRoom(); // Viene creata solo una volta
        }
    }

    public static MessageRoom getInstance() {
        return instance;
    }

    public synchronized void AddMessage(JSONObject json){
        listamsg.add(json);
        notifyAll();
    }

    public synchronized void waitMsg() throws InterruptedException {
        wait();
    }

    public synchronized boolean ThereIsMsg(String id){
        for (JSONObject msg : listamsg) {
            if (msg.has("to") && msg.getString("to").equals(id)) {
                return true;
            }
        }
        return false;
    }

    public synchronized JSONObject SerchMsg(String id){
        for (JSONObject msg : listamsg) {
            if (msg.has("to") && msg.getString("to").equals(id)) {
                return msg;
            }
        }
        return null;
    }

    public synchronized void Remove(JSONObject msg){
        listamsg.remove(msg);
    }
}
