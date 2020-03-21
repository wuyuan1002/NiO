package buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scaterring��
 *      ����,��ɢ,��������һ��Channel�����ݷ�ɢ�������Buffer����,
 *      һ�����˾Ͱ���˳������һ��,����ʵ�����ݵķ��ű��ࡣ
 *      ����һ����Ϣ����������,��һ������ͷ��Ϣ,�ڶ�������Э����Ϣ,
 *      ������������Ϣ�塣���԰���������Ϣ�ֱ�ŵ���ͬ�ģ�uffer���У������ð�������Ϣ������
 *      һ�� Buffer�У����ڳ����н��н�����������ʡȥ�˽�����ʱ�䡣
 *
 * Gatering��
 *      �ڽ�����д���� Channel��ʱ�����Բ��� buffer���飬����д����
 *      һ�� buffer����Ͱ���˳������һ����
 *
 *
 * ʹ�÷�ʽ���� cmd telnet localhost 8899�����Ӻ���������ַ�����
 *
 * @author wuyuan
 * @date 2020/3/21
 */
public class ScatteringAndGatherIng {
    public static void main(String[] args) throws IOException {
        // ������������
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));
        
        // ����3��buffer
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);
        // �ܹ��ܶ�ȡ�����ݳ���
        int messageLength = 2 + 3 + 4;
        
        // ��ȡ�ͻ������Ӻ��ȡ����
        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true) {
            // �Ѷ�ȡ�����ݳ���
            int byteRead = 0;
            // ���ܿͻ���д��ĵ��ַ��� -- 3��buffer��������ѭ������
            while (byteRead < messageLength) {
                // ��ȡbuffer���ݵ�channel -- �������buffer���� =====================================================
                long r = socketChannel.read(byteBuffers);
                byteRead += r;
                System.out.println("byteRead:" + byteRead);
                // ͨ������ӡ
                Arrays.stream(byteBuffers).
                        map(buffer -> "postiton:" + buffer.position() + ",limit:" + buffer.limit()).
                        forEach(System.out::println);
            }
            // ������buffer��flip��
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            // �����ݶ������Ե��ͻ���
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                // д��buffer�е����ݵ�channel -- �������buffer���� =====================================================
                long r = socketChannel.write(byteBuffers);
                byteWrite += r;
            }
            // ������buffer��clear
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead:" + byteRead + ",byteWrite:" + byteWrite + ",messageLength:" + messageLength);
        }
    }
}
