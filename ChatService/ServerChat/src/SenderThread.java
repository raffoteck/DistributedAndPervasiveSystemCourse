import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class SenderThread extends Thread {
    private final String clientId;
    private final PrintWriter out;
    private final MessageRoom room;

    public SenderThread(String clientId, PrintWriter out) {
        this.clientId = clientId;
        this.out = out;
        this.room = MessageRoom.getInstance();
    }

    public void run() {
        JSONObject msg = new JSONObject();
        while (true) {
            while(room.ThereIsMsg(clientId))
            {
                msg = room.SerchMsg(clientId);
                out.println(msg);
                room.Remove(msg);
            }
            try {
                room.waitMsg();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
