package org.botelloneslennon.winthelotery.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

@SuppressWarnings("unused")
public class WinTheLoteryMain {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		URL url = null;
		String server1 = "https://www.national-lottery.co.uk/results";
	    try {
	        url = new URL(server1);
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    }
	    InputStream in = null;
	    try {
	    	URLConnection con = url.openConnection();
	    	con.setConnectTimeout(20000);
	    	con.setReadTimeout(20000);
	    	in = con.getInputStream();
			String text = new Scanner(in).useDelimiter("\\A").next();
			text = text.replace("\n", " ").replace("\r", " ").replace("\t", " ").replaceAll("\\s+", " ");
	    	System.out.println("Server: " + server1);
	    	System.out.println("Esperanza: " + cacularEsperanza());
		    System.out.println("Premio para: " + Arrays.toString(getTagValues(text).toArray())); // Prints [apple, orange, pear]
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally{
	    	 try { in.close(); } catch (IOException e) { }
	    }

	}
	
	
	private static double cacularEsperanza() {
		double pOpcion = 2.0;
		double n = calcularNumeroOpciones();
		double p = calcularTotalPremios();
		return p/(n*pOpcion);
	}


	private static int calcularNumeroOpciones() {
		return 76275360;
	}

	private static double calcularTotalPremios() {
		int[] arrayPremiosCategorias = {2, 6, 7, 49, 1363, 1904, 4792, 24927, 46467, 150408, 121420, 576536, 1769289};
		double[] arrayAcertantesCategorias = {30570889, 2099078.98, 8889.08, 2928.19, 112.29, 112.2, 25.28, 22.38, 12.69, 7.42, 13, 8.21, 3.29};
		double total = 0;
		for(int i=0;i<arrayPremiosCategorias.length;i++)
			total = total + (arrayPremiosCategorias[i]*arrayAcertantesCategorias[i]);
		System.out.printf("Total: %f€\n", total);
		return total;
	}

	private static final Pattern TAG_REGEX = Pattern.compile("<div class=\"winning_numbers_euromillions winning_numbers\"> <div class=\"winning_numbers_inner clr\"> <h3 class=\"clr\"> <span class=\"main vh\">Ball numbers</span> <span class=\"optional txt_medium\">Lucky Stars</span> </h3> <div class=\"draw_numbers clr\"> <ol class=\"draw_numbers_list clr\"> <li class=\"normal normal_first\"> (.+?) </li> <li class=\"normal \"> (.+?) </li> <li class=\"normal \"> (.+?) </li> <li class=\"normal \"> (.+?) </li> <li class=\"normal normal_last\"> (.+?) </li> <li class=\"special special_first\"><span class=\"vh\">Lucky Star</span> (.+?)</li> <li class=\"special special_last\"><span class=\"vh\">Lucky Star</span> (.+?)</li> </ol> </div> </div> </div>");

	private static List<String> getTagValues(final String str) {
	    final List<String> tagValues = new ArrayList<String>();
	    final Matcher matcher = TAG_REGEX.matcher(str);
	    while (matcher.find()) {
	        tagValues.add(matcher.group(1));
	        tagValues.add(matcher.group(2));
	        tagValues.add(matcher.group(3));
	        tagValues.add(matcher.group(4));
	        tagValues.add(matcher.group(5));
	        tagValues.add(matcher.group(6));
	        tagValues.add(matcher.group(7));
	    }
	    return tagValues;
	}

}