package fr.utaria.utariadatabase.util;

import fr.utaria.utariadatabase.InstanceManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIReader {

	private static final String API_ENDPOINT = "https://api.utaria.fr/v2/";

	private static String authToken;

	private static Configuration configuration;

	public static boolean authenticate() {
		if (authToken != null) return true;

		Configuration configuration = getUtariaConfig();
		if (configuration == null) return false;

		String name = configuration.getString("auth.name");
		String params = "name=" + name + "&password=" + configuration.getString("auth.password");
		JSONObject obj = APIReader.apiRequest("authenticate", "POST", params);

		if (obj != null && (Boolean) obj.get("success")) {
			authToken = (String) obj.get("token");
			return true;
		} else {
			return false;
		}
	}

	public static String getAuthToken() {
		return authToken;
	}

	public static Configuration getUtariaConfig() {
		if (configuration != null)
			return configuration;

		File confFile = new File("utaria.yml");
		if (!confFile.exists())
			return null;

		return configuration = InstanceManager.getInstance().getConfiguration(confFile);
	}

	public static JSONObject apiRequest(String action, String method, String params) {
		try {
			URL url = new URL(API_ENDPOINT + action);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod(method);
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);

			if (method.equals("POST")) {
				byte[] postData = params.getBytes("UTF-8");

				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
				conn.getOutputStream().write(postData);
			}

			// Code de retour incorrect, on ne retourne rien.
			if (conn.getResponseCode() == 404)
				return null;

			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			StringBuilder sb = new StringBuilder();
			for (int c; (c = in.read()) >= 0;)
				sb.append((char)c);

			return (JSONObject) new JSONParser().parse(sb.toString());
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
