package com.hellogood.http.task;

import com.hellogood.utils.HttpsUtils;
import com.hellogood.utils.StaticFileUtil;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 通知任务
 * Created by kejian on 2017/9/30.
 */
public class NoticeTask extends Thread{

    Logger logger = LoggerFactory.getLogger(NoticeTask.class);

    private HttpServletRequest request;
    private Map<String, String> params;

    public NoticeTask(HttpServletRequest request, Map<String, String> params){
        this.request = request;
        this.params = params;
    }

    public NoticeTask(Map<String, String> params){
        this.params = params;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }


    public void run(){
        notice(request, params);
    }

    /**
     * 消息通知
     *
     * @param params
     */
    public String notice(HttpServletRequest request, Map<String, String> params) {
        logger.info("消息通知，请求参数..." + params);
        String token = getRequestToken(request);
        String url = StaticFileUtil.getProperty("systemConfig",
                "INTERFACE_SERVER")
                + StaticFileUtil.getProperty("systemConfig", "MESSAGE");
        JsonObject jsonObject = new JsonObject();
        for (String key : params.keySet()) {
            jsonObject.addProperty(key, params.get(key));
        }
        String result = HttpsUtils.post(url, jsonObject.toString(), token);
        return result;
    }

    /**
     * 消息通知
     *
     * @param params
     */
    public String notice(Map<String, String> params) {
        logger.info("消息通知，请求参数..." + params);
        String token = getRequestToken(request);
        String url = StaticFileUtil.getProperty("systemConfig",
                "INTERFACE_SERVER")
                + StaticFileUtil.getProperty("systemConfig", "MESSAGE");
        JsonObject jsonObject = new JsonObject();
        for (String key : params.keySet()) {
            jsonObject.addProperty(key, params.get(key));
        }
        String result = HttpsUtils.post(url, jsonObject.toString(), token);
        return result;
    }

    public String getRequestToken(HttpServletRequest request) {
        String serverUrl = StaticFileUtil.getProperty("systemConfig",
                "INTERFACE_SERVER");
        String url = serverUrl
                + StaticFileUtil.getProperty("systemConfig", "CREATE_TOKEN");
        Map<String, String> tokenRequestMap = new HashMap<>();
        tokenRequestMap.put("source",
                StaticFileUtil.getProperty("systemConfig", "ADMIN_SOURCE"));
        JSONObject jsonObject = new JSONObject(HttpsUtils.post(url,
                tokenRequestMap));
        String token = String.valueOf(jsonObject.get("token"));
        return token;
    }
}
