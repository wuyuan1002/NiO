package acceptor.bossworker;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * ÿһ���ͻ������ӵĴ����������������Ӧ�Ŀͻ������ӵ��¼�
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class Handler implements Runnable {
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    
    //ֻ�������µ�����ʱ��newһ��Handler��ִ�иù��췽��������handler�󶨵�key�ϡ�֮����������¼�ʱ��ֱ��ִ�����󶨵�handler��run����
    Handler(Selector selector, SocketChannel accept) {
        try {
            this.socketChannel = accept;
            this.socketChannel.configureBlocking(false);
            this.selectionKey = this.socketChannel.register(selector, 0);
            //���µ�������Ӹ�Handler����������������ӵ��¼�
            this.selectionKey.attach(this);
            //���µ����Ӱ󶨵�selectorѡ������
            this.socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        //����Ƕ��¼�
        if (this.selectionKey.isReadable()) {
            //ִ����Ӧ����
            
            
        }
        
        //�����д�¼�
        if (this.selectionKey.isWritable()) {
            //ִ����Ӧ����
            
            
        }
        
        
        
    }
}
