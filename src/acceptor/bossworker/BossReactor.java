package acceptor.bossworker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 选择器，监听事件的发生，当有事件发生时进行对任务的分发
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class BossReactor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private final SelectionKey key;
    
    private final WorkerReactor workerReactor;
    
    public BossReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        //boss只关注连接事件
        key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new Acceptor());
        
        //创建worker并启动它 -- 一个boss可以有多个worker
        workerReactor = new WorkerReactor();
        new Thread(workerReactor).start();
    }
    
    @Override
    public void run() {
        int num = 0;
        try {
            //监听事件
            while (!Thread.interrupted() && (num = selector.select()) != 0) {
                System.out.println("本次有" + num + "个连接！");
                
                Set<SelectionKey> keys = selector.selectedKeys();
                //将连接转给worker,绑定到worker的selector上,由worker处理通道的读写事件
                dispatch(keys);
                //清空selectedKeySet，防止下次重复处理
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //分发
    private void dispatch(Set<SelectionKey> keys) {
        Acceptor acceptor = (Acceptor) key.attachment();
        acceptor.keys = keys;
        //由另一个线程完成分发任务,实现异步操作,主线程可以直接继续去监听连接事件,而不用自己执行分发任务,执行完后再去监听
        new Thread(acceptor).start();
    }
    
    //接收器
    class Acceptor implements Runnable {
        private Set<SelectionKey> keys = null;
        
        @Override
        public void run() {
            if (this.keys != null) {
                for (SelectionKey key : keys) {
                    //获取到客户端连接通道并为每一个连接绑定一个handler处理器,
                    //在handler的构造方法中会把该通道绑定到worker的selector上
                    SocketChannel channel = (SocketChannel) key.channel();
                    new Handler(BossReactor.this.workerReactor.getSelector(), channel);
                }
            }
            this.keys.clear();
        }
    }
    
}
