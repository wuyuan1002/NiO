package acceptor.twoworkers;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * worker����������ӵĶ�д�¼����ַ�����
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class WorkerReactor implements Runnable {
    private final Selector selector;
    
    //�̳߳������첽ִ�и������ӵĶ�д�¼�
    private final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 3, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<>(3));
    
    WorkerReactor() throws IOException {
        this.selector = Selector.open();
    }
    
    @Override
    public void run() {
        try {
            while (!Thread.interrupted() && (selector.select() != 0)) {
                Set<SelectionKey> keys = selector.selectedKeys();
                //���¼��ַ� --
                //(��Netty�в���ȥ�ַ����񣬶�������channel�����е��¼�����handler���ɵ�ǰ�̰߳���ִ�У�
                //�������Խ���̰߳�ȫ����,���,netty��handler�в�Ҫ��ִ��ʱ������Ĳ���,�����,��Ӧ���Լ������̳߳�
                //����,��Ȼһ����ʱ�����������������е�handler.)
                dispatch(keys);
                //���selectedKeySet����ֹ�´��ظ�����
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void dispatch(Set<SelectionKey> keys) {
        for (SelectionKey key : keys) {
            //��ȡ�¼�����󶨵Ķ��󣬲�ִ�иö���ķ������������¼�
            Runnable handler = (Runnable) (key.attachment());
            if (handler != null) {
                //����һ���߳��д�����ʵ���첽����
                threadPool.execute(handler);
            }
        }
    }
    
    public Selector getSelector() {
        return selector;
    }
}