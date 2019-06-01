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

        FileInputStream in = new FileInputStream("folder\\NioTest1");
        FileOutputStream out = new FileOutputStream("folder\\NioTest2");


        FileChannel inchannel = in.getChannel();
        FileChannel outchannel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (inchannel.read(buffer) != -1){
            buffer.flip();
            outchannel.write(buffer);
            buffer.clear();
        }

        inchannel.close();
        outchannel.close();

    }
}
