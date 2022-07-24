package cn.itcast.nio.c1;

import java.nio.ByteBuffer;

import static cn.itcast.nio.c1.ByteBufferUtil.debugAll;

public class TestByteBufferReadWrite {
    public static void main(String[] args) {

        ByteBuffer buffer =  ByteBuffer.allocate(10);
        //0X表示16进制  61 'a'
        buffer.put((byte)0X61);
        debugAll(buffer);

        buffer.put(new byte[]{0X62,0X63,0X64});
        debugAll(buffer);

//        System.out.println(buffer.get());

        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer);

        buffer.compact();
        debugAll(buffer);

        buffer.put(new byte[]{0X65,0X66});
        debugAll(buffer);
    }
}
