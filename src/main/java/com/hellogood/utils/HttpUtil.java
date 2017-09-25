package com.hellogood.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javapns.org.json.JSONException;
import javapns.org.json.JSONTokener;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hellogood.exception.BusinessException;

public class HttpUtil {
    private final static Integer CONNECTION_TIMEOUT = 2 * 1000;// 设置请求超时
    private final static Integer SO_TIMEOUT = 2 * 1000; // 设置等待数据超时时间10秒钟

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String get(String uri, String token) {
        logger.info("请求URL： " + uri);
        String result = null;
        HttpClient httpclient = new HttpClient();
        GetMethod method = new GetMethod(uri);
        method.setRequestHeader("token", token);
        try {
            HttpMethodParams params = method.getParams();
            params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    CONNECTION_TIMEOUT);
            params.setParameter("token", token);
            params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
            int statusCode = httpclient.executeMethod(method);
            //调用返回状态
            if (statusCode == HttpStatus.SC_OK) {
                result = method.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("httpClient  get 调用异常。");
        } finally {
            method.releaseConnection();
            httpclient.getHttpConnectionManager().closeIdleConnections(0);
        }
        logger.info("返回：" + result);
        return result;
    }

    public static String get(String uri) {
        logger.info("请求URL： " + uri);
        String result = null;
        HttpClient httpclient = new HttpClient();
        GetMethod method = new GetMethod(uri);
        try {
            HttpMethodParams params = method.getParams();
            params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    CONNECTION_TIMEOUT);
            params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
            int statusCode = httpclient.executeMethod(method);
            //调用返回状态
            if (statusCode == HttpStatus.SC_OK) {
                result = method.getResponseBodyAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("httpClient  get 调用异常。");
        } finally {
            method.releaseConnection();
            httpclient.getHttpConnectionManager().closeIdleConnections(0);
        }
        logger.info("返回：" + result);
        return result;
    }

    /**
     * http post
     *
     * @param url
     * @param jsonString
     * @param token
     * @return
     */
    public static String post(String url, String jsonString, String token) {
        logger.info("请求URL: " + url);
        logger.info("请求TOKEN: " + token);
        logger.info("请求参数：" + jsonString);
        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("token", token);
        post.setHeader("content-type", "application/json");
        post.setHeader("charset", HTTP.UTF_8);
        HttpResponse resp = null;
        StringBuffer result = new StringBuffer();
        try {
            post.setEntity(new StringEntity(jsonString, HTTP.UTF_8));
            resp = httpClient.execute(post);
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = resp.getEntity();
                JSONTokener tokener = new JSONTokener(new InputStreamReader(entity.getContent(), HTTP.UTF_8));
                char c = '\0';
                while ((c = tokener.next()) != '\0') {
                    result.append(c);
                }
                logger.debug("http响应:" + result.toString());
            } else {
                logger.info("请求失败, 错误代码" + resp.getStatusLine().getStatusCode());
            }
        } catch (HttpException e) {
            e.printStackTrace();
            throw new BusinessException("httpClient  post 调用异常。");
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("httpClient  post 调用异常。");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        logger.info("返回结果= " + result);
        return result.toString();
    }

    public static String post(String uri, Map<String, String> map) {

        logger.info("请求URL: " + uri);
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(uri);
        method.setRequestHeader("token", map.get("token"));
        String reslut = null;
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            method.setParameter(entry.getKey(), entry.getValue());
            jsonObject.append(entry.getKey(), entry.getValue());
        }
        logger.info("参数：" + jsonObject.toString());
        try {
            int code = client.executeMethod(method);
            HttpMethodParams params = method.getParams();
            params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    CONNECTION_TIMEOUT);
            params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
            logger.info("HttpStatus code:"+code);
            if (code == HttpStatus.SC_OK) {
                reslut = method.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
            throw new BusinessException("httpClient  post 调用异常。");

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("httpClient  post 调用异常。");
        } finally {
            method.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
        logger.info("返回结果：" + reslut);
        return reslut;
    }

    public static void main(String[] args) {
//		
//		Map<String, String> map=new HashMap<String, String>();
//		map.put("source", "admin_source");
//		JSONObject a = new JSONObject(post(
//				"http://192.168.1.23:8080/hellogood_ws/auth/createToken.do",map));
//		String token=(String) a.get("token");
//		System.out.println(get("http://192.168.1.23:8080/hellogood_ws/appointment/send/26.do",token));
//		);
    	System.out.println((int)'\0');
    }
}
