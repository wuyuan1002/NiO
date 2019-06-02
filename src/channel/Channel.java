package channel;
/**
 * 1.通道(Channel):源节点与目标节点之间的链接，相当于铁路，
 *                 在java NIO中负责缓冲区数据的传输，Channel本身不存储数据，因此，需要配合缓冲区进行传输。
 *   ##  通道、缓冲区、数据  之间的关系就相当于 铁路、火车、货物  之间的关系  ##
 * 
 * 2.通道的主要实现类
 *    都是java.nio.channels包下的Channel接口的实现类
 *      |--FileChannel --> 用于本地文件的传输
 *      |--SocketChannel --> 用于网络数据的传输(TCP)
 *      |--ServerSocketChannel --> 用于网络数据的传输(TCP)
 *      |--DatagramChannel --> 用于网络数据的传输(UDP)
 *      
 * 3.如何获取通道(有三种)
 *    A.java对以下支持通道的类提供了getChannel()方法
 *       本地IO:
 *          FileInputStream
 *          FileOutputStream
 *          RandomAccessStream
 *       网络IO:
 *          Socket
 *          ServerSocket
 *          DatagreamSocket
 *    B.JDK1.7中对各个通道提供了一个静态方法open()
 *    C.JDK1.7中的Files工具类中的newByteChannel()方法
 *    
 * 4.通道之间的数据传输
 *    transferFrom
 *    transferTo
 *
 *
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/5/31
 *
 */
public class Channel {

	public static void main(String[] args) {
		
	}

}
