package cn.itcast.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        //FileChannel
        //1.输入输出流
        //2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            //准备缓存区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            //从channel读数据,向buffer写
//            int len = channel.read(buffer);
//            //打印buffer内容
//            buffer.flip();//切换到读模式
//
//            while (buffer.hasRemaining()){
//                byte b = buffer.get();
//                System.out.println((char)b);
//            }
            while (true){
                int len = channel.read(buffer);
                log.info("读取到的字节{}",len);
                if(len == -1){
                    break;
                }
                buffer.flip();
                while (buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.info("实际字节{}",(char) b);
                }
                //切换成写模式
                buffer.clear();
            }


        } catch (IOException e) {
        }
    }
}
