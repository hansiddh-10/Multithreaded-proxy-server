package JunitThreadPool;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
public class Server {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        System.out.println("Multithreaded Server running on port " + PORT);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.execute(() -> handleClient(clientSocket));
        }
    }
    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
            }
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/plain");
            out.println();
            out.println("Multithreaded Response from Java Web Server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
