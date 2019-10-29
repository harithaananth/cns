import java.io.*;
import java.util.*;
import java.lang.Math;
import java.math.*;

class SHA12
{
	public static int[] K = new int[80];

	public static void initialise_buffer(int[] buffer)
	{
		for(int i=16;i<80;i++)
		{
			buffer[i] = Integer.rotateLeft(buffer[i-3]^buffer[i-8]^buffer[i-14]^buffer[i-16],1);
		}
	}
	public static void main(String[] args)
	{
		System.out.println("SHA - 1 : ");
		System.out.println("Enter the input string : ");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		byte[] message = input.getBytes();
		int[] buffer = new int[80];
		int ii = 0;
		for(ii=0;ii<message.length;ii++)
			buffer[ii>>2] = message[ii] << (24 - (ii%4)*8) | buffer[ii>>2];
		buffer[ii>>2] = 0x80 << (24 - (ii%4)*8) | buffer[ii>>2];

		buffer[15] = message.length * 8;
		initialise_buffer(buffer);
		for(int i=0;i<20;i++)
			K[i] = (int) 0x5A827999L;
		for(int i=20;i<40;i++)
			K[i] = (int) 0x6ED9EBA1L;
		for(int i=40;i<60;i++)
			K[i] = (int) 0x8F1BBCDCL;
		for(int i=60;i<80;i++)
			K[i] = (int) 0xCA62C1D6L;
		for(int i=0;i<80;i++)
			System.out.println(buffer[i]);
		int a = 0x67452301;
		int b = (int) 0xEFCDAB89L;
		int c = (int) 0x98BADCFEL;
		int d = 0x10325476;
		int e = (int) 0xC3D2E1F0L;
		int oa = 0x67452301;
		int ob = (int) 0xEFCDAB89L;
		int oc = (int) 0x98BADCFEL;
		int od = 0x10325476;
		int oe = (int) 0xC3D2E1F0L;

		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);

		for(int i=0;i<80;i++)
		{
			int f = 0;
			if(i<20)
			{
				f = (b&c)|(~b&d);
			}
			else if(i>19 && i<40)
			{
				f = b^c^d; 
			}
			else if(i>39 && i<60)
			{
				f = (b&c)|(b&d)|(c&d);
			}
			else if(i>59 && i<80)
			{
				f = b^c^d; 
			}
			int temp = e+f+Integer.rotateLeft(a,5)+buffer[i]+K[i];
			e = d;
			d = c;
			c = Integer.rotateLeft(b,30);
			b = a;
			a = temp;

			System.out.println(i);
			System.out.println("a: " + a);
            System.out.println("b: " + b);
            System.out.println("c: " + c);
            System.out.println("d: " + d);
            System.out.println("e: " + e);
            System.out.println("\n");
		}
		a = a+oa;
		b = b+ob;
		c = c+oc;
		d = d+od;
		e = e+oe;

		int[] words = {a, b, c, d, e, 0};
        String result = "";
        for (int i = 0; i < 5; i++) {
            String temp = Integer.toHexString(words[i]);
            while (temp.length() < 8) {
                temp = "0" + temp;
            }
            result += temp;
        }
        System.out.println(result);

	}
}