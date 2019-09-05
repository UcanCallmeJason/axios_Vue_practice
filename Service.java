package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Service {

	private static Service instance = new Service();

	private Service() {
	}

	public static Service getInstance() {
		return instance;
	}

	public static JSONArray readPopFromAPI() throws SQLException, IOException, ParseException {
		BufferedReader br = null;
		JSONParser parser = new JSONParser();
		JSONObject obj = null;

		int start = 29;
		int end = 54;
		URL url = new URL("http://openapi.seoul.go.kr:8088/647a43446473636335326668596c71/json/octastatapi26/" + start
				+ "/" + end + "/");

		HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
		urlconnection.setRequestMethod("GET");
		br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));

		obj = (JSONObject) ((JSONObject) parser.parse(br.readLine())).get("octastatapi26");
		JSONArray jsonData = (JSONArray) obj.get("row"); 
		return jsonData;

	}

	public static ArrayList<ArrayList<String>> getAllHappData(JSONArray jsonData) {
		ArrayList<ArrayList<String>> allData = new ArrayList<>();
		JSONArray rawData = jsonData;
		//String answer
		for(int i=0; i<rawData.size(); i++) {
			ArrayList<String> takeData = new ArrayList<>();
			JSONObject j = (JSONObject)rawData.get(i);
			takeData.add("'" +(String)j.get("DAEBULLYU")+ "'");
			takeData.add((String)j.get("SAHOESAENGHWAL"));
			allData.add(takeData);
		}
		return allData;
	}

	public static void main(String[] args) {
		try {
			//System.out.println(readPopFromAPI());
			System.out.println(getAllHappData(readPopFromAPI()));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
