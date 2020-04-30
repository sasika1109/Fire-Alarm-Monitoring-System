import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.json.JSONException;

public interface RMIService extends Remote {

    public String getSensorDetails() throws IOException, InterruptedException, RemoteException,JSONException;
    public String addSensor(String FloorNo, String RoomNo, String Status) throws IOException, InterruptedException, RemoteException;
    public String updateSensor(String ID, String FloorNo, String RoomNo, String Status) throws IOException, InterruptedException, RemoteException;
    public String getSingleSensorDetails() throws IOException, InterruptedException, RemoteException;
    public boolean getLogin(String user, String pass) throws RemoteException,InterruptedException, IOException;
    
}