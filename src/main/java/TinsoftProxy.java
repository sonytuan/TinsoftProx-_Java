import lombok.Data;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

@Data
public class TinsoftProxy {
    private  String proxy ;
    private  String apiKey;
    private  int location;
    private  int nextChange;
    private  int timeout;
    private  Boolean isGetIPSuccess;
    private  String error;
    private final String serverDomain ="http://proxy.tinsoftsv.com";
    private double lastRequest=0;

    public TinsoftProxy(String apiKey,int location){
        this.apiKey = apiKey;
        this.location = location;
        this.nextChange =0;
        this.timeout=0;
        this.isGetIPSuccess =false;
        this.error="";
    }

    public TinsoftProxy(String apiKey){
        this(apiKey, 0);
    }

    public void initData(){
        this.nextChange = 0;
        this.timeout = 0;
        this.isGetIPSuccess = false;
        this.error = "";
    }

    public void changeIP(){
        initData();
        if(checkLastRequest()) {
            try {
                String rs = loadUrl(serverDomain + "/api/changeProxy.php?key=" + apiKey + "&location=" + location);
                if (!Objects.equals(rs, "")) {
                    JSONObject jObject = new JSONObject(rs);
                    setData(jObject);
                } else {
                    this.error = "Request server timeout!";
                }
            } catch (Exception e) {
                this.error = "Request server timeout!";
            }
        }else {
            this.error ="Request so fast!";
            this.isGetIPSuccess = false;
        }
    }
    public void getCurrentIP(){
        initData();
        if(checkLastRequest()) {
            try {
                String rs = loadUrl(serverDomain + "/api/getProxy.php?key=" + apiKey);
                if (!Objects.equals(rs, "")) {
                    JSONObject jObject = new JSONObject(rs);
                    setData(jObject);
                } else {
                    this.error = "Request server timeout!";
                }
            } catch (Exception e) {
                this.error = "Request server timeout!";
            }
        } else {
            this.error ="Request so fast!";
            this.isGetIPSuccess = false;
        }
    }

    public void setData(JSONObject jObject){
        if (jObject.getBoolean("success")) {
            this.proxy = jObject.getString("proxy");
            this.nextChange = jObject.getInt("next_change");
            this.timeout = jObject.getInt("timeout");
            this.isGetIPSuccess = true;
        } else {
            this.error = jObject.getString("description");
        }
    }

    private  boolean checkLastRequest(){
        double now = System.currentTimeMillis();
        if((now-this.lastRequest) >= 10000){
            this.lastRequest = now;
            return  true;
        }else {
            return  false;
        }

    }
    private String loadUrl(String apiUrl) throws IOException, InterruptedException {
        String result;
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .setHeader("Accept", "application/json")
                .setHeader("Content-type", "application/json")
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        result = response.body();
        return  result;
    }
}
