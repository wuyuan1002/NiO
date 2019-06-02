package channel;
/**
 * 1.ͨ��(Channel):Դ�ڵ���Ŀ��ڵ�֮������ӣ��൱����·��
 *                 ��java NIO�и��𻺳������ݵĴ��䣬Channel�����洢���ݣ���ˣ���Ҫ��ϻ��������д��䡣
 *   ##  ͨ����������������  ֮��Ĺ�ϵ���൱�� ��·���𳵡�����  ֮��Ĺ�ϵ  ##
 * 
 * 2.ͨ������Ҫʵ����
 *    ����java.nio.channels���µ�Channel�ӿڵ�ʵ����
 *      |--FileChannel --> ���ڱ����ļ��Ĵ���
 *      |--SocketChannel --> �����������ݵĴ���(TCP)
 *      |--ServerSocketChannel --> �����������ݵĴ���(TCP)
 *      |--DatagramChannel --> �����������ݵĴ���(UDP)
 *      
 * 3.��λ�ȡͨ��(������)
 *    A.java������֧��ͨ�������ṩ��getChannel()����
 *       ����IO:
 *          FileInputStream
 *          FileOutputStream
 *          RandomAccessStream
 *       ����IO:
 *          Socket
 *          ServerSocket
 *          DatagreamSocket
 *    B.JDK1.7�жԸ���ͨ���ṩ��һ����̬����open()
 *    C.JDK1.7�е�Files�������е�newByteChannel()����
 *    
 * 4.ͨ��֮������ݴ���
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
