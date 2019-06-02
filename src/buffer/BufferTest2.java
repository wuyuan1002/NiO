package buffer;

import java.nio.ByteBuffer;

/**
 * ֱ�ӻ��������ֱ�ӻ�����:
 * 
 * 1.��ֱ�ӻ�����: ͨ��allocate()��������������,����������������JVM���ڴ���(����)������ͨ������. JVM����ֱ�ӹܿ�.
 * 				  ���ڳ�����io�豸(�����)�򽻵�ʱ�������ڲ���ϵͳ�ڴ��JVM�ڴ�(��)����һ��copy�Ĺ��̡�
 *
 * 2.ֱ�ӻ�����: ͨ��allocateDirect()�����������������������������ڲ���ϵͳ�������ڴ���,JVM�޷�ֱ�ӹܿأ�
 * 				ʡȥ���м��copy���̣���ĳ������¿������Ч��.
 * 				ȱ��:
 * 					1.�������Ŀ���Ȩ�ڲ���ϵͳ��������������ǲ����׿���
 * 					2.����ȫ
 * 					3.��Դ�ķѴ�
 * 					4.��ʱ�����ڴ治��ȷ��
 *
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/5/31
 */
public class BufferTest2 {

	public static void main(String[] args) {

		//1.����ֱ�ӻ�����
		ByteBuffer buf1 = ByteBuffer.allocate(1024);
		//2.�ж��ǲ���ֱ�ӻ�����
		System.out.println(buf1.isDirect());


		//1.����ֱ�ӻ�����
		ByteBuffer buf2 = ByteBuffer.allocateDirect(1024);
		//2.�ж��ǲ���ֱ�ӻ�����
		System.out.println(buf2.isDirect());
		
		
	}

}
