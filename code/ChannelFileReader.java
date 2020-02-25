package code;/*
             这部分代码是为了控制文件的读写，
             分次读入大文件，并写入不同的小文件。
             */

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class ChannelFileReader
{
    private FileInputStream fileIn;
    private ByteBuffer byteBuf;
    private long fileLength;
    private int arraySize;
    private byte[] array;

    public ChannelFileReader(String fileName, int arraySize) throws IOException {
        this.fileIn = new FileInputStream(fileName);
        this.fileLength = fileIn.getChannel().size();
        this.arraySize = arraySize;
        this.byteBuf = ByteBuffer.allocate(arraySize);
    }
 
    public int read() throws IOException {
        FileChannel fileChannel = fileIn.getChannel();
        int bytes = fileChannel.read(byteBuf);// 读取到ByteBuffer中
        if (bytes != -1) {
            array = new byte[bytes];// 字节数组长度为已读取长度
            byteBuf.flip();
            byteBuf.get(array);// 从ByteBuffer中得到字节数组
            byteBuf.clear();
            return bytes;
        }
        return -1;
    }
 
    public void close() throws IOException {
        fileIn.close();
        array = null;
    }
 
    public byte[] getArray() {
        return array;
    }
 
    public long getFileLength() {
        return fileLength;
    }
 
    public static void main(String[] args) throws IOException {
        ChannelFileReader reader = new ChannelFileReader("xxx", 65536); //读取10G目标文件，读取指定的大小；
    
        while (reader.read() != -1) ;
     {
        String url = new String(reader.array); //把字节数组读成字符串；

        String[] urls = url.split(",");//把url字符串文件转换成url数组；

        for(String index : urls) //根据url hash%50后的值存到不同的文件
        {
           int num = Calculation.hashCode(index);

           String  theurl = "D:/input"+num+".txt";

           /*
           以下为写入文件到目标的url
           */
           
           FileOutputStream outputStream = new FileOutputStream(theurl, true); //文件内容追加模式--推荐
		FileChannel channel = outputStream.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(String.valueOf(num));
		byteBuffer.put(String.valueOf(num).getBytes("utf-8"));
		byteBuffer.flip();//读取模式转换为写入模式
		channel.write(byteBuffer);
		channel.close();
		if(outputStream != null){
			outputStream.close();
		}



        }
          


        reader.close();     
    
     }

     
    }


}