package com.jt.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class HttpClientService {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;


    public String doGet(String url, Map<String,String> params,String charset){
        if(StringUtils.isEmpty(charset)){
            charset = "utf-8";
        }
        if(params!=null){
            url+="?";
            for (Map.Entry<String,String> entry : params.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                url = url + key + "=" + value + "&";
            }
            url = url.substring(0,url.length()-1);
        }

        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        String result = null;
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(response.getEntity(),charset);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    public String doGet(String url){
        return doGet(url,null,null);
    }

    public String doGet(String url ,Map<String,String> params){
        return doGet(url,params,null);
    }
}
