public class Weather {
    //城市
    private String city;
    //天气
    private String weather;
    //时间
    private String time;
    //现在天气
    private String stime;
    //最高温度
    private String max;
    //最地温度
    private String mix;


    public Weather(){

    }

    public Weather(String city, String weather, String time, String stime, String max, String mix) {
        this.city = city;
        this.weather = weather;
        this.time = time;
        this.stime = stime;
        this.max = max;
        this.mix = mix;
    }

    public String getCity() {
        return city;
    }

    public String getWeather() {
        return weather;
    }

    public String getTime() {
        return time;
    }

    public String getStime() {
        return stime;
    }

    public String getMax() {
        return max;
    }

    public String getMix() {
        return mix;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setMix(String low) {
        this.mix = low;
    }

    @Override
    public String toString() {
        return "weather{" +
                "city='" + city + '\'' +
                ", weather='" + weather + '\'' +
                ", time='" + time + '\'' +
                ", stime='" + stime + '\'' +
                ", max='" + max + '\'' +
                ", mix='" + mix + '\'' +
                '}';
    }


}
