package cn.itcast.netty.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

public class MultiThreadServer {
    public static void main(String[] args) {

    }

   static class Worker implements Runnable{
        private Thread  thread;
        private Selector selector;
        private String name;
        private volatile boolean start = false;//还未初始化

        public Worker(String name) {
            this.name = name;
        }

        //初始化线程，和selector
        public void register() throws IOException {
            if(!start){
                thread = new Thread(this,name);
                thread.start();
                selector = Selector.open();
            }
        }

        @Override
        public void run() {
            while (true){
                try {
                    selector.select();
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()){
                        SelectionKey key = iter.next();
                        iter.remove();
                        if(key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel =(SocketChannel) key.channel();
                            channel.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
