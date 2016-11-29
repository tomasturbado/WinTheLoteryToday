package es.botelloneslennon.tiroaltag.main;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

@SuppressWarnings("unused")
public class TiroAlTagMain {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		URL url = null;
	
	    try {
	        url = new URL("http://stackoverflow.com/questions/6272405/read-from-url-java");
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    }
	    InputStream in = null;
	    try {
	    	
	    	URLConnection con = url.openConnection();
	    	con.setConnectTimeout(20000);
	    	con.setReadTimeout(20000);
	    	in = con.getInputStream();
	    	
	    	// esto es usando Apache Commons
//	    	String theString = IOUtils.toString(in, "UTF-8");
//	    	System.out.println(theString);
	    	
	    	
	    	// esta forma se puede usar tambien para leer archivos.
	    	// \\A es la regex del comienzo del archivo.
			String text = new Scanner(in).useDelimiter("\\A").next();
	    	System.out.println(text);
	    	
	    	
	    	// usa esto si prefieres leer linea a linea:
//	    	bf = new BufferedReader(new InputStreamReader(in));
//	        while ((inputLine = bf.readLine()) != null) {
//	            System.out.println(inputLine);
//	        }
	    	
	       
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally{
	    	 try { in.close(); } catch (IOException e) { }
	    }

	}

}