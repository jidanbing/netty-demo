package cn.itcast.nio.c1;

import java.nio.ByteBuffer;

import static cn.itcast.nio.c1.ByteBufferUtil.debugAll;

public class Test {

    //网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
    //        但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
    //
    //        * Hello,world\n
    //        * I'm zhangsan\n
    //        * How are you?\n
    //
    //        变成了下面的两个 byteBuffer (黏包，半包)
    //
    //        * Hello,world\nI'm zhangsan\nHo
    //        * w are you?\n
    //
    //        现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        //                     11            24
        buffer.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(buffer);

        buffer.put("w are you?\nhaha!\n".getBytes());
        split(buffer);
    }
    private static void split(ByteBuffer buffer) {
        buffer.flip();
        int oldLimit = buffer.limit();
        for (int i = 0; i < oldLimit; i++) {
            if (buffer.get(i) == '\n') {
                System.out.println(i);
                ByteBuffer target = ByteBuffer.allocate(i + 1 - buffer.position());
                // 0 ~ limit
                buffer.limit(i + 1);
                target.put(buffer); // 从source 读，向 target 写
                debugAll(target);
                buffer.limit(oldLimit);
            }
        }
        buffer.compact();
    }
}
