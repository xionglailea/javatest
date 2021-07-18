package trans;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * Created by xiong on 2017/1/22.
 */
public class HttpServerTest {
    public static void main(String[] args) {
        InetSocketAddress inetSock = new InetSocketAddress(10001);
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(inetSock, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpServer.createContext("/", new myHandler1());
        httpServer.createContext("/args",new myHandler2());
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.start();
        System.out.println("HttpServer Test Start!");
    }

    static class myHandler1 implements HttpHandler{
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String responseString = "<font color='#ff0000'>Hello! This a HttpServer!</font>";
            //设置响应头
            System.out.println(httpExchange.getRequestMethod());
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            String s = null;
            while ( (s = reader.readLine()) != null){ //使用post方法的时候 需要读取请求体里面的内容
                System.out.println(s);
            }
            final Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/html");
            httpExchange.sendResponseHeaders(200, responseString.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(responseString.getBytes());
            os.close();
            Set<String> headers = httpExchange.getRequestHeaders().keySet();
            for(String ss : headers){
                System.out.println(ss + ":" + httpExchange.getRequestHeaders().get(s));
            }

        }
    }

    static class myHandler2 implements HttpHandler{
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            URI uri = httpExchange.getRequestURI();
            System.out.println(uri.getPath());
            System.out.println(uri.getQuery());
            System.out.println(httpExchange.getRequestMethod());
            final Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            httpExchange.sendResponseHeaders(200,0);
            String args = uri.getQuery();
            String[] all = args.split("&");
            StringBuilder sb = new StringBuilder();
            for(String s : all){
                sb.append(s);
                sb.append(";");
            }
            httpExchange.getResponseBody().write(sb.toString().getBytes());
            httpExchange.close();
        }
    }
    //一个http请求由请求行（包括请求方法，URL，http协议版本组成）、请求头部(键值对组成，每行一对；)、空行（用来通知服务器不再有请求头）、
    // 和请求数据（通常get方法，该部分为空post方法，需要读取里面的内容）
    //在HttpExchange中可以读到这些内容
}
