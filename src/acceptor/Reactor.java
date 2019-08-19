package acceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ѡ�����������¼��ķ����������¼�����ʱ���ж�����ķַ�
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class Reactor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    
    //�����¼����̳߳�
    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<>(3));
    
    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        //�����¼��ϰ�һ����������ڸ��¼�������������¼�.ֻ�����ӵ�serverSocketChannel�󶨵���acceptor����
        key.attach(new Acceptor());
    }
    
    @Override
    public void run() {
        int num = 0;
        try {
            //�����¼�
            while (!Thread.interrupted() && (num = selector.select()) != 0) {
                System.out.println("������" + num + "���¼������ˣ�");
                
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    //��ÿһ���¼��ַ�
                    dispatch(key);
                }
                //���selectedKeySet����ֹ�´��ظ�����
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void dispatch(SelectionKey key) {
        //��ȡ�¼�����󶨵Ķ��󣬲�ִ�иö���ķ�����������¼�
        Runnable r = (Runnable) (key.attachment());
        if (r != null) {
            //����һ���߳��д���ʵ���첽����
            threadPool.execute(r);
        }
    }
    
    
    //������
    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                //��ȡ�ͻ��˵�����
                SocketChannel accept = serverSocketChannel.accept();
                if (accept != null) {
                    //�½�һ������������handler�Ĺ��췽���а������Ŀͻ������Ӱ󶨵�selectorѡ������
                    //֮��ͻ������ӷ����¼��������ﴴ���Ĵ����������¼�
                    new Handler(selector, accept);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
