import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.Math;
class DES2{
    public static String[] keys = new String[16];
    private static int[] ip = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36,
            28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32,
            24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19,
            11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };
    private static int[] ip_inverse = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47,
            15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13,
            53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51,
            19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17,
            57, 25 };
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
            private static int[] pc1 = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34,
            26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63,
            55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53,
            45, 37, 29, 21, 13, 5, 28, 20, 12, 4 };
            private static int[] pc2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29,
            32 };
            private static int[] e_table = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8,
            9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32,
            1 };
            private static int[] P = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5,
            18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4,
            25};
            private static int[] keyShift = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2,
            2, 1 };
            
            public static String chstring2binary64bit(String input)
            {
                String ret_string = "";
                for(int i=0;i<input.length();i++)
                {
                    int x = (int)input.charAt(i);
                    String temp = Integer.toBinaryString(x);
                   // System.out.println("INPUT STRING:"+temp);

                    while(temp.length()%8!=0)
                    {
                        temp = "0"+ temp;
                    }
                    ret_string = ret_string + temp;
                }
                while(ret_string.length()%64!=0)
                    ret_string = "0"+ret_string ;
                return ret_string;
            }
            public static String initial_permutation(String input)
            {
                char[] return_charArray = new char[64];
                for(int i=0;i<64;i++)
                {
                    return_charArray[i] = input.charAt(ip[i]-1);
                }
                String ret_string = new String(return_charArray);
                return ret_string;
            }
            public static String inverseInitial_permutation(String input)
            {
                char[] return_charArray = new char[64];
                for(int i=0;i<64;i++)
                {
                    return_charArray[i] = input.charAt(ip_inverse[i]-1);
                }
                String ret_string = new String(return_charArray);
                return ret_string;
            }
            public static String expansion(String input)
            {
                char[] return_charArray = new char[48];
                for(int i=0;i<e_table.length;i++)
                {
                    return_charArray[i] = input.charAt(e_table[i]-1);
                }
                String ret_string = new String(return_charArray);
                return ret_string;
            }
            public static String keyPc1(String input)
            {
                char[] return_charArray = new char[56];
                for(int i=0;i<pc1.length;i++)
                {
                    return_charArray[i] = input.charAt(pc1[i]-1);
                }
                String ret_string = new String(return_charArray);
                return ret_string;
            }
            public static String keyPc2(String input)
            {
                char[] return_charArray = new char[48];
                for(int i=0;i<pc2.length;i++)
                {
                    return_charArray[i] = input.charAt(pc2[i]-1);
                }
                String ret_string = new String(return_charArray);
                return ret_string;
            }
            public static String permute(String input)
            {
                char[] return_charArray = new char[32];
                for(int i=0;i<P.length;i++)
                {
                    return_charArray[i] = input.charAt(P[i]-1);
                }
                String ret_string = new String(return_charArray);
                return ret_string;
            }
            public static String rotate_once(String str)
            {
                String ret_string = "";
                String temp = str.charAt(0)+"";
                for(int i=1;i<str.length();i++)
                {
                    ret_string = ret_string + str.charAt(i);
                }
                ret_string = ret_string + temp;
                return ret_string;
            }
            public static String rotate(String str,int i)
            {
                for(int j=0;j<i;j++)
                {
                    str = rotate_once(str);
                }
                return str;
            }
            public static String xor(String str1, String str2)
            {
                if(str1.length()!=str2.length())
                {
                    System.out.println("Not equal strings");
                    return null;
                }
                String ret_string = "";
                for(int i=0;i<str1.length();i++)
                {
                    if(str1.charAt(i)=='0' && str2.charAt(i)=='0')
                        ret_string = ret_string + "0";
                    else if(str1.charAt(i)=='1' && str2.charAt(i)=='1')
                        ret_string = ret_string + "0";
                    else if(str1.charAt(i)=='1' && str2.charAt(i)=='0')
                        ret_string = ret_string + "1";
                    else if(str1.charAt(i)=='0' && str2.charAt(i)=='1')
                        ret_string = ret_string + "1";
                }
                return ret_string;

            }
            public static String sbox_internal(String input,int i)
            {
                //System.out.println("Inside sbox");
                String rowString = "" + input.charAt(0)+input.charAt(5);
                String colString = "" + input.charAt(1)+input.charAt(2)+input.charAt(3)+input.charAt(4);
                int row = Integer.parseInt(rowString,2);
                //System.out.println(row);
                int col = Integer.parseInt(colString,2);
                //System.out.println(col);
                int retval = s_table[i][row][col];
                String ret_string = Integer.toBinaryString(retval);
                while(ret_string.length()%4!=0)
                    ret_string = "0" + ret_string;

                return ret_string;
            }
            public static String sbox(String input)
            {
                //8 words of 6 bits each
                int value = 0;
                String[] words = new String[8];
                String ret_string = "";

                for(int i=0;i<8;i++)
                {
                    words[i] = input.substring(0+value,6+value);
                    value = value + 6;
                }
                for(int i = 0;i<8;i++)
                {
                    ret_string = ret_string + sbox_internal(words[i],i);
                }

                return ret_string;
            }

            public static String start_encryption(String input,String key)
            {
                String workingString = input;
                String workingKey = key;
                workingString = initial_permutation(workingString);
                workingKey = keyPc1(workingKey);//56 bits
                //System.out.println("key : " + workingKey);
                for(int i=0;i<16;i++)
                {
                    //workingKey = rotate(workingKey,keyShift[i]);
                    String workingKey11 = workingKey.substring(0,28);
                    String workingKey12 = workingKey.substring(28,56);
                    workingKey11 = rotate(workingKey11,keyShift[i]);
                    workingKey12 = rotate(workingKey12,keyShift[i]);
                    workingKey = workingKey11 + workingKey12;
                    //System.out.println("key : " + workingKey);
                    String workingKey2 = keyPc2(workingKey);//48 bits
                    keys[i] = workingKey2;

                    String left = workingString.substring(0,32);
                    String right = workingString.substring(32,64);
                    String temp = right;
                    right = expansion(right);//48 bits
                    right = xor(right,workingKey2);
                    //System.out.println("After xor "+ right);
                    right = sbox(right);
                    //System.out.println(right);
                    right = permute(right);
                    //System.out.println(right.length());
                    right = xor(right,left);
                    left = temp;
                    workingString = left+right;
                    //System.out.println(workingString);
                }
                String left = workingString.substring(0,32);
                String right = workingString.substring(32,64);
                workingString = right+left;
                workingString = inverseInitial_permutation(workingString);

                return workingString;
            }
            public static String start_decryption(String input)
            {
                String workingString = input;
                System.out.println("inside decryption...");
                //String workingKey = key;
                workingString = initial_permutation(workingString);
                //workingKey = keyPc1(workingKey);//56 bits
                for(int i=0;i<16;i++)
                {
                

                    String left = workingString.substring(0,32);
                    String right = workingString.substring(32,64);

                    String temp = right;

                    right = expansion(right);//48 bits
                    right = xor(right,keys[15-i]);
                    right = sbox(right);
                    
                    right = permute(right);
                    //System.out.println(right.length());
                    right = xor(right,left);
                    left = temp;
                    workingString = left+right;
                }
                String left = workingString.substring(0,32);
                String right = workingString.substring(32,64);
                workingString = right+left;
                workingString = inverseInitial_permutation(workingString);
                return workingString;
            }
            public static void main(String[] args)
            {
                System.out.println("Enter the input string");
                Scanner in = new Scanner(System.in);
                String input = in.nextLine();
                System.out.println("Enter the Key");
                long key = in.nextLong();
                String key64bit = Long.toBinaryString(key);
                while(key64bit.length()%64!=0)
                    key64bit = "0" + key64bit;
                //System.out.println(key64bit +" :"+key64bit.length());
                String binaryinput = chstring2binary64bit(input);
                //System.out.println(binaryinput+" :"+binaryinput.length());
                String encrypted = start_encryption(binaryinput,key64bit);
                System.out.println("\nenced:"+ encrypted);

                String decrypted = start_decryption(encrypted);
                System.out.println("\ndecryoted:"+decrypted);
                //System.out.println(encrypted);

                BigInteger enc = new BigInteger(encrypted,2);
                System.out.println(enc.toString(16));
                enc = new BigInteger(decrypted,2);
                System.out.println(enc.toString(16));
            }
}