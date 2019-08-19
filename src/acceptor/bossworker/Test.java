package acceptor.bossworker;

import java.io.IOException;

/**
 * Acceptor是反应器模式,也叫 Dispatcher调度器模式或 Notifier通知器模式:
 * 监听事件的发生，当有事件发生后，分发给其对应的处理器处理(此处使用多线程实现异步处理，多个handler处理器同时执行)
 *
 * boss只监听连接事件,当有连接事件发生后,它会得到连接的客户端通道,把这些连接通道转给worker,
 * worker把这些连接注册到自己的selector上,不断监听通道的读写事件.
 *
 * 也就是说,boss的selector上只注册着一个channel,那就是服务器的
 * serversocketchannel,感兴趣的事件是连接事件.
 * 而worker的seler上注册着所有客户端的连接,感兴趣的事件是读或写事件
 *
 * 一个boss可以对应多个worker
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
