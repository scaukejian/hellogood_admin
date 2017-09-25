package com.hellogood.http.filter;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP访问过滤器
 */
public class GzipFilter implements Filter {

    public void destroy() {
    }

    /**
     * 判断浏览器是否支持GZIP
     *
     * @param request
     * @return
     */
    private static boolean isGZipEncoding(HttpServletRequest request) {
        boolean flag = false;
        String encoding = request.getHeader("Accept-Encoding");
        if (encoding.indexOf("gzip") != -1) {
            flag = true;
        }
        return flag;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String ext = FilenameUtils.getExtension(uri);
        if (isGZipEncoding(req)) {
            //需要过滤的扩展名：.htm,.html,.jsp,.js,.ajax,.css
            String gzipPattern= "htm, html, jsp, js, ajax, css";
            if(StringUtils.indexOf(gzipPattern, ext) > -1) {
                GzipResponseWrapper wrapper = new GzipResponseWrapper(resp);
                chain.doFilter(request, wrapper);
                byte[] gzipData = gzip(wrapper.getResponseData());
                resp.addHeader("Content-Encoding", "gzip");
                resp.setContentLength(gzipData.length);
                ServletOutputStream output = response.getOutputStream();
                output.write(gzipData);
                output.flush();
            }else{
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    public void init(FilterConfig arg0) throws ServletException {

    }

    private byte[] gzip(byte[] data) {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
        GZIPOutputStream output = null;
        try {
            output = new GZIPOutputStream(byteOutput);
            output.write(data);
        } catch (IOException e) {
        } finally {
            try {
                if(output != null) output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteOutput.toByteArray();
    }
}
