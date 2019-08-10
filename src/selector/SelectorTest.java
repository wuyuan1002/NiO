package selector;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 实现一个selector监听五个端口
 *
 * 普通的socket网络编程一个请求一个端口号一个线程,
 * 而这里一个线程一个selector,一个selector处理着
 * 五个端口,相当于处理着五个请求
 *
 *
 *
 * SelectionKey的四种监听事件类型:
 *
 *  SelectionKey.OP_ACCEPT —— 接收连接事件，表示服务器监听到了客户连接，服务器可以接收这个连接了
 *  SelectionKey.OP_CONNECT —— 连接就绪事件，表示客户与服务器的连接已经建立成功
 *  SelectionKey.OP_READ —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
 *  SelectionKey.OP_WRITE —— 写就绪事件，表示已经可以向通道写数据了（通道目前可以用于写操作）
 *
 * @author wuyuan
 * @date 2019/8/8
 * @version 1.0
 */
public class SelectorTest {
    public static void main(String[] args) throws Exception {
        
        //1.构建五个端口号
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;
        
        //2.创建selector选择器对象
        Selector selector = Selector.open();
        
        //3.在每一个端口上注册监听事件,使每一个端口都可以接受连接 -- 一个channel管理一个端口
        for (int i = 0; i < ports.length; ++i) {
            //获得服务器端的连接通道(一个channel对象)
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //将通道设置成非阻塞的(只有非阻塞的通道才可以注册到选择器Selector上)
            serverSocketChannel.configureBlocking(false);
            //socket对象绑定监听端口号，用来让客户端连接
            serverSocketChannel.bind(new InetSocketAddress(ports[i]));
            //把每一个端口号对应的channel上注册到selector选择器上,注册的监听事件类型为接受,也就是使该通道可以接受别的连接
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        
        //等待监听客户端的事件
        while (true) {
            //阻塞方法，有事件发生后返回
            selector.select();
            //获取到所有就绪的事件
            Set<SelectionKey> keys = selector.selectedKeys();
            
            //迭代处理每一个事件
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                //得到每一个发生事件的selectionKey
                SelectionKey selectionKey = iterator.next();
                //将selectionKey从selected-key集合中移除
                iterator.remove();
                
                if (selectionKey.isAcceptable()) {
                    //如果是连接事件
                    
                    
                    
                    //通过事件获取发生了该事件的serverSocketChannel
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    
                    //通过serverSocketChannel获取到真正的连接channel
                    SocketChannel channel = serverSocketChannel.accept();
                    //将连接设置成非阻塞的
                    channel.configureBlocking(false);
                    //将连接通道注册到selector上，并监听读事件
                    channel.register(selector, SelectionKey.OP_READ);
                    
                    System.out.println("连接事件：" + channel);
                    
                } else if (selectionKey.isReadable()) {
                    //如果是读事件
                    
                    
                    
                    //处理发生读事件的channel
                    
                }
                
                
            }
            
            
        }
        
        
    }
}
