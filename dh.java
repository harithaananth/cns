import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.Math;

class dh
{
	static Boolean miller_rabin(int n,int d)
	{
		int a = (int)(Math.random() * ((n-2)-1) + 1); // random * (max-min) + min
		System.out.println(a);
		int x = (int)(Math.pow(a,d)) % n;
		System.out.println(x);
		if(x == n-1 || x == 1)
			return true;
		x = x*x % n;
		if(x == 1)
			return false;
		else if(x == n-1)
			return true;
		return false;
	}

	public static void primefactors(HashSet<Integer> s,int phi)
	{
		BigInteger pc = new BigInteger("2");
		Integer tempi = new Integer(phi);
		String temps = tempi.toString();
		BigInteger phib = new BigInteger(temps);
		for(;pc.compareTo(phib)==-1;pc=pc.nextProbablePrime())
		{
			if(phib.mod(pc).compareTo(BigInteger.ZERO)==0)
			{
				s.add(pc.intValue());
			}
		}
	}
	
	public static void main(String[] args)
	{
		HashSet<Integer> s = new HashSet<Integer>();
		BigInteger pri = new BigInteger("760");
		BigInteger prime = pri.nextProbablePrime();
		int n = prime.intValue();
		System.out.println(n+"\n");
		int nminus1 = n-1;
		int r1=0;
		while(nminus1%2==0)
		{
			nminus1=nminus1/2;
			r1=r1+1;
		}
		int d = nminus1;
		
		primefactors(s,n-1);
		for(Integer a:s)
			System.out.println(a);
		System.out.println('\n');
		int phi = n-1;
		int flag;
		int flag2= 0;
		String temps;
		int g=0;
		for(int r=2;r<phi;r++)
		{
			flag = 0;
			for(Integer a:s)
			{
				Integer tempi1 = new Integer(a);
				temps = tempi1.toString();
				BigInteger ab = new BigInteger(temps);
				Integer tempi2 = new Integer(phi);
				temps = tempi2.toString();
				BigInteger phib = new BigInteger(temps);
				Integer tempi3 = new Integer(r);
				temps = tempi3.toString();
				BigInteger rb = new BigInteger(temps);
				Integer tempi4 = new Integer(n);
				temps = tempi4.toString();
				BigInteger nb = new BigInteger(temps);
				if(rb.pow(phib.divide(ab).intValue()).mod(nb).compareTo(BigInteger.ONE)==0)
				{
					//System.out.println(r+" "+phi+" "+a);
					flag = 1;
					break;
				}
			}
			if(flag==0)
			{
				System.out.println(r);
				g = r;
				flag2=1;
			}
			if(flag2==1)
				break;

		}

		Integer tempi1 = new Integer(g);
		temps = tempi1.toString();
		BigInteger gb = new BigInteger(temps);
		Integer tempi4 = new Integer(n);
		temps = tempi4.toString();
		BigInteger nb = new BigInteger(temps);
		System.out.println("Enter Xa secret key");
		Scanner in = new Scanner(System.in);
		int Xa = in.nextInt();
		System.out.println("Public Key of Xa is " + gb.pow(Xa).mod(nb));
		System.out.println("Enter Xb secret key");
		int Xb = in.nextInt();
		System.out.println("Public Key of Xb is " + gb.pow(Xb).mod(nb));


		BigInteger Ya = gb.pow(Xa).mod(nb);
		BigInteger Yb = gb.pow(Xb).mod(nb);

		BigInteger k1 = Ya.pow(Xb).mod(nb);
		BigInteger k2 = Yb.pow(Xa).mod(nb);

		System.out.println(k1+" "+k2);


	}
}