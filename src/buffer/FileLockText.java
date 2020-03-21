package buffer;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * �ļ���
 *
 * @author wuyuan
 * @date 2020/3/21
 */
public class FileLockText {
    public static void main(String[] args) throws Exception {
        // ��������ļ����ʶ��� -- RandomAccessFile������������޸��ļ�������ָ�������ļ��е��ĸ�λ�á�������io��ϵ���ֱ࣬�Ӽ̳�Object�ࡣ
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
