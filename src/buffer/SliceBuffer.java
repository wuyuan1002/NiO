package buffer;

import java.nio.ByteBuffer;

/**
 * Slice得到的新buffer与原来的buffer共享相同的底层数据，两者对共有数据所作的修改都互相影响
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/1
 */
public class SliceBuffer {
    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get()+" == buffer原来的");
        }

//        System.err.println("---------------------");

        buffer.position(2).limit(6);
        ByteBuffer slicebuffer = buffer.slice();

//        System.out.println("---------");
        while (slicebuffer.hasRemaining()) {
            System.out.println(slicebuffer.get()+" ** slicebuffer的");
        }
        slicebuffer.flip();

//        System.out.println("---------");
        for (int i = 0; i < slicebuffer.capacity(); ++i) {
            byte b = slicebuffer.get(i);
            slicebuffer.put((byte) (b * 2));
        }

        buffer.position(0).limit(buffer.capacity());
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get()+" -- 修改完的");
        }

    }
}
