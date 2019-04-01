package com.mintc.eurakeconsumer.controller;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@Configuration
public class HystrixController extends CommonController{

    public static class HystrixText extends HystrixCommand<String>{
        protected HystrixText() {
            super(HystrixCommandGroupKey.Factory.asKey("myGroup"));
        }

        @Override
        protected String run() throws Exception {
            String url = "http://localhost:1112/error22";
            //1.创建一个默认的http客户端
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2.创建一个GET请求
            HttpGet httpGet = new HttpGet(url);
            String responseBody = null;
            try {
                //3.获取响应
                HttpResponse httpResponse = httpClient.execute(httpGet);
                responseBody = EntityUtils.toString(httpResponse.getEntity());
                System.out.println();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseBody;
        }

        @Override
        protected String getFallback() {
            return "熔断回退策略";
        }
    }

    @GetMapping(value = "/router")
    @ResponseBody
    public String router(){
        RestTemplate temp = getRestTemplate();
        return temp.getForObject("http://eureka-provider/search/1", String.class);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            HystrixText hystrixText = new HystrixText();
            System.out.println("result:"+hystrixText.execute());
        }
    }
}
