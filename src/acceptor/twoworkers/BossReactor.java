package acceptor.twoworkers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * boss��������ͻ��˵������¼�����������ʱ�����ӷַ���ע�ᵽworker��selector�ϣ���worker�������ӵĶ�д�¼�
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class BossReactor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private final SelectionKey key;
    
    private final WorkerReactor workerReactor;
    
    BossReactor(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        //bossֻ��ע�����¼�
        key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new Acceptor());
        
        //����������worker -- һ��boss�����ж��worker
        workerReactor = new WorkerReactor();
        new Thread(workerReactor).start();
    }
    
    @Override
    public void run() {
        int num = 0;
        try {
            //�����¼�
            while (!Thread.interrupted() && (num = selector.select()) != 0) {
                System.out.println("������" + num + "�����ӣ�");
                
                Set<SelectionKey> keys = selector.selectedKeys();
                //������ת��worker,�󶨵�worker��selector��,��worker����ͨ���Ķ�д�¼�
                dispatch(keys);
                //���selectedKeySet����ֹ�´��ظ�����
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //�ַ�
    private void dispatch(Set<SelectionKey> keys) {
        Acceptor acceptor = (Acceptor) key.attachment();
        acceptor.keys = keys;
        //����һ���߳���ɷַ�����,ʵ���첽����,���߳̿���ֱ�Ӽ���ȥ���������¼�,�������Լ�ִ�зַ�����,ִ�������ȥ����
        new Thread(acceptor).start();
    }
    
    //������
    class Acceptor implements Runnable {
        private Set<SelectionKey> keys = null;
        
        @Override
        public void run() {
            if (this.keys != null) {
                for (SelectionKey key : keys) {
                    //��ȡ���ͻ�������ͨ����Ϊÿһ�����Ӱ�һ��handler������,
                    //��handler�Ĺ��췽���л�Ѹ�ͨ���󶨵�worker��selector��
                    SocketChannel channel = (SocketChannel) key.channel();
                    new Handler(BossReactor.this.workerReactor.getSelector(), channel);
                }
                this.keys.clear();
            }
        }
    }
    
}
