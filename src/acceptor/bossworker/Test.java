package acceptor.bossworker;

import java.io.IOException;

/**
 * Acceptor�Ƿ�Ӧ��ģʽ,Ҳ�� Dispatcher������ģʽ�� Notifier֪ͨ��ģʽ:
 * �����¼��ķ����������¼������󣬷ַ������Ӧ�Ĵ���������(�˴�ʹ�ö��߳�ʵ���첽�������handler������ͬʱִ��)
 *
 * bossֻ���������¼�,���������¼�������,����õ����ӵĿͻ���ͨ��,����Щ����ͨ��ת��worker,
 * worker����Щ����ע�ᵽ�Լ���selector��,���ϼ���ͨ���Ķ�д�¼�.
 *
 * Ҳ����˵,boss��selector��ֻע����һ��channel,�Ǿ��Ƿ�������
 * serversocketchannel,����Ȥ���¼��������¼�.
 * ��worker��seler��ע�������пͻ��˵�����,����Ȥ���¼��Ƕ���д�¼�
 *
 * һ��boss���Զ�Ӧ���worker
 *
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class Test {
    public static void main(String[] args) throws IOException {
        new Thread(new BossReactor(8088)).start();
    }
}
