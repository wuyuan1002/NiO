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
 * 选择器，监听事件的发生，当有事件发生时进行对任务的分发
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class Reactor implements Runnable {
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    
    //处理事件的线程池
    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<>(3));
    
    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        //给该事件上绑定一个处理对象，在该事件被触发后处理该事件.只有连接的serverSocketChannel绑定的是acceptor对象
        key.attach(new Acceptor());
    }
    
    @Override
    public void run() {
        int num = 0;
        try {
            //监听事件
            while (!Thread.interrupted() && (num = selector.select()) != 0) {
                System.out.println("本次有" + num + "个事件发生了！");
                
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    //将每一个事件分发
                    dispatch(key);
                }
                //清空selectedKeySet，防止下次重复处理
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void dispatch(SelectionKey key) {
        //获取事件上面绑定的对象，并执行该对象的方法来处理该事件
        Runnable r = (Runnable) (key.attachment());
        if (r != null) {
            //在另一个线程中处理，实现异步操作
            threadPool.execute(r);
        }
    }
    
    
    //接收器
    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                //获取客户端的连接
                SocketChannel accept = serverSocketChannel.accept();
                if (accept != null) {
                    //新建一个处理器，在handler的构造方法中把新来的客户端连接绑定到selector选择器上
                    //之后客户端连接发生事件后由这里创建的处理器处理事件
                    new Handler(selector, accept);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
