package niodemo1;

import java.nio.ByteBuffer;

/**
 * ֱ�ӻ��������ֱ�ӻ�����
 * 
 * 1.��ֱ�ӻ�����(TestBuffer1�еĻ���������):ͨ��allocate()��������������,����������������JVM���ڴ���
 * 2.ֱ�ӻ�����:ͨ��allocateDirect()�������������������������������˲���ϵͳ�������ڴ���,��ĳ������¿������Ч��
 *
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/5/31
 */
public class TestBuffer2 {

	public static void main(String[] args) {

		//1.����ֱ�ӻ�����
		ByteBuffer buf = ByteBuffer.allocateDirect(1024);
		//2.�ж�һ���ǲ���ֱ�ӻ�����
		System.out.println(buf.isDirect());
		
		
	}

}
