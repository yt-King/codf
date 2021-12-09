package com.zufe.codf.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
/**
 * @author 应涛
 * @date 2021/12/5
 * @function：
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "callbackFilter")
public class ResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        MyResponseWrapper responseWrapper = new MyResponseWrapper((HttpServletResponse) response);
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        filterChain.doFilter(request, responseWrapper);
        System.out.println("httpServletRequest.getRequestURL() = " + httpServletRequest.getRequestURL());
        System.out.println("httpServletResponse.getStatus() = " + httpServletResponse.getStatus());
        System.out.println("httpServletResponse.getHeader(\"location\") = " + httpServletResponse.getHeader("location"));

        String str = httpServletResponse.getHeader("location");
        //把返回值输出到客户端
        if(302==httpServletResponse.getStatus()){
            responseWrapper.setStatus(200);
            responseWrapper.setHeader("location","");
            PrintWriter writer = null;
            responseWrapper.setCharacterEncoding("UTF-8");
            responseWrapper.setContentType("application/json; charset=utf-8");
            try {
                writer = responseWrapper.getWriter();
                writer.print(str);
                System.out.println("str = " + str);
            } catch (IOException e) {
                log.error("response error", e);
            } finally {
                if (writer != null)
                    writer.close();
            }
        }
       return;
    }


    private class MyResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayOutputStream buffer;
        private ServletOutputStream out;

        public MyResponseWrapper(HttpServletResponse httpServletResponse) {
            super(httpServletResponse);
            buffer = new ByteArrayOutputStream();
            out = new WrapperOutputStream(buffer);
        }
        @Override
        public ServletOutputStream getOutputStream()
                throws IOException {
            return out;
        }

        @Override
        public void flushBuffer()
                throws IOException {
            if (out != null) {
                out.flush();
            }
        }

        public byte[] getContent()
                throws IOException {
            flushBuffer();
            return buffer.toByteArray();
        }

        class WrapperOutputStream extends ServletOutputStream {
            private ByteArrayOutputStream bos;

            public WrapperOutputStream(ByteArrayOutputStream bos) {
                this.bos = bos;
            }

            @Override
            public void write(int b)
                    throws IOException {
                bos.write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener arg0) {
            }
        }
    }

    @Override
    public void init(FilterConfig arg0)
            throws ServletException {
    }

    @Override
    public void destroy() {
    }

}

