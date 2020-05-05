import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RMIServer extends UnicastRemoteObject implements RMIService {

	private static int count = 0;
	private final String API_URL = "http://localhost:3000/api/"; // API URL which is reusable in every http call

	public RMIServer() throws RemoteException {
		super();
	}

	// login user
	@Override
	public boolean getLogin(String username, String password)
			throws IOException, InterruptedException, RemoteException {
		// Create JSON body for the login POST request
		final String SENSOR_PARAMS = "{\n\r" + "\"email\": \"" + username + "\",\r\n" + "    \"password\":\"" + password
				+ "\"\n}";
		System.out.println(SENSOR_PARAMS);

		// Create URL
		URL obj = new URL(this.API_URL + "auth/");

		// Open a new connection with data and the url
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		OutputStream os = connection.getOutputStream();
		os.write(SENSOR_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = connection.getResponseCode();

		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + connection.getResponseMessage());

		if (responseCode == HttpURLConnection.HTTP_OK) { // check if the connection is done properly ( status === 200)
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			try {
				JSONObject sensors = new JSONObject(response.toString()); // get response as a JSON object
				System.out.println(sensors.getString("message"));
				if (sensors.getString("message").equals("unauthorized")) { // check if the message is unauthorized
					return false;
				} else {
					return true;
				}
			} catch (JSONException ex) {
				System.err.println(ex.getMessage());
				return false;
			}
		} else {
			System.out.println("POST NOT WORKED"); // IF connection fails
			return false;
		}
	}

	// get sensor details
	public String getSensorDetails() throws IOException, InterruptedException, RemoteException, JSONException {
		// create url for http call
		URL urlForGetRequest = new URL(this.API_URL + "sensors/");
		// Open get request connection
		HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
		conection.setRequestMethod("GET");
		int responseCode = conection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {

			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));

			StringBuffer response = new StringBuffer();
			String readLine;
			while ((readLine = in.readLine()) != null) {
				response.append(readLine);
			}
			in.close();

			JSONObject jsomnObj = new JSONObject(response.toString());
			JSONArray sensors = jsomnObj.getJSONArray("sensors");

			for (int i = 0; i < sensors.length(); i++) {
				JSONObject sensor = sensors.getJSONObject(i);
				this.checkSensorStatus(sensor);
			}
			// return response.toString();
			return sensors.toString();
		} else {
			System.out.println("GET NOT WORKED");
			return null;
		}
	}

	// check SMOKE LEVEL and CO2 Level
	// this method will create http request to the backend API
	public boolean checkSensorStatus(JSONObject sensor) throws JSONException, IOException {
		if (sensor.getInt("smokeLevel") > 5 || sensor.getInt("CO2Level") > 5) {
			// This method is utilized to send api request to either send mails or sms
			sendAlert(sensor.getInt("floorNo"), sensor.getInt("roomNo"), "email"); // Use send alert method to send an
																					// email
			sendAlert(sensor.getInt("floorNo"), sensor.getInt("roomNo"), "text"); // Use send alert method to send text

			return true;
		} else {
			return false;
		}
	}

	// send email
	public void sendAlert(int FloorNo, int RoomNo, String service)
			throws MalformedURLException, IOException, JSONException {
		final String SENSOR_PARAMS = "{\n" + "\"floor\": " + FloorNo + ",\r\n" + " \"room\": " + RoomNo + "\n}";

		URL obj = new URL(this.API_URL + "messages/" + service); // send API call according to the method call

		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);

		OutputStream os = postConnection.getOutputStream();
		os.write(SENSOR_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = postConnection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
			StringBuffer response = new StringBuffer();
			String readLine;
			while ((readLine = in.readLine()) != null) {
				response.append(readLine);
			}
			in.close();
			JSONObject result = new JSONObject(response.toString()); // get the response as JSON object
			if ((boolean) result.get("status") == true) { // check if the status is true
				System.out.println("Response: " + result.getString("message"));
			}
		} else {
			System.out.println("POST method FAILED");
		}
	}

	// get single sensor details
	public String getSingleSensorDetails() throws IOException, InterruptedException, RemoteException {

		final String SENSOR_ID = "5ea0a01f8d913858f6490d7a";

		URL obj = new URL(this.API_URL + "sensors/5ea0a01f8d913858f6490d7a");

		HttpURLConnection conection = (HttpURLConnection) obj.openConnection();
		conection.setRequestMethod("GET");
		int responseCode = conection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			StringBuffer response = new StringBuffer();
			String readLine;
			while ((readLine = in.readLine()) != null) {
				response.append(readLine);
			}
			in.close();
			System.out.println("JSON String Result " + response.toString());
			return response.toString();
		} else {
			System.out.println("GET NOT WORKED");
			return null;
		}
	}

	// add sensor
	public String addSensor(String FloorNo, String RoomNo, String Status)
			throws IOException, InterruptedException, RemoteException {
		final String SENSOR_PARAMS = "{\n" + "\"floorNo\": " + FloorNo + ",\r\n" + " \"roomNo\": " + RoomNo + ",\r\n"
				+ " \"status\": \"" + Status + "\"\n}";
		System.out.println(SENSOR_PARAMS);

		URL obj = new URL(this.API_URL + "sensors/");

		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);

		OutputStream os = postConnection.getOutputStream();
		os.write(SENSOR_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = postConnection.getResponseCode();

		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + postConnection.getResponseMessage());

		if (responseCode == HttpURLConnection.HTTP_CREATED) {
			BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
			return response.toString();
		} else {
			System.out.println("POST NOT WORKED");
			return "POST NOT WORKED";
		}

	}

	// update sensor
	public String updateSensor(String ID, String FloorNo, String RoomNo, String Status)
			throws IOException, InterruptedException, RemoteException {

		final String SENSOR_ID = ID;
		final String SENSOR_PARAMS = "{\n" + "\"floorNo\": " + FloorNo + ",\r\n" + " \"roomNo\": " + RoomNo + ",\r\n"
				+ " \"status\": \"" + Status + "\"\n}";

		URL obj = new URL(this.API_URL + "sensors/" + SENSOR_ID);

		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		OutputStream os = connection.getOutputStream();
		os.write(SENSOR_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = connection.getResponseCode();

		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + connection.getResponseMessage());

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			JSONObject employeeDetails = new JSONObject();
			in.close();
			System.out.println(response.toString());
			return response.toString();
		} else {
			System.out.println("POST NOT WORKED");
			return "POST NOT WORKED";
		}
	}

	public static void main(String[] args) {

		System.setProperty("java.security.policy", "file:allowall.policy");

		try {

			RMIServer svr = new RMIServer();
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("rmiservice", svr);

			System.out.println("Service started....");
		} catch (RemoteException re) {
			System.err.println(re.getMessage());
		} catch (AlreadyBoundException abe) {
			System.err.println(abe.getMessage());
		}
	}
}