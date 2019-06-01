package channelandbuffer;

import java.io.FileInputStream;

/**
 * @author wuyuan
 * @version 1.0
 * @date 2019/6/1
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("folder\\NioTest1");
        FileInputStream out = new FileInputStream("folder\\NioTest2");
    }
}
