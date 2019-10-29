import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;
class RC{
    int key[];
    int n,m ;
    void print(String str){
        System.out.println(str);
    }
    void setKey(String str){
        
        m = str.length();
        key = new int[m];
        for(int i=0;i<m;i++){
            key[i] = Integer.parseInt("" + str.charAt(i));
        }
        print("Key : " + str);
    }
    void printMat(String str,char arr[][],int n,int m){
        print(str);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(arr[i][j] + " ");
            }
            print("");
        }
    }
    String encode(String str){
        while(str.length()%m!=0)
        {
            str+="x";
        }
        n = str.length()/m;
        print("n = " + n +", m = " + m);
        char inputMatrix[][] = new char[n][m];
        int k=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                inputMatrix[i][j] = str.charAt(k);
                k++;
            }
        }
        printMat("Input Matrix : ", inputMatrix, n, m);
        String cipher="";
        int count = 0;
        while(count<m)
        {
            for(int k1=0;k1<m;k1++){
                if(key[k1]==count)
                {
                    int j=k1;
                    for(int i=0;i<n;i++)
                    {
                        cipher+=inputMatrix[i][j];
                    }
                    count++;
                }
            }
        }
        print("Cipher : "+ cipher);
        return cipher;
    }
    String decode(String keyInput,String cipher){
        n = cipher.length()/m;
        int count = 0,charIndex=0;
        char decodeMat[][] = new char[n][m];
        while(count<m)
        {
            for(int k=0;k<m;k++){
                if(key[k] == count){
                    int j = k;
                    for(int i=0;i<n;i++){
                        decodeMat[i][j] = cipher.charAt(charIndex);
                        charIndex++;
                    }
                }
            }
            count++;
        }
        printMat("Decode Mat", decodeMat, n, m);
        String msg="";
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                msg+=decodeMat[i][j];
            }
        }
        print("Message : " + msg);
        return msg;
    }
}
public class RowColumn {

    public static void main(String[] args) {
        // TODO code application logic here
        RC rc = new RC();
        rc.setKey("4312560");
        String cipher = rc.encode("attackpostponeduntiltwoam");
        rc.decode("4312560",cipher);
    }
    
}
