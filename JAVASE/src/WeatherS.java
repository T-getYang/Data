import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WeatherS {
    public static List<Weather> list = new LinkedList<>();

    public static void main(String[] args) {
        String url = "https://www.tianqiapi.com/api?version=v9&cityid=101280601&appid=86865224&appsecret=ik5xXxzf";
        PrintWriter out = null;
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            out = new PrintWriter(connection.getOutputStream());
            out.flush();

            inputStream =connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String str = bufferedReader.readLine();
            str = new String(str.getBytes(),"UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(str);
            String city = jsonObject.getString("city");
            JSONArray data = jsonObject.getJSONArray("data");
            Iterator<Object> iterator = data.iterator();

            while (iterator.hasNext()){
                JSONObject jsonObjectOne = JSONObject.parseObject(iterator.next().toString());
                String date = jsonObjectOne.getString("date");
                String wea =jsonObjectOne.getString("wea");
                String tem = jsonObjectOne.getString("tem");
                String tem1 = jsonObjectOne.getString("tem1");
                String tem2 =jsonObjectOne.getString("tem2");
                list.add(new Weather(city,date,wea,tem1,tem2,tem));

            }

            for (Weather tq:list){
                System.out.println(tq);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
