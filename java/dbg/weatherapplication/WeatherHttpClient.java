package dbg.weatherapplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by grego on 12/17/2015.
 */
public class WeatherHttpClient {

    public static String MAIN_URL = "http://api.openweathermap.org/data/2.5/weather?q=";


    public String getWeatherData (String location) {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            con = (HttpURLConnection) (new URL(MAIN_URL + location + "/city?id=524901&APPID=562b1e6d6ab0ca5874ab46dbfa5fa13b")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine())!=null)
                buffer.append(line+"\r\n");
            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch (Throwable t){
            t.printStackTrace();
        }
        finally {
            try{
                if (is !=null)
                    is.close();
            } catch (Throwable t) {t.printStackTrace();}
            try {
                if (con !=null)
                    con.disconnect();
            } catch (Throwable t) {t.printStackTrace();}
        }
        return null;
    }
}
