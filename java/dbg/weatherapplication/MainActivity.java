package dbg.weatherapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import dbg.weatherapplication.model.Location;
import dbg.weatherapplication.model.Weather;

public class MainActivity extends AppCompatActivity {



    private TextView cityText, condDescr, temp, press, windSpeed, windDeg, hum;
    private ImageView imgView;
    private EditText countryinput,cityinput;
    private Button search;
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        imgView = (ImageView) findViewById(R.id.condIcon);
        search = (Button) findViewById(R.id.buttonsearch);
        city = "London,UK";
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});



    }


    public void clickedbutton(View view) {
try{


        cityinput = (EditText) findViewById(R.id.citynameinput);
        countryinput = (EditText) findViewById(R.id.CountryNameInput);
        String citylocation,countrylocation;
        citylocation = cityinput.getText().toString();
        countrylocation = countryinput.getText().toString();
        city = citylocation.toString() + ","+countrylocation.toString();
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
}catch (NullPointerException e)
{
    e.printStackTrace();
}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
            try {
                weather = JSONWeatherParser.getWeather(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            //if(weather.iconData != null && weather.iconData.length > 0)
            cityText.setText(weather.location.getCity()+ "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr()+")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "�C");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("" + weather.wind.getDeg() + "�");
        }
    }
}







