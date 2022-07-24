package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.itcast.nio.c1.ByteBufferUtil.debugRead;

@Slf4j
public class ServerNotBlocking {
    public static void main(String[] args) throws IOException {
        //使用nio 来理解阻塞模式       单线程

        //0.ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        //1.创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);   //      --非阻塞模式

        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        //3.连接的集合
        List<SocketChannel> channels = new ArrayList<>();

        while (true){
            //4.accept 建立与客户端的通信
            //log.info("cennecting...");
            SocketChannel sc = ssc.accept(); //非阻塞方法,线程还会运行 ，如果没有建立连接，accept返回null
            if(sc!=null){
                log.info("cennected...{}",sc);
                sc.configureBlocking(false);    //SocketChannel非阻塞
                channels.add(sc);
            }

            for (SocketChannel channel:channels) {

                //5.接收客户端发送的信息
                //log.info("before read...{}",channel);
                int read = channel.read(buffer);//非阻塞方法，线程还会运行   如果没有读到数据，read返回0
                if (read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }
            }
        }



    }
}
