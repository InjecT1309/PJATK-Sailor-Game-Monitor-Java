import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class SailorGameMonitor {
    public static void main(String[] args) throws Exception {
        System.out.println("Enter my ip and port [ip:port]");
        String arguments[] = new Scanner(System.in).nextLine().split(":");
        String ip = arguments[0];
        int port = Integer.parseInt(arguments[1]);
        HttpServer server = HttpServer.create(new InetSocketAddress(ip, port), 0);
        server.createContext("/monitor", e -> {
            BufferedReader read = new BufferedReader(new InputStreamReader(e.getRequestBody()));
//            System.out.println(e.getRequestHeaders().entrySet());
            System.out.println(read.readLine());
            String response = "OK";
            e.sendResponseHeaders(200, response.length());
            OutputStream os = e.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        System.out.println("Monitor is listening on " + server.getAddress().getAddress() + ":" + server.getAddress().getPort() + "/monitor");
        server.start();
    }
}
