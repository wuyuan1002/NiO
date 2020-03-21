package buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scaterring：
 *      撒开,分散,将来自于一个Channel的数据分散读到多个Buffer当中,
 *      一个满了就按照顺序用下一个,可以实现数据的分门别类。
 *      比如一个消息有三个部分,第一部分是头信息,第二部分是协议信息,
 *      第三部分是消息体。可以把这三个消息分别放到不同的Ｂuffer当中，而不用把所有信息都读到
 *      一个 Buffer中，再在程序中进行解析，这样就省去了解析的时间。
 *
 * Gatering：
 *      在将数据写出到 Channel中时，可以采用 buffer数组，依次写出，
 *      一个 buffer用完就按照顺序用下一个。
 *
 *
 * 使用方式：打开 cmd telnet localhost 8899，连接后可以输入字符串了
 *
 * @author wuyuan
 * @date 2020/3/21
 */
public class ScatteringAndGatherIng {
    public static void main(String[] args) throws IOException {
        // 开启服务器端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));
        
        // 构造3个buffer
        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);
        // 总共能读取的数据长度
        int messageLength = 2 + 3 + 4;
        
        // 获取客户端连接后读取数据
        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true) {
            // 已读取的数据长度
            int byteRead = 0;
            // 接受客户端写入的的字符串 -- 3个buffer都读满后循环跳出
            while (byteRead < messageLength) {
                // 读取buffer数据到channel -- 传入的是buffer数组 =====================================================
                long r = socketChannel.read(byteBuffers);
                byteRead += r;
                System.out.println("byteRead:" + byteRead);
                // 通过流打印
                Arrays.stream(byteBuffers).
                        map(buffer -> "postiton:" + buffer.position() + ",limit:" + buffer.limit()).
                        forEach(System.out::println);
            }
            // 将所有buffer都flip。
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            // 将数据读出回显到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                // 写出buffer中的数据到channel -- 传入的是buffer数组 =====================================================
                long r = socketChannel.write(byteBuffers);
                byteWrite += r;
            }
            // 将所有buffer都clear
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead:" + byteRead + ",byteWrite:" + byteWrite + ",messageLength:" + messageLength);
        }
    }
}
