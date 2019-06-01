package niodemo1;

import java.nio.ByteBuffer;

/**
 *
 * 1.��������Buffer�����������ݵĴ�ȡ���������ײ������飬���ڴ洢��ͬ���������͵�����
 *
 *                    �����������͵Ĳ�ͬ��boolean���⣩���ṩ�˲�ͬ�Ļ�������
 *                       ByteBuffer --> **���**
 *                       CharBuffer
 *                       ShortBuffer
 *                       IntBuffer
 *                       LongBuffer
 *                       FloatBuffer
 *                       DoubleBuffer
 *
 *                    ���ǵĹ���ʽ����һ����
 *                       A.ͨ��allocate()��������������
 *                       B.ͨ��put()���������ݴ��뻺����
 *                       C.ͨ��get()������ȡ�������е�����
 *
 *
 * 2.�������е��ĸ���������(��Buffer���ж���)��
 *
 *  private int position = 0;  ----->>  λ�ã���ʾ�����������ڲ��������ݵ�λ��
 *  private int limit;  ----->>  ���ޣ���ʾ�������п��Բ��������ݴ�С����limit��������ݲ��ܽ��ж�д��
 *  private int capacity;  ----->>  ��������ʾ�����������洢���ݵ�������һ�������Ͳ��ܸı䡣Ҳ��������һ���������Ĵ�С�Ͳ��ܸı���
 *  private int mark = -1;  ----->>  ��ǣ���¼��ǰposition��λ�ã�֮���ͨ��reset()����ʹ��ǰposition�ָ���mark()����¼��λ��
 *
 *  ��С��ϵ: 0 <= mark <= position <= limit <= capacity
 *
 *
 *  ************�������пɲ���������λ��position��limit֮��*************
 *
 *
 *
 * @author wuyuan
 * @version 1.0
 * @date 2019/5/31
 */
public class TestBuffer1 {

    public static void main(String[] args) {

        System.out.println("-----------------------------allocate()------------------------------------");
        //1������һ��ָ����С�Ļ�����
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------put()----------------------------------------");
        //2.����put()����,�������ݵ�������
        String str = "wuyuan";
        buf.put(str.getBytes());//д����ģʽ
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------flip()----------------------------------------");
        /*3.�л�Ϊ�����ݵ�ģʽ,Ҳ��������position��limit��λ�ã�
         *  д�����ݺ�position�����ݵ���һ��λ�ã��������ݵĻ�positionҪλ��Ҫ���������ݵ���ǰ��
         *
         *  get()������ȡ����position��limit֮������ݣ�
         *  ����flip()���������еĲ�����: limit = position; position = 0;
         */
        buf.flip();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------get()----------------------------------------");
        //4.����get()������ȡ�������е�����
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);//get()������ȡ����position��limit֮�������
        System.out.println(new String(dst, 0, dst.length));

        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------mark()��reset()----------------------------------------");
        //5.mark()��reset()��������Ǻͻָ�position��λ��
        buf.flip();
        byte[] dst1 = new byte[buf.limit()];
        buf.get(dst1, 1, 2);
        System.out.println(new String(dst1, 0, dst1.length));
        System.out.println(buf.position());

        buf.mark();
        buf.get(dst1, 2, 3);
        System.out.println(new String(dst1, 0, dst1.length));
        System.out.println(buf.position());

        buf.reset();
        System.out.println(buf.position());

        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-----------------------------hasRemaining()��remaining()----------------------------------------");
        //6.�жϻ��������Ƿ񻹴��ڿɲ��������ݲ�����ɲ������ݵ�����
        if (buf.hasRemaining()) {
            //����ɲ��������ݵ�����,Ҳ����position��limit֮�仹�ж������� == (limit-position)
            System.out.println(buf.remaining());
        }

        System.out.println("-----------------------------clear()----------------------------------------");
        /*6.��ջ�����
         *   clear()����ֻ��������position��limit��λ�ã���������ݴ��ڡ�������״̬��
         *   ʹ position = 0; limit = capacity;
         */
        buf.clear();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //clear()�󣬻������е����ݻ���
        byte[] dstq = new byte[buf.limit()];
        buf.get(dstq);
        System.out.println(new String(dstq));

    }

}
