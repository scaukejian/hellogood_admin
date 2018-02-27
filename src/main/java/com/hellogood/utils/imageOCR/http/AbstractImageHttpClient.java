package com.hellogood.utils.imageOCR.http;

import com.hellogood.utils.imageOCR.ClientConfig;
import com.hellogood.utils.imageOCR.exception.AbstractImageException;
import com.hellogood.utils.imageOCR.exception.ParamException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public abstract class AbstractImageHttpClient {
    protected ClientConfig config;
    protected HttpClient httpClient;
    
    private PoolingHttpClientConnectionManager connectionManager;
    private IdleConnectionMonitorThread idleConnectionMonitor;
    
    private RequestConfig requestConfig;

    public AbstractImageHttpClient(ClientConfig config) {
        super();
        this.config = config;
        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.connectionManager.setMaxTotal(config.getMaxConnectionsCount());
        this.connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsCount());
        this.httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        this.idleConnectionMonitor = new IdleConnectionMonitorThread(this.connectionManager);
        this.idleConnectionMonitor.start();
    }
    
    protected RequestConfig onGetConfig(){
        this.requestConfig =  RequestConfig.custom()
            .setConnectionRequestTimeout(this.config.getConnectionRequestTimeout())
            .setConnectTimeout(this.config.getConnectionTimeout())
            .setSocketTimeout(this.config.getSocketTimeout())
            .setProxy(this.config.getProxy())
            .build();
        return this.requestConfig;}
    
    protected abstract String sendPostRequest(HttpRequest httpRequest) throws AbstractImageException;

    protected abstract String sendGetRequest(HttpRequest httpRequest) throws AbstractImageException;

    public String sendHttpRequest(HttpRequest httpRequest) throws AbstractImageException {

        HttpMethod method = httpRequest.getMethod();
        if (method == HttpMethod.POST) {
            return sendPostRequest(httpRequest);
        } else if (method == HttpMethod.GET) {
            return sendGetRequest(httpRequest);
        } else {
            throw new ParamException("Unsupported Http Method");
        }
    }
    
    public void shutdown() {
        this.idleConnectionMonitor.shutdown();
    }
}
