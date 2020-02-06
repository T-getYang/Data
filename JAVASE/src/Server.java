import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server{
    // 所有的客户端
    private List<Socket> clients = new ArrayList<>();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    String format = sf.format(date);
    public static void main(String[] args) throws IOException {
        new Server().init();
    }

    public void init() throws IOException {
        // 建立连接
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器启动:" + serverSocket);
        while(true){
            // 等待请求
            Socket client = serverSocket.accept();
            clients.add(client);
            String name = client.getInetAddress().getHostAddress()+ ":" + client.getPort()+"- "+format ;
            System.out.println(name+": 上线了");
            repal(name+": 上线了");

            // 线程: "同时执行"
            new Thread() {
                @Override
                public void run() {
                    try {
                        /* 客户端的输入流 */;
                        InputStream is = client.getInputStream();
                        byte[] bytes = new byte[1024];
                        int lenght = -1;
                        while ((lenght = is.read(bytes)) != -1) {
                            String msg = new String(bytes, 0, lenght);
                            System.out.println(client + ":客户端消息:" + msg);
                            Pattern compile = Pattern.compile("^@([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{5})\\s+.*$");
                            // Pattern compile = Pattern.compile("^@([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})\\s+.*$");
                            Matcher matcher = compile.matcher(msg);
                            if(matcher.find()){ // 私聊
                                String ip = matcher.group(1);
                                // 向客户端发送消息
                                for (Socket socket : clients) {
                                    System.out.println("bool"+String.format("%s:%s", socket.getInetAddress().getHostAddress(), socket.getPort()).equals(ip));
                                    System.out.println(ip);
                                    String format = String.format("%s:%s", socket.getInetAddress().getHostAddress(), socket.getPort());
                                    System.out.println(format);
                                    if(format.equals(ip)){
                                        try {
                                            OutputStream os = socket.getOutputStream();
                                            os.write(msg.getBytes("UTF-8"));
                                        } catch (Exception e) {
                                            // 用户下线
                                            msg ="发送失败,对方不在线";
                                            OutputStream os = socket.getOutputStream();
                                            os.write(msg.getBytes("UTF-8"));
                                            clients.remove(socket);
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }else{
                                // 发送的消息
                                String name = client.getInetAddress().getHostAddress() + ":" + client.getPort();
                                msg = name +"- "+format+ "\n" + msg;
                                broadcast(client,msg);
                            }
                        }
                    }catch (Exception e){
                        System.out.println(name+"下线了");
                        repal(name+"下线了");
                        clients.remove(client);
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public void broadcast(Socket clint,String msg) {
        // 向客户端发送消息
        for (Socket socket : clients) {
            if(clint!=socket){
                try {
                    OutputStream os = socket.getOutputStream();
                    os.write(msg.getBytes("UTF-8"));
                } catch (Exception e) {
                    // 用户下线
                    clients.remove(socket);
                    e.printStackTrace();
                }
            }
        }
    }
    public void repal(String msg) {
        // 向客户端发送消息
        for (Socket socket : clients) {
            try {
                OutputStream os = socket.getOutputStream();
                os.write(msg.getBytes("UTF-8"));
            } catch (Exception e) {
                // 用户下线
                clients.remove(socket);
                e.printStackTrace();
            }
        }
    }
}

