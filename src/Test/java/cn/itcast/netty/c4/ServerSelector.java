package cn.itcast.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

@Slf4j
public class ServerSelector {
    public static void main(String[] args) throws IOException {

        //1.创建selector，管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);


        //2.建立channel与selector的联系（注册）
        //selectionKey 就是将来事件发生后，通过它可以知道那个事件和那个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        //key 只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key : {}",sscKey);

        ssc.bind(new InetSocketAddress(8080));

        while (true){
           //3.select方法  阻塞直到绑定事件发生
            //select 在事件未处理时，它不会阻塞  事件发生后要么处理要么取消
            selector.select();

            //4.处理事件   selectedKeys是一个set集合，内部包含了所有事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();//accept ,read
            while (iter.hasNext()){
                SelectionKey key = iter.next();
                //select 在事件发生后，就会将相关的 key 放入 selectedKeys 集合，但不会在处理完后从 selectedKeys 集合中移除，需要我们自己编码删除
                iter.remove();
                log.debug("key : {}",sscKey);
                //5.区分事件类型
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",sc);
                }else if(key.isReadable()){
                    try {
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer);//如果是正常断开，read的返回值是-1
                        if(read==-1){
                            key.cancel();
                        }else {
                            buffer.flip();
                            debugAll(buffer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();//因为客户端断开了，因此需要取消key，从selector的key集合中删除
                    }
                }

//                key.cancel();
            }
        }



    }
}
