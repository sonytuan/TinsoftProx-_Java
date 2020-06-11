package com.tinsoft.proxy;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TinsoftProxy {
    public  String proxy ;
    public  String api_key;
    public  int location;
    public  int next_change;
    public  int timeout;
    public  Boolean isGetIPSuccess;
    public  String error;
    private String serverDomain ="http://proxy.tinsoftsv.com";
    private double lastRequest=0;

    public TinsoftProxy(String api_key,int location){
        this.api_key = api_key;
        this.location = location;
        this.next_change=0;
        this.timeout=0;
        this.isGetIPSuccess =false;
        this.error="";
    }
    public void changeIP(){

        this.next_change = 0;
        this.timeout = 0;
        this.isGetIPSuccess = false;
        this.error = "";
        if(checkLastRequest()) {
            try {
                String rs = loadUrl(serverDomain + "/api/changeProxy.php?key=" + api_key + "&location=" + location);
                if (rs != "") {
                    JSONObject jObject = new JSONObject(rs);
                    if (jObject.getBoolean("success")) {
                        this.proxy = jObject.getString("proxy");
                        this.next_change = jObject.getInt("next_change");
                        this.timeout = jObject.getInt("timeout");
                        this.isGetIPSuccess = true;
                    } else {
                        this.error = jObject.getString("description");
                    }

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
    public void getCurentIP(){

        this.next_change = 0;
        this.timeout = 0;
        this.isGetIPSuccess = false;
        this.error = "";
        if(checkLastRequest()) {
            try {
                String rs = loadUrl(serverDomain + "/api/getProxy.php?key=" + api_key);
                if (rs != "") {
                    JSONObject jObject = new JSONObject(rs);
                    if (jObject.getBoolean("success")) {
                        this.proxy = jObject.getString("proxy");
                        this.next_change = jObject.getInt("next_change");
                        this.timeout = jObject.getInt("timeout");
                        this.isGetIPSuccess = true;
                    } else {
                        this.error = jObject.getString("description");
                    }

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
    private  boolean checkLastRequest(){
        double now = System.currentTimeMillis();
        if((now-this.lastRequest)>=10000){
            this.lastRequest = now;
            return  true;
        }else {
            return  false;
        }

    }
    private String loadUrl(String api_url) throws IOException, ExecutionException, InterruptedException {
        String myUrl = api_url;
        String result;
        HttpGetRequest getRequest = new HttpGetRequest();
        result = getRequest.execute(myUrl).get();
        //System.out.println(result);
        return  result;
    }


}
