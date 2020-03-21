package buffer;

import java.nio.ByteBuffer;

/**
 * 直接缓冲区与非直接缓冲区:
 *
 * 1.非直接缓冲区: 通过allocate()方法创建缓冲区,将缓冲区建立在了JVM的内存中(堆中)，是普通的数组. JVM可以直接管控.
 * 				  但在程序与io设备(如磁盘)打交道时，数据在操作系统内存和JVM内存(堆)上有一个copy的过程。
 *
 * 2.直接缓冲区: 通过allocateDirect()方法创建缓冲区，将缓冲区建立在操作系统的物理内存中,JVM无法直接管控，
 * 				省去了中间的copy过程，在某种情况下可以提高效率.
 *
 * 				在 Buffer类中有一个long address;属性指向那块堆外内存的地址,保持着JVM和它的联系,不然的话JVM就不能在里面读写数据了.
 *
 * 				缺点:
 * 					1.缓冲区的控制权在操作系统，里面的数据我们不容易控制
 * 					2.不安全
 * 					3.资源耗费大
 * 					4.何时销毁内存不能确定
 *
 *
 * 3.操作系统可以直接操作JVM上的内存，那为什么操作系统不直接操作那块堆上的
 * 	 缓冲区而是先把数据copy到操作系统内存上，然后再去操作呢？
 *
 * 	 答:因为如果在操作系统操作JVM的内存时,如果在那个时候在那块内存上JVM发起了GC垃圾回收,
 * 	 	在垃圾回收的时候,会出现对象的移动,这时数据就乱套了,所以要把缓冲区的数据先copy到
 * 	 	堆外内存,然后操作系统再进行相应的IO操作.
 *
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/5/31
 */
public class BufferTest2 {
    
    public static void main(String[] args) {
        //1.创建直接缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(1024);
        //2.判断是不是直接缓冲区
        System.out.println(buf1.isDirect());
        
        //1.创建直接缓冲区
        ByteBuffer buf2 = ByteBuffer.allocateDirect(1024);
        //2.判断是不是直接缓冲区
        System.out.println(buf2.isDirect());
    }
}
