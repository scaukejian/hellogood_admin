package com.hellogood.utils;

import com.hellogood.exception.BusinessException;
import org.apache.commons.codec.binary.*;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Created by kejian
 */
public class HttpsUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpsUtils.class);


    private static int SocketTimeout = 2000;//2秒
    private static int ConnectTimeout = 2000;//2秒
    private static Boolean SetTimeOut = true;

    private static CloseableHttpClient getHttpClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        //指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        //构建客户端
        return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }

    /**
     * get
     *
     * @param url     请求的url
     * @param token
     * @return
     * @throws IOException
     */
    public static String get(String url, String token){

        log.info(" get [url="+url+"] , [token="+token+"]");

        String responseBody = "";
        //支持https
        CloseableHttpClient httpClient = getHttpClient();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("token", token);
        if (SetTimeOut) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SocketTimeout)
                    .setConnectTimeout(ConnectTimeout).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);
        }
        try {
            log.info("Executing request " + httpGet.getRequestLine());
            //请求数据
            CloseableHttpResponse response = httpClient.execute(httpGet);
            log.info(response.getStatusLine().toString());
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                responseBody = EntityUtils.toString(entity);
                log.info("responseBody ---> " + responseBody);
                //EntityUtils.consume(entity);
            } else {
                log.info("http return status error:" + status);
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(httpClient != null)
                    httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    /**
     * get
     *
     * @param url     请求的url
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {

        log.info(" get [url="+url+"] ");

        String responseBody = "";
        //支持https
        CloseableHttpClient httpClient = getHttpClient();

        HttpGet httpGet = new HttpGet(url);
        if (SetTimeOut) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SocketTimeout)
                    .setConnectTimeout(ConnectTimeout).build();//设置请求和传输超时时间
            httpGet.setConfig(requestConfig);
        }
        try {
            log.info("Executing request " + httpGet.getRequestLine());
            //请求数据
            CloseableHttpResponse response = httpClient.execute(httpGet);
            log.info(response.getStatusLine().toString());
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                responseBody = EntityUtils.toString(entity);
                log.info("responseBody ---> " + responseBody);
                //EntityUtils.consume(entity);
            } else {
                log.info("http return status error:" + status);
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.close();
        }
        return responseBody;
    }

    /**
     * post
     *
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @param params  post form 提交的参数
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> queries, Map<String, String> params) throws IOException {

        log.info(" post [url="+url+"] , [queries="+queries+"] , [params="+params+"]");

        String responseBody = "";
        //支持https
        CloseableHttpClient httpClient = getHttpClient();

        StringBuilder sb = new StringBuilder(url);

        if (queries != null && !queries.isEmpty()) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }

        //指定url,和http方式
        HttpPost httpPost = new HttpPost(sb.toString());
        if (SetTimeOut) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SocketTimeout)
                    .setConnectTimeout(ConnectTimeout).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
        }
        //添加参数
        List<BasicNameValuePair> nvps = new ArrayList<>();
        if (params != null && params.keySet().size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        //请求数据
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            log.info(response.getStatusLine().toString());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                responseBody = EntityUtils.toString(entity);
                log.info("responseBody ---> " + responseBody);
                //EntityUtils.consume(entity);
            } else {
                log.info("http return status error:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
        return responseBody;
    }

    /**
     * post
     *
     * @param url     请求的url
     * @param params  post form 提交的参数
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params){

        log.info(" post [url="+url+"] , [params="+params+"]");

        String responseBody = "";
        //支持https
        CloseableHttpClient httpClient = getHttpClient();

        //指定url,和http方式
        HttpPost httpPost = new HttpPost(url);
        if (SetTimeOut) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SocketTimeout)
                    .setConnectTimeout(ConnectTimeout).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
        }
        //添加参数
        List<BasicNameValuePair> nvps = new ArrayList<>();
        if (params != null && params.keySet().size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //返回数据
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            response = httpClient.execute(httpPost);
            log.info(response.getStatusLine().toString());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                responseBody = EntityUtils.toString(entity);
                log.info("responseBody ---> " + responseBody);
                //EntityUtils.consume(entity);
            } else {
                log.info("http return status error:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }


    /**
     * post
     *
     * @param url     请求的url
     * @param jsonString  post form 提交的参数
     * @param token
     * @return
     * @throws IOException
     */
    public static String post(String url, String jsonString, String token){

        log.info(" post [url="+url+"] , [queries="+jsonString+"] , [token="+token+"]");

        String responseBody = "";

        //支持https
        CloseableHttpClient httpClient = getHttpClient();

        //指定url,和http方式
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("token", token);
        httpPost.setHeader("content-type", "application/json");
        httpPost.setHeader("charset", HTTP.UTF_8);

        if (SetTimeOut) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(SocketTimeout)
                    .setConnectTimeout(ConnectTimeout).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
        }
        //返回数据
        CloseableHttpResponse response = null;
        try {
            //添加参数
            httpPost.setEntity(new StringEntity(jsonString, HTTP.UTF_8));
            response = httpClient.execute(httpPost);
            log.info(response.getStatusLine().toString());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                responseBody = EntityUtils.toString(entity);
                log.info("responseBody ---> " + responseBody);
                //EntityUtils.consume(entity);
            } else {
                log.info("http return status error:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("https post 调用异常");
        } finally {
            try {
                if(response != null) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    public static void main(String[] args){
        String url = "https://192.168.1.44:8443/hellogood_ws/auth/createToken.do";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("source", StaticFileUtil.getProperty("systemConfig", "ADMIN_SOURCE"));
        post(url, requestMap);

//        String url = "https://192.168.1.44:8443/hellogood_ws/privateLetter/batchAddIMMessage.do";
//        JSONObject json = new JSONObject();
//        String[] targetCodes = new String[]{"80001105"};
//        json.accumulate("fromUserCode", 40001913);
//        json.accumulate("userCodeList", targetCodes);// 单个发送
//        json.accumulate("content", org.apache.commons.codec.binary.Base64.encodeBase64String("测试推送001".getBytes()));// 发送内容
//        json.accumulate("type", "text");
//        String result = post(url, json.toString(), "123");
//        log.info(result);
    }
}
