package niodemo1;

import java.nio.ByteBuffer;

/**
 * 直接缓冲区与非直接缓冲区
 * 
 * 1.非直接缓冲区(TestBuffer1中的缓冲区就是):通过allocate()方法创建缓冲区,将缓冲区建立在了JVM的内存中
 * 2.直接缓冲区:通过allocateDirect()方法创建缓冲区，将缓冲区建立在了操作系统的物理内存中,在某种情况下可以提高效率
 *
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/5/31
 */
public class TestBuffer2 {

	public static void main(String[] args) {

		//1.创建直接缓冲区
		ByteBuffer buf = ByteBuffer.allocateDirect(1024);
		//2.判断一下是不是直接缓冲区
		System.out.println(buf.isDirect());
		
		
	}

}
