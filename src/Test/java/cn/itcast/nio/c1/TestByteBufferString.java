package cn.itcast.nio.c1;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.itcast.nio.c1.ByteBufferUtil.debugAll;

public class TestByteBufferString {

    public static void main(String[] args) {
        //1.字符串转ByteBuffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        debugAll(buffer1);

        //2.Charset  自动切换到读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer2);

        //3.wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer3);

        String str1 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str1);

        //还处于写模式
        String str2 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(str2);

    }
}
