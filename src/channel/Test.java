package channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ��NioTest1.txt�ж�ȡ����д��NioTest2.txt��
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/1
 */
public class Test {
    public static void main(String[] args) throws Exception {

        //�õ����ļ������� -- ���������
        FileInputStream in = new FileInputStream("folder\\NioTest1");
        FileOutputStream out = new FileOutputStream("folder\\NioTest2");

        //�õ��ļ����ӵ�ͨ�������ڴ��仺����
        FileChannel inchannel = in.getChannel();
        FileChannel outchannel = out.getChannel();

        //�½�һ��������
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //��ͨ��1�ж�ȡ���ݴ��뻺����Ȼ���ٴӻ������а�����д��ͨ��2
        while (inchannel.read(buffer) != -1) {
            buffer.flip();
            outchannel.write(buffer);
            buffer.clear();
        }

        //�ر�����ͨ�� -- �ر���ʱ,��� ͨ�� != null, ���ر�����Ӧ��ͨ��
        in.close();
        out.close();

    }
}
