package selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * ʵ��һ��selector��������˿�
 *
 * ��ͨ��socket������һ������һ���˿ں�һ���߳�,
 * ������һ���߳�һ��selector,һ��selector������
 * ����˿�,�൱�ڴ������������
 *
 *
 *
 * SelectionKey�����ּ����¼�����:
 *
 *  SelectionKey.OP_ACCEPT ���� ���������¼�����ʾ�������������˿ͻ����ӣ����������Խ������������
 *  SelectionKey.OP_CONNECT ���� ���Ӿ����¼�����ʾ�ͻ���������������Ѿ������ɹ�
 *  SelectionKey.OP_READ ���� �������¼�����ʾͨ�����Ѿ����˿ɶ������ݣ�����ִ�ж������ˣ�ͨ��Ŀǰ�����ݣ����Խ��ж������ˣ�
 *  SelectionKey.OP_WRITE ���� д�����¼�����ʾ�Ѿ�������ͨ��д�����ˣ�ͨ��Ŀǰ��������д������
 *
 * @author wuyuan
 * @date 2019/8/8
 * @version 1.0
 */
public class SelectorTest {
    public static void main(String[] args) throws Exception {
        
        //1.��������˿ں�
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;
        
        //2.����selectorѡ��������
        Selector selector = Selector.open();
        
        //3.��ÿһ���˿���ע������¼�,ʹÿһ���˿ڶ����Խ������� -- һ��channel����һ���˿�
        for (int i = 0; i < ports.length; ++i) {
            //��÷������˵�����ͨ��(һ��channel����)
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //��ͨ�����óɷ�������(ֻ�з�������ͨ���ſ���ע�ᵽѡ����Selector��)
            serverSocketChannel.configureBlocking(false);
            //socket����󶨼����˿ںţ������ÿͻ�������
            serverSocketChannel.bind(new InetSocketAddress(ports[i]));
            //��ÿһ���˿ںŶ�Ӧ��channel��ע�ᵽselectorѡ������,ע��ļ����¼�����Ϊ����,Ҳ����ʹ��ͨ�����Խ��ܱ������
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        
        //�ȴ������ͻ��˵��¼�
        while (true) {
            //�������������¼������󷵻�
            selector.select();
            //��ȡ�����о������¼�
            Set<SelectionKey> keys = selector.selectedKeys();
            
            //��������ÿһ���¼�
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                //�õ�ÿһ�������¼���selectionKey
                SelectionKey selectionKey = iterator.next();
                //��selectionKey��selected-key�������Ƴ�
                iterator.remove();
                
                if (selectionKey.isAcceptable()) {
                    //����������¼�
                    
                    
                    
                    //ͨ���¼���ȡ�����˸��¼���serverSocketChannel
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    
                    //ͨ��serverSocketChannel��ȡ������������channel
                    SocketChannel channel = serverSocketChannel.accept();
                    //���������óɷ�������
                    channel.configureBlocking(false);
                    //������ͨ��ע�ᵽselector�ϣ����������¼�
                    channel.register(selector, SelectionKey.OP_READ);
                    
                    System.out.println("�����¼���" + channel);
                    
                } else if (selectionKey.isReadable()) {
                    //����Ƕ��¼�
                    
                    
                    
                    //���������¼���channel
                    
                }
                
                
            }
            
            
        }
        
        
    }
}
