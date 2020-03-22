package buffer;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * �ļ���
 *
 * RandomAccessFile����JDK1.4֮�������������ڴ�ӳ���ļ�ȡ��, ���Ƽ�ʹ���ڴ�ӳ���ļ�������RandomAccessFile��
 *
 *      �������ɷ��ʺ��޸��ļ�������λ�á�������io��ϵ���ֱ࣬�Ӽ̳�Object�ࡣʹ�úܶ� native����ʵ���˶��ļ��Ĳ�����
 *      ���ڿ���ֱ�������ļ�������λ������д���ݣ�����Ҫ���ļ�ĩβ��������ʱ�Ͳ��ð��ļ��������ڴ����ˡ�
 *
 *      RandomAccessFile�������һ����¼ָ�룬���Ա�ʶ��ǰ��д����λ�ã��������´���һ��RandomAccessFile����ʱ��
 *      �ö�����ļ���¼ָ��λ���ļ�ͷ��Ҳ����0����,����/д��n���ֽں��ļ���¼ָ�뽫������ƶ�n���ֽڡ�
 *      ����֮�⣬RandomAccessFile�������ɵ��ƶ���¼ָ�룬��������ǰ�ƶ���Ҳ��������ƶ���
 *      RandomAccessFile�������������������������ļ��ļ�¼ָ��.
 *
 *           long getFilePointer(); �����ļ���¼ָ��ĵ�ǰλ��
 *           void seek(long pos); ���ļ���¼ָ�붨λ��posλ��
 *
 * @author wuyuan
 * @date 2020/3/21
 */
public class FileLockTest {
    public static void main(String[] args) throws Exception {
        // ��������ļ����ʶ���
        RandomAccessFile rf = new RandomAccessFile("wuyuan.txt", "rw");
        // ��ȡ�ļ�ͨ��
        FileChannel fc = rf.getChannel();
        
        // ��ȡ�ļ��� -- �ӵ�3��λ�ÿ�ʼ����6����Ϊ��������
        FileLock lock = fc.lock(3, 6, true);
        
        // ����Ƿ���Ч
        System.out.println(lock.isValid());
        // ����Ƿ�Ϊ������
        System.out.println(lock.isShared());
        
        // �ͷ��ļ���
        lock.release();
        // �ر��ļ�����
        rf.close();
    }
}
