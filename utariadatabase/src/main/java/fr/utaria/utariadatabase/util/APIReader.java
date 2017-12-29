package fr.utaria.utariadatabase.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class APIReader {

	private static final String BASE_URL = "https://utaria.fr/api/";


	public static Object get(String method, String submethod, Map<String, String> params) throws Exception {
		// Création de l'URL d'appel à l'API
		String url     = BASE_URL + method + "/" + submethod;
		if (params.size() > 0)
			url += "?" + APIReader.urlEncodeUTF8(params);

		// Récupération du retour de l'API
		String content = APIReader.readUrl(url);

		// Transformation des informations en format JSONObject pour une lecture plus facile
		JSONParser parse = new JSONParser();
		JSONObject result = (JSONObject) parse.parse(content);

		if (result.containsKey("resultat"))
			return result.get("resultat");
		else if (result.containsKey("error")) {
			JSONObject error = (JSONObject) result.get("error");

			throw new IllegalAccessException("API Erreur #" + String.format("%03d", (long) error.get("code")) + " : " + error.get("message"));
		}

		return null;
	}


	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;

		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));

			StringBuffer buffer = new StringBuffer();

			int read;
			char[] chars = new char[1024];

			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	private static String urlEncodeUTF8(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	private static String urlEncodeUTF8(Map<?,?> map) {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<?,?> entry : map.entrySet()) {
			if (sb.length() > 0)
				sb.append("&");

			sb.append(String.format("%s=%s",
					urlEncodeUTF8(entry.getKey().toString()),
					urlEncodeUTF8(entry.getValue().toString())
			));
		}

		return sb.toString();
	}

}
