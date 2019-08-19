package acceptor.bossworker;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyuan
 * @date 2019/8/18
 */
public class WorkerReactor implements Runnable {
    private final Selector selector;
    
    //�̳߳������첽ִ�и������ӵĶ�д�¼�
    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<>(3));
    
    public WorkerReactor() throws IOException {
        this.selector = Selector.open();
    }
    
    @Override
    public void run() {
        try {
            while (!Thread.interrupted() && (selector.select() != 0)) {
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator it = keys.iterator();
                while (it.hasNext()) {
                    //��ÿһ���¼��ַ�
                    dispatch((SelectionKey) it.next());
                }
                //���selectedKeySet����ֹ�´��ظ�����
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void dispatch(SelectionKey key) {
        //��ȡ�¼�����󶨵�handler���������󣬲�ִ�иö���ķ�����������¼�
        Runnable handler = (Runnable) (key.attachment());
        if (handler != null) {
            //����һ���߳��д���ʵ���첽����
            threadPool.execute(handler);
        }
    }
    
    public Selector getSelector() {
        return selector;
    }
}
