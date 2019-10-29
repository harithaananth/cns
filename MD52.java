import java.io.*;
import java.lang.Math;
import java.util.*;
import java.math.*;

class MD52
{
	static int[] T = new int[64];
	static BigInteger[] Tb = new BigInteger[64];
	static int[] S = {	7,12,17,22,7,12,17,22,7,12,17,22,7,12,17,22,
						5,9,14,20,5,9,14,20,5,9,14,20,5,9,14,20,
						4,11,16,23,4,11,16,23,4,11,16,23,4,11,16,23,
						6,10,15,21,6,10,15,21,6,10,15,21,6,10,15,21};
	

	public static void main(String[] args)
	{
		
		for (int i=0; i<64;i++)
		{
        	T[i] = (int) (long) ((1L << 32) * Math.abs(Math.sin(i + 1)));
		}
		
		System.out.print("Enter the message : ");
		Scanner in = new Scanner(System.in);
		
		String input = in.nextLine();
		byte[] message = input.getBytes();
		
		int messageLenBytes = message.length;
        long messageLenBits = (long) messageLenBytes * 8;

    	//int numBlocks = messageLenBytes/64 + 1; //1000000 is 64 in decimal, means that message with 64 characters needs new block
    	
    	byte[] paddingBytes = new byte[64 - messageLenBytes]; //assuming only one block
    	paddingBytes[0] = (byte) 0x80;
    	for (int i = 0; i < 8; i++) 
    	{
        	paddingBytes[paddingBytes.length - 8 + i] = (byte) messageLenBits;
        	messageLenBits >>>= 8;
    	}
    	int[] buffer = new int[16];
        int index = 0;
        for (int j = 0; j < 64; j++, index++)
            	buffer[j >>> 2] = ((int) ((index < messageLenBytes) ? message[index]: paddingBytes[index - messageLenBytes]) << 24)| 
            (buffer[j >>> 2] >>> 8);
    
        for(int j = 0;j<16;j++)
        {
        	System.out.println(buffer[j] + " ");
        }
        int a = 0x67452301;
		int b = (int) 0xEFCDAB89L;
		int c = (int) 0x98BADCFEL;
		int d = 0x10325476;
		int oa = 0x67452301;
		int ob = (int) 0xEFCDAB89L;
		int oc = (int) 0x98BADCFEL;
		int od = 0x10325476;
        for(int i=0;i<64;i++)
        {
        	int f = 0;
        	int g = 0;
        	if(i<16)
        	{
        		f = (b & c) | (~b & d);
        		g = i; 
        	}
        	else if(i>15 && i<32)
        	{
        		f = (b & d) | (c & ~d);
                g = (i*5 + 1) % 16;

        	}
        	else if(i>31 && i<48)
        	{
        		f = b^c^d;
                g = (i*3 + 5) % 16;
        	}
        	else if(i>47 && i<64)
        	{
        		f = c ^ (b | ~d);
                g = (7*i)%16;
        	}
        	int temp = b + Integer.rotateLeft(a + f + buffer[g] + T[i],S[i]);
        	a = d;
        	d = c;
        	c = b;
        	b = temp;

        }
        a = a+oa;
        b = b+ob;
        c = c+oc;
        d = d+od;

        byte[] md5 = new byte[16];
    	int count = 0;
    	for(int i=0;i<4;i++)
		{
			int n=0;
			if(i==0)
				n = a;
			else if(i==1)
				n = b;
			else if(i==2)
				n = c;
			else if(i==3)
				n = d;
			for(int j=0;j<4;j++)
			{
				md5[count] = (byte)n;
				count = count + 1;
				n>>>=8;
			}
		}

    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < md5.length; i++)
    	{
        	sb.append(String.format("%02X", md5[i]));
    	}
    	System.out.println(sb.toString());
	}
}