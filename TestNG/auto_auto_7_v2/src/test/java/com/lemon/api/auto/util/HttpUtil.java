package com.lemon.api.auto.util;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;


public class HttpUtil {

    public static  Map<String,String> cookies = new HashMap<String, String>();

    public static void main(String[] args) {
        String url  =  "http://h104.jzzhibo.com/q100/specialProp";
        String url1  =  "http://h104.jzzhibo.com/allactivity/duanwuAnchorList";
        Map<String,String> map  =  new HashMap<String, String>();
        map.put("uid","2207270");
//        String response =  DoPost(url,map);
//        System.out.println(response);
        System.out.println(DoGet(url1,map));
    }


    /**
     * @param url 地址和接口
     * @param params 参数
     * @return
     */
    public static String DoPost(String url, Map<String, String> params) {
        String result = null;
        HttpPost post = new HttpPost(url);
        //准备测试数据
        //准备请求头
        post.addHeader("IEPNGS", "8c875611a7c88647f18eb321e7faafba");

        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            parameters.add(new BasicNameValuePair(key, value));
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
            HttpClient client = HttpClients.createDefault();
            addCookieInRequestHeaderBeforeRequest(post);
            //
            HttpResponse reponse = client.execute(post);
            getAndStoreCookiesFromResponHeader(reponse);
            //状态码
            int code = reponse.getStatusLine().getStatusCode();
            // 使用Apache提供的工具类进行转换成字符串
            result = EntityUtils.toString(reponse.getEntity());
            System.out.println("code=『"+code+"』,result=『"+result+"』");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void addCookieInRequestHeaderBeforeRequest(HttpRequest request) {
        String jessionIdCookie  = cookies.get("JSESSIONID");
        if(jessionIdCookie!=null){
            request.addHeader("Cookie",jessionIdCookie);
        }
    }

    public static String DoGet(String url,Map<String,String> params){
//       准备数据
        String result  = null;
        Set<String> keys =  params.keySet();
        int num = 1;
        for (String key:keys) {
            if(num==1){
                url +=( "?"+key+"="+params.get(key));
            }else{
                url +=( "&"+key+"="+params.get(key));
            }
            num++;
        }
        System.out.println(url);
        HttpGet get  = new HttpGet(url);
        get.addHeader("IEPNGS", "8c875611a7c88647f18eb321e7faafba");
        HttpClient httpclient = HttpClients.createDefault();
        try {
            addCookieInRequestHeaderBeforeRequest(get);
            HttpResponse response =  httpclient.execute(get);
            getAndStoreCookiesFromResponHeader(response);
            int code  = response.getStatusLine().getStatusCode();
            result = EntityUtils.toString(response.getEntity());
            System.out.println("code=『"+code+"』,result=『"+result+"』");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }

    public static  String doService(String url,String type,Map<String,String> params){
        String result = "";
        if("post".equalsIgnoreCase(type)){
            result = HttpUtil.DoPost(url,params);
        }else if("get".equalsIgnoreCase(type)){
            result= HttpUtil.DoGet(url,params);
        }
        return result;

    }


    public static void getAndStoreCookiesFromResponHeader(HttpResponse httpResponse){
        //从响应头里取出名字为“set_Cookie”的响应头
        Header setCookieHeader  =  httpResponse.getFirstHeader("Set_Cookie");
        if(setCookieHeader!=null){
            String cookiePairsString = setCookieHeader.getValue();
            if(cookiePairsString!=null&&cookiePairsString.trim().length()>0){
                String[] cookiePairs=cookiePairsString.split(";");
                if(cookiePairs!=null){
                    for (String cookiepair:cookiePairs) {
                        if(cookiepair.contains("JSESSIONID")){
                            cookies.put("JSESSION",cookiepair);
                        }

                    }
                }
            }
        }

    }



}

