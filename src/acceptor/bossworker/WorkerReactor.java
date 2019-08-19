package acceptor.bossworker;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * worker负责监听连接的读写事件并分发处理
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class WorkerReactor implements Runnable {
    private final Selector selector;
    
    //线程池用来异步执行各个连接的读写事件
    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<>(3));
    
    WorkerReactor() throws IOException {
        this.selector = Selector.open();
    }
    
    @Override
    public void run() {
        try {
            while (!Thread.interrupted() && (selector.select() != 0)) {
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
        //获取事件上面绑定的handler处理器对象，并执行该对象的方法来处理该事件
        Runnable handler = (Runnable) (key.attachment());
        if (handler != null) {
            //在另一个线程中处理，实现异步操作
            threadPool.execute(handler);
        }
    }
    
    public Selector getSelector() {
        return selector;
    }
}
