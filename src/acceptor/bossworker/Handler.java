package acceptor.bossworker;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 每一个客户端连接的处理器，用来处理对应的客户端连接的事件
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class Handler implements Runnable {
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    
    //只有在有新的连接时才new一个Handler，执行该构造方法，并把handler绑定到key上。之后该连接有事件时会直接执行它绑定的handler的run方法
    Handler(Selector selector, SocketChannel accept) {
        try {
            this.socketChannel = accept;
            this.socketChannel.configureBlocking(false);
            this.selectionKey = this.socketChannel.register(selector, 0);
            //给新的连接添加该Handler对象，用来处理该连接的事件
            this.selectionKey.attach(this);
            //把新的连接绑定到selector选择器上
            this.socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        //如果是读事件
        if (this.selectionKey.isReadable()) {
            //执行相应操作
            
            
        }
        
        //如果是写事件
        if (this.selectionKey.isWritable()) {
            //执行相应操作
            
            
        }
        
        
        
    }
}
