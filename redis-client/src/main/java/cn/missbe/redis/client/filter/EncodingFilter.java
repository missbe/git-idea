package cn.missbe.redis.client.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-30 下午5:59
 *   @author lyg
 *   @version 1.0
 *   @Description 设置请求和回复编码为UTF-8
 **/
@WebFilter(urlPatterns = {"/redis/*"},initParams = {@WebInitParam(name = "charEncoding", value = "UTF-8")})
public class EncodingFilter implements Filter {

    private String charEncoding;

    public EncodingFilter() {
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        charEncoding = fConfig.getInitParameter("charEncoding");
        if (charEncoding == null || charEncoding.equals("null")) {
            throw new ServletException("EncodingFilter中的编码设置为空");
        }
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        PrintUtil.print(request.getRemoteAddr() + ":将请求编码设置为UTF-8", SystemLog.Level.info);
        // 将设置的编码与请求的编码进行比较，若不同，则将请求的编码设置为设置的编码
        if (!charEncoding.equals(request.getCharacterEncoding())) {
            request.setCharacterEncoding(charEncoding);
        }
//        PrintUtil.print(response.getCharacterEncoding() + ":将响应编码设置为UTF-8", SystemLog.Level.info);
        // 将相应的编码设置为设置的编码
        response.setCharacterEncoding(charEncoding);
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // TODO Auto-generated method stub
    }
}