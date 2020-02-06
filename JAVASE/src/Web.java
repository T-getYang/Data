import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Web {
    private static Socket scoket;
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        scoket = new Socket("192.168.6.16",8080);
        System.out.println("你成功的连接进了聊天室");

        new Thread(){
            @Override
            public void run(){
                try {
                    // 一直读
                    InputStream is = scoket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int lenght = -1;

                    while ((lenght = is.read(bytes)) != -1) {	// 读取客户端的发送的消息
                        System.out.println(new String(bytes, 0, lenght,"utf-8"));
                    }

                } catch (Exception e) {
                    System.out.println("聊天室已关闭！");
                }
            }
        }.start();

        while (true) {
            String nextLine = sc.nextLine();
            OutputStream os = scoket.getOutputStream();
            os.write(nextLine.getBytes("UTF-8"));
        }
    }

}
