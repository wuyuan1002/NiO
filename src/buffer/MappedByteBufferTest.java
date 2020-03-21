package buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * �ڴ�ӳ���ļ� MappedByteBuffer��
 *      ��ͨ��io�������ڴ��л����ļ��Ŀ�������(JVM�ڴ�<�û�̬>�Ͳ���ϵͳ�ڴ�<�ں�̬>֮��Ŀ�����OS�����û�̬���ں�̬��ת��)��
 *      �ڴ�ӳ���ļ�ʱ�ǽ�ĳһ�εĶ����ڴ��ַ���ļ������ĳһ���ֽ�����ӳ���ϵ����ʱ��û�п������ݵ��ڴ���ȥ��
 *      ��������ʹ��ӳ���ļ��е�ĳһ��ʱֱ�Ӷ�ȡ�ļ���JVM�ڴ��У���ȥ���ļ��Ŀ�����
 *      �ڴ�ӳ���ļ���Ч�ʱȱ�׼IO�ߵ���Ҫԭ�������Ϊ���˰����ݿ�����OS�ں˻�������һ��������ֱ�ӱ����������̵ĵ�ַ�ռ��С�
 *
 * MappedByteBuffer���ã�
 *      �������ļ�ֱ���� JVM�ڴ��н����޸ģ�������ϵͳ����Ҫ����һ�Σ�
 *      ֱ�ӻ����� DirectByteBuffer�� MappedByteBuffer�����ࡣ
 *
 * @author wuyuan
 * @date 2020/3/21
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccess = new RandomAccessFile("file1.txt", "rw");
        FileChannel fileChannel = randomAccess.getChannel();
        
        // ��ȡ�ļ�ӳ��MappedByteBuffer -- 0-5��Χӳ�䵽JVM�ڴ��У����ݿ���ֱ�����ڴ����
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        
        // �޸��ļ�ָ��λ�õ�ֵ
        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(1, (byte) 'b');
        
        // �ر��ļ�
        randomAccess.close();
    }
}
