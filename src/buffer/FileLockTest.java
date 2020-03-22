package buffer;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁
 *
 * RandomAccessFile：（JDK1.4之后大多数方法被内存映射文件取代, 更推荐使用内存映射文件而不是RandomAccessFile）
 *
 *      可以自由访问和修改文件的任意位置。他不是io体系的类，直接继承Object类。使用很多 native方法实现了对文件的操作。
 *      由于可以直接跳到文件的任意位置来读写数据，所以要在文件末尾增加数据时就不用把文件都读到内存中了。
 *
 *      RandomAccessFile类包含了一个记录指针，用以标识当前读写处的位置，当程序新创建一个RandomAccessFile对象时，
 *      该对象的文件记录指针位于文件头（也就是0处）,当读/写了n个字节后，文件记录指针将会向后移动n个字节。
 *      除此之外，RandomAccessFile可以自由的移动记录指针，即可以向前移动，也可以向后移动。
 *      RandomAccessFile包含了以下两个方法来操作文件的记录指针.
 *
 *           long getFilePointer(); 返回文件记录指针的当前位置
 *           void seek(long pos); 将文件记录指针定位到pos位置
 *
 * @author wuyuan
 * @date 2020/3/21
 */
public class FileLockTest {
    public static void main(String[] args) throws Exception {
        // 创建随机文件访问对象
        RandomAccessFile rf = new RandomAccessFile("wuyuan.txt", "rw");
        // 获取文件通道
        FileChannel fc = rf.getChannel();
        
        // 获取文件锁 -- 从第3个位置开始，锁6个，为共享锁。
        FileLock lock = fc.lock(3, 6, true);
        
        // 输出是否有效
        System.out.println(lock.isValid());
        // 输出是否为共享锁
        System.out.println(lock.isShared());
        
        // 释放文件锁
        lock.release();
        // 关闭文件连接
        rf.close();
    }
}
