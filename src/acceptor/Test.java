package acceptor;

import java.io.IOException;

/**
 * Acceptor�Ƿ�Ӧ��ģʽ:
 * �����¼��ķ����������¼������󣬷ַ������Ӧ�Ĵ���������(�˴�ʹ�ö��߳�ʵ���첽�������handler������ͬʱִ��)
 *
 *
 * �������ֻ��һ��selector�������������ӵ�����,��,д���¼�.
 * ��bossworker�ļ����е���������selector�����¼�,bossֻ���������¼�,
 * ���������¼�������,����õ����ӵĿͻ���ͨ��,����Щ����ͨ��ת��worker,
 * worker����Щ����ע�ᵽ�Լ���selector��,���ϼ���ͨ���Ķ�д�¼�.
 *
 * Ҳ����˵,boss��selector��ֻע����һ��channel,�Ǿ��Ƿ�������
 * serversocketchannel,����Ȥ���¼��������¼�.
 * ��worker��selector��ע�������пͻ��˵�����,����Ȥ���¼��Ƕ���д�¼�
 *
 * @author wuyuan
 * @date 2019/8/18
 */
public class Test {
    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8088)).start();
    }
}
