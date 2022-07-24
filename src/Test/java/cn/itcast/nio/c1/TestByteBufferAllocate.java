package cn.itcast.nio.c1;

import java.nio.ByteBuffer;

public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());

//        class java.nio.HeapByteBuffer    --java堆内存 读写效率低  受到GC影响
//        class java.nio.DirectByteBuffer  --直接内存 读写效率高（少一次拷贝） 不会受到GC影响  很宝贵，分配的效率的，可能造成内存泄露

    }
}
