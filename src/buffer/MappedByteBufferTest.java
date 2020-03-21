package buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射文件 MappedByteBuffer：
 *      普通的io操作在内存中会有文件的拷贝过程(JVM内存<用户态>和操作系统内存<内核态>之间的拷贝，OS会有用户态和内核态的转换)，
 *      内存映射文件时是将某一段的堆外内存地址和文件对象的某一部分建立起映射关系，此时并没有拷贝数据到内存中去，
 *      当代码中使用映射文件中的某一段时直接读取文件到JVM内存中，免去了文件的拷贝。
 *      内存映射文件的效率比标准IO高的重要原因就是因为少了把数据拷贝到OS内核缓冲区这一步，数据直接被拷贝到进程的地址空间中。
 *
 * MappedByteBuffer作用：
 *      可以让文件直接在 JVM内存中进行修改，而操作系统不需要拷贝一次，
 *      直接缓冲区 DirectByteBuffer是 MappedByteBuffer的子类。
 *
 * @author wuyuan
 * @date 2020/3/21
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccess = new RandomAccessFile("file1.txt", "rw");
        FileChannel fileChannel = randomAccess.getChannel();
        
        // 获取文件映射MappedByteBuffer -- 0-5范围映射到JVM内存中，内容可以直接在内存操作
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        
        // 修改文件指定位置的值
        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(1, (byte) 'b');
        
        // 关闭文件
        randomAccess.close();
    }
}
