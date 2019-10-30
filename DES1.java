import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class DES
{
	private static int[] ip = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36,
			28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32,
			24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19,
			11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
    private static int[] ip_inverse = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47,
			15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13,
			53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51,
			19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17,
			57, 25 };
        // initial key permutation 64 => 56 biti
	private static int[] pc1 = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34,
			26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63,
			55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53,
			45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };
	// key permutation at round i 56 => 48
	private static int[] pc2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10,
			23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55,
			30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29,
			32 };
        	// expansion permutation from function f
	private static int[] e_table = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8,
			9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21,
			20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32,
			1 };
    private static int[] keyShift = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2,
			2, 1 };
    private static int[] P = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5,
			18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4,
			25

	};
	// substitution boxes
	private static int[][][] s_table = {
			{ 		{ 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
					{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
					{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
					{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } 
			},
			{ 		{ 15, 1, 8, 14, 6, 11, 3, 2, 9, 7, 2, 13, 12, 0, 5, 10 },
					{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
					{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
					{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } 
			},
			{ 		{ 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
					{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
					{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
					{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } 
			},
			{ 		{ 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
					{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
					{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
					{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } 
			},
			{ 		{ 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
					{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
					{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
					{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } 
			},
			{ 		{ 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
					{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
					{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
					{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }

			},
			{ 		{ 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
					{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
					{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
					{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }

			},
			{ 		{ 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
					{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
					{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
					{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }

			} };

	String[] subkeys= new String[17];

	DES()
	{
		// System.out.println("Hud");
	}

	String plaintoHex(String p)
	{
		String hex="";
		for(int i=0;i<p.length();i++)
		{
			int a=p.charAt(i);
			hex+=Integer.toHexString(a);
		}
		return hex;
	}

	String HextoBinary(String hexm)
	{
		String bin="";
		for(int i=0;i<16;i++)
		{
			String t1=Integer.toBinaryString(Integer.parseInt(hexm.charAt(i)+"",16));
			String four="";
			for(int j=t1.length();j<4;j++)
			{
				four+="0";
			}
			four+=t1;
			bin+=four;
		}
		return bin;
	}

	String permutate_pc1(String input)
	{
		String res="";
		for(int i=0;i<56;i++)
		{
			res+=input.charAt(pc1[i]-1);
		}
		return res;
	}

	String permutate_ip(String input)
	{
		String res="";
		for(int i=0;i<64;i++)
		{
			res+=input.charAt(ip[i]-1);
		}
		return res;
	}

	String leftCircularShift(String pr_key,int r)
	{
		int count=keyShift[r-1];
		String C=pr_key.substring(0,28);
		String D=pr_key.substring(28);
		C=C.substring(count)+C.substring(0,count);
		D=D.substring(count)+D.substring(0,count);
		String shifted_string=C+D;
		return shifted_string;
	}

	String permutate_pc2(String input)
	{
		String res="";
		for(int i=0;i<48;i++)
		{
			res+=input.charAt(pc2[i]-1);
		}
		return res;
	}

	String performround(String pc,String key)
	{
		String lh=pc.substring(0,32);
		String rh=pc.substring(32);
		String expand_string=expand(rh);
		String xor_string=XOR_operation(expand_string,key);
		String round_oper_res="";
		for(int i=0;i<48;i+=6)
		{
			String substr=xor_string.substring(i,i+6);
			String temp=substr.charAt(0)+""+substr.charAt(5)+"";
			int row=Integer.parseInt(temp,2);
			int column=Integer.parseInt(substr.substring(1,5),2);
			int val=s_table[i/6][row][column];
			String t1=Integer.toBinaryString(val);
			String fourbit="";
			for(int j=t1.length();j<4;j++)
			{
				fourbit+="0";
			}
			fourbit+=t1;
			round_oper_res+=fourbit;
		}
		String expanded_round_oper_res=permutate_p(round_oper_res);
		String new_rh=XOR_operation(expanded_round_oper_res,lh);
		String new_lh=rh;
		String roundresult=new_lh+new_rh;
		return roundresult;		
	}

	String expand(String input)
	{
		String res="";
		for(int i=0;i<48;i++)
		{
			res+=input.charAt(e_table[i]-1);
		}
		return res;
	}

	String XOR_operation(String s1,String s2)
	{
		String xor="";
		for(int i=0;i<s1.length();i++)
		{
			if(s1.charAt(i)==s2.charAt(i))
				xor+="0";
			else
				xor+="1";
		}
		return xor;
	}

	String permutate_p(String input)
	{
		String res="";
		for(int i=0;i<32;i++)
		{
			res+=input.charAt(P[i]-1);
		}
		return res;
	}

	String permutate_inv(String input)
	{
		String res="";
		for(int i=0;i<64;i++)
		{
			res+=input.charAt(ip_inverse[i]-1);
		}
		return res;
	}

	String binToHexa(String in)
	{
		String hex="0123456789ABCDEF";
		String hexa="";
		for(int i=0;i<64;i+=4)
		{
			hexa+=hex.charAt(Integer.parseInt(in.substring(i,i+4),2));
		}
		return hexa;	
	}  

	String hexaToPlain(String hmes)
	{
		String ans="";
		for(int i=0;i+1<hmes.length();i+=2)
		{
			String hexval=hmes.substring(i,i+2);
			int dec_val=Integer.parseInt(hexval,16);
			char c=(char)dec_val;
			ans+=c;
		}
		return ans;	
	}

	String encrypt(String mes)
	{
		String hexmes=plaintoHex(mes);
		String plain=HextoBinary(hexmes);
		System.out.println("Plain text : "+mes);
		System.out.println("Plain text(Hexa) : "+hexmes);
		String initial_key="0100100101000101010011110100011001001001010101010010001100110000";
		String pc1_key=permutate_pc1(initial_key);
		String prev_cipher=permutate_ip(plain);
		String prev_key=pc1_key;
		for(int round=1;round<=16;round++)
		{
			prev_key =leftCircularShift(prev_key,round);
			subkeys[round]=permutate_pc2(prev_key);
			prev_cipher=performround(prev_cipher,subkeys[round]);
		}
		String swapfinal=prev_cipher.substring(32)+prev_cipher.substring(0,32);
		String final_perm=permutate_inv(swapfinal);
		String en=binToHexa(final_perm);
		System.out.println("FINAL CIPHER : "+en);
		return en;
	}

	String decrypt(String mes)
	{
		String hexmessage=mes;
		String cipher=HextoBinary(hexmessage);
		String previous_cipher=permutate_ip(cipher);
		System.out.println("DECRYPTION : ");
		for(int round=16;round>=1;round--)
		{
			previous_cipher=performround(previous_cipher,subkeys[round]);	
		}
		previous_cipher=previous_cipher.substring(32)+previous_cipher.substring(0,32);
		String decrypted_cipher=permutate_inv(previous_cipher);
		String decrypted_text=binToHexa(decrypted_cipher);
		return decrypted_text;
	}	
}

class DES1
{
	public static void main(String[] args) 
	{
		DES des=new DES();
		String en=des.encrypt("security");	
		String de=des.decrypt(en);	
		System.out.println("Decrypted hex mes : "+de);
		System.out.println("Decrypted final : "+des.hexaToPlain(de));
	}
}
