package selector;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/2
 */
public class NioSelectorServer {
    
    /**
     * 定义存储所有客户端连接的 map 集合，从而可以让服务器知道有哪些客户端连接着服务器，
     * 实现有一个客户端发送消息后服务器可以找到所有客户端并广播转发给他们
     *
     * 该map可以没有，这是为了业务需求才设置的，不然服务器不知道有哪些客户端连接着它，就不能实现把一个消息广播转发给其他客户端了
     */
    private static final Map<String, SocketChannel> SERVER_SOCKET_MAP = new HashMap<>();
    
    
    public static void main(String[] args) throws Exception {
        
        /**
         * 首先建立一个服务器端连接通道，获取serverSocket并绑定监听端口号，
         * 然后注册在选择器上并添加监听事件类型为连接事件。此时选择器上只注册了一个channel，监听连接事件。
         *
         * 之后只要有客户端访问端口号进行连接时，可以获取到连接的channel对象，然后把该channel对象也
         * 注册到选择器上并添加添加事件的类型。该channel连接通信的端口号并不是8899了，而是服务器随机的
         * 端口号用来和该客户端进行通信，每一个channel都有自己对应的端口号。
         */
        
        
        //获得服务器端的连接通道(一个channel对象)
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //将通道设置成非阻塞的(只有非阻塞的通道才可以注册到选择器Selector上)
        serverSocketChannel.configureBlocking(false);
        //获取服务器端的serverSocket对象
        ServerSocket serverSocket = serverSocketChannel.socket();
        //socket对象绑定监听端口号，用来让客户端连接
        serverSocket.bind(new InetSocketAddress(8899));
        
        //创建selector选择器对象
        Selector selector = Selector.open();
        /*
         * 将服务器连接通道serverSocketChannel对象注册到selector选择器上，并添加监听事件的类型selectonKey(这儿监测的是连接事件)
         *
         * 若要一个channel监听多个事件，则用"位或"把多个事件相连,如(serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT | SelectionKey.OP_READ);)
         *
         * 一个(SelectionKey)事件可以有多个channel关注监听，一个channel也可以同时关注监听多个(SelectionKey)事件
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        
        //开始事件监听处理
        while (true) {
            try {
                /*
                 * 该方法是阻塞方法，不断监听事件。当选择器上注册的channel有其对应的监听事件发生时，该方法返回，
                 * 返回的是有事件发生的(被捕获的，已经就绪的)channel的数量
                 * (select()方法返回的int值表示有多少通道已经就绪)
                 *
                 * 如果不添加参数则返回所有类型事件的已就绪的channel的数量，
                 * 如果添加参数则返回参数类型的事件的已就绪的channel的数量，如selector.select(SelectionKey.OP_ACCEPT);返回注册了连接事件并已就绪的channel的数量
                 */
                selector.select();
                
                //获取相关事件被捕获的selectionKey的集合(监听的事件发生了的selectionKey的集合)
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                
                //循环遍历selectionKeys集合的每一个元素，判断每个SelectionKey是什么类型的事件，根据事件类型的不同进行不同的处理
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    
                    //得到每一个selectionKey
                    SelectionKey selectionKey = iterator.next();
                    /*
                     * 每一次处理完必须把该selectionKey从selected-key集合中移除，
                     * 不然下次处理时会出现重复处理。(本来该selectionKey没有就绪，但不删除的话会有上次遗留的该selectionKey)
                     *
                     * 使用迭代器的remove方法或者集合的remove方法一处selectionKey
                     */
                    iterator.remove();
                    // selectionKeys.remove(selectionKey);
    
                    
                    
                    //定义客户端的连接通道
                    final SocketChannel client;
    
                    try {
                        /**如果是连接事件，则获取连接的通道，把它注册到选择器上*/
                        if (selectionKey.isAcceptable()) {
                            //获取到发生该事件的channel对象
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            //服务器端获取客户端的连接 ( server.accept();是阻塞方法，直到有客户端连接时返回客户端的连接，但在这里不会阻塞，因为已经有连接事件发生了 )
                            client = server.accept();
                            //将通道设置成非阻塞的
                            client.configureBlocking(false);
            
                            //定义存储通道到map集合的key(这里生成一个UUID作为key)，服务器通过map集合找到所有连接着的客户端
                            String key = "【" + UUID.randomUUID().toString() + "】";
                            //将连接过来的客户端通道存储到map中
                            SERVER_SOCKET_MAP.put(key, client);
            
            
                            System.out.println(client + "已连接！");
            
            
                            /**如果是读事件发生*/
                        } else if (selectionKey.isReadable()) {
                            //获取到发生该事件的channel对象
                            client = (SocketChannel) selectionKey.channel();
                            //定义读取数据的缓冲区
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            //读取数据到缓冲区,把读到的数据都放到转成一个string，最后统一转发给其他客户端
                            StringBuilder stringBuilder = new StringBuilder();
                            while (client.read(buffer) != -1) {
                                buffer.flip();
                
                                //定义字符集编码与解码
                                Charset charset = Charset.forName("utf-8");
                                stringBuilder.append(charset.decode(buffer).array());
                
                                buffer.clear();
                            }
            
                            //将得到的数据转换成字符串
                            String receiveMessage = stringBuilder.toString();
            
            
                            /** 将得到的数据广播转发给所有客户端 */
                            //获取发送数据的连接通道的key
                            String sendKey = null;
                            for (Map.Entry<String, SocketChannel> entry : SERVER_SOCKET_MAP.entrySet()) {
                                if (client == entry.getValue()) {
                                    sendKey = entry.getKey();
                                    break;
                                }
                            }
            
                            //发送给每一个客户端
                            for (Map.Entry<String, SocketChannel> entry : SERVER_SOCKET_MAP.entrySet()) {
                                //定义发送数据的缓冲区
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                //如果是自己的话
                                if (entry.getKey().equals(sendKey)) {
                                    writeBuffer.put(("【自己】" + sendKey + " : " + receiveMessage).getBytes());
                    
                                    //如果不是自己的话
                                } else {
                                    writeBuffer.put((sendKey + " : " + receiveMessage).getBytes());
                                }
                
                                writeBuffer.flip();
                                //发送给客户端
                                entry.getValue().write(writeBuffer);
                            }
            
                        }
        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
    }
}
