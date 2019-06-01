package channelandbuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 从NioTest1.txt中读取数据写到NioTest2.txt中
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/1
 */
public class Test1 {
    public static void main(String[] args) throws Exception {

        //得到与文件的连接 -- 输入输出流
        FileInputStream in = new FileInputStream("folder\\NioTest1");
        FileOutputStream out = new FileOutputStream("folder\\NioTest2");

        //得到文件连接的通道，用于传输缓冲区
        FileChannel inchannel = in.getChannel();
        FileChannel outchannel = out.getChannel();

        //新建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //从通道1中读取数据存入缓冲区然后再从缓冲区中把数据写入通道2，
        while (inchannel.read(buffer) != -1) {
            buffer.flip();
            outchannel.write(buffer);
            buffer.clear();
        }

        //关闭通道和流
        inchannel.close();
        outchannel.close();
        in.close();
        out.close();

    }
}
