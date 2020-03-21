package buffer;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁
 *
 * @author wuyuan
 * @date 2020/3/21
 */
public class FileLockText {
    public static void main(String[] args) throws Exception {
        // 创建随机文件访问对象 -- RandomAccessFile可以随机访问修改文件，可以指定访问文件中的哪个位置。他不是io体系的类，直接继承Object类。
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
