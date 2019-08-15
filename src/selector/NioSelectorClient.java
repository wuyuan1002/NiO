package selector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyuan
 * @date 2019/6/8
 * @version 1.0
 */
public class NioSelectorClient {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress("localhost", 8899));
            
            
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                
                selectionKeys.forEach(selectionKey -> {
                    if (selectionKey.isConnectable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        if (client.isConnectionPending()) {
                            try {
                                client.finishConnect();
                                client.register(selector, SelectionKey.OP_READ);
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((LocalDateTime.now() + "连接成功！").getBytes());
                                writeBuffer.flip();
                                client.write(writeBuffer);
    
                                ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 3, TimeUnit.MINUTES,new LinkedBlockingQueue<>(3));
                                threadPool.execute(() -> {
                                    while (true) {
                                        try {
                                            writeBuffer.clear();
                                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                            String sendMessage = br.readLine();
                                            writeBuffer.put(sendMessage.getBytes());
                                            writeBuffer.flip();
                                            client.write(writeBuffer);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            
                        }
                    } else if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        try {
                            int count = client.read(readBuffer);
                            if (count > 0) {
                                String receiveMessage = new String(readBuffer.array(), 0, count);
                                System.out.println(receiveMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    selectionKeys.remove(selectionKey);
                    
                });
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
