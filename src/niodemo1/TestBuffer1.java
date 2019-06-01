package niodemo1;

import java.nio.ByteBuffer;

/**
 *
 * 1.缓冲区（Buffer）：用于数据的存取。缓冲区底层是数组，用于存储不同是数据类型的数据
 *
 *                    根据数据类型的不同（boolean除外），提供了不同的缓冲区：
 *                       ByteBuffer --> **最常用**
 *                       CharBuffer
 *                       ShortBuffer
 *                       IntBuffer
 *                       LongBuffer
 *                       FloatBuffer
 *                       DoubleBuffer
 *
 *                    他们的管理方式几乎一样：
 *                       A.通过allocate()方法创建缓冲区
 *                       B.通过put()方法将数据存入缓冲区
 *                       C.通过get()方法获取缓冲区中的数据
 *
 *
 * 2.缓冲区中的四个核心属性(在Buffer类中定义)：
 *
 *  private int position = 0;  ----->>  位置，表示缓冲区中正在操作的数据的位置
 *  private int limit;  ----->>  界限，表示缓冲区中可以操作的数据大小，（limit后面的数据不能进行读写）
 *  private int capacity;  ----->>  容量，表示缓冲区中最大存储数据的容量，一旦声明就不能改变。也就是数组一旦创建它的大小就不能改变了
 *  private int mark = -1;  ----->>  标记，记录当前position的位置，之后可通过reset()方法使当前position恢复到mark()所记录的位置
 *
 *  大小关系: 0 <= mark <= position <= limit <= capacity
 *
 *
 *  ************缓冲区中可操作的数据位于position和limit之间*************
 *
 *
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/5/31
 */
public class TestBuffer1 {

    public static void main(String[] args) {

        System.out.println("-----------------------------allocate()------------------------------------");
        //1，分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------put()----------------------------------------");
        //2.利用put()方法,存入数据到缓冲区
        String str = "wuyuan";
        buf.put(str.getBytes());//写数据模式
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------flip()----------------------------------------");
        /*3.切换为读数据的模式,也就是重置position和limit的位置，
         *  写入数据后，position在数据的下一个位置，而读数据的话position要位于要操作的数据的最前面
         *
         *  get()方法读取的是position和limit之间的数据，
         *  所以flip()方法所进行的操作是: limit = position; position = 0;
         */
        buf.flip();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------get()----------------------------------------");
        //4.利用get()方法读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);//get()方法获取的是position到limit之间的数据
        System.out.println(new String(dst, 0, dst.length));

        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------mark()和reset()----------------------------------------");
        //5.mark()和reset()方法，标记和恢复position的位置
        buf.flip();
        byte[] dst1 = new byte[buf.limit()];
        buf.get(dst1, 1, 2);
        System.out.println(new String(dst1, 0, dst1.length));
        System.out.println(buf.position());

        buf.mark();
        buf.get(dst1, 2, 3);
        System.out.println(new String(dst1, 0, dst1.length));
        System.out.println(buf.position());

        buf.reset();
        System.out.println(buf.position());

        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------hasRemaining()和remaining()----------------------------------------");
        //6.判断缓冲区中是否还存在可操作的数据并输出可操作数据的数量
        if (buf.hasRemaining()) {
            //输出可操作的数据的数量,也就是position和limit之间还有多少数据 == (limit-position)
            System.out.println(buf.remaining());
        }

        System.out.println("-----------------------------clear()----------------------------------------");
        /*6.清空缓冲区
         *   clear()方法只是重置了position和limit的位置，里面的数据处于“被遗忘状态”
         *   使 position = 0; limit = capacity;
         */
        buf.clear();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //clear()后，缓冲区中的数据还在
        byte[] dstq = new byte[buf.limit()];
        buf.get(dstq);
        System.out.println(new String(dstq));

    }

}
