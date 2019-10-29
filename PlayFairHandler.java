/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;

class PlayFair{
    char matrix[][] = new char[5][5];
    void print(String str){
        System.out.println(str);
    }
    String remDuplicates(String str1,String str2){
        for(int i=0;i<str2.length();i++){
            char c = str2.charAt(i);
            if(!str1.contains("" + c))
                str1+=c;
        }
        return str1;
    }
    
    void generateMatrix(String key){
        key = key.replace('j','i');
        String uniqueKey = remDuplicates("", key);
        print(uniqueKey);
        String alphabets = "abcdefghiklmnopqrstuvwxyz";
        String matString = remDuplicates(uniqueKey, alphabets);
        print(matString);
        print("Length : " + matString.length());
        int k=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                matrix[i][j] = matString.charAt(k);
                k++;
                System.out.print(matrix[i][j] + " ");
            }
            print("");
        }
    }
    String _formatInput(String input){
        input = input.replace('j','i');
        for(int i=0;i+1<input.length();i+=2)
        {
            if(input.charAt(i)==input.charAt(i+1))
            {
                input = input.substring(0,i+1) + "x" + input.substring(i+1);
            }
        }
        if(input.length()%2!=0)
            input+="x";
        return input;
    }
    String getCipherPair(char a,char b){
        int r1=0,c1=0,r2=0,c2=0;
        int newr1,newr2,newc1,newc2;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(matrix[i][j]==a){
                    r1 = i;
                    c1 = j;
                }
                if(matrix[i][j]==b){
                    r2 = i;
                    c2 = j;
                }
            }
        }
        if(r1==r2){
            newr1 = r1;
            newr2 = r2;
            newc1 = (c1+1)%5;
            newc2 = (c2+1)%5;
        }
        else if(c1==c2){
            newc1 = c1;
            newc2 = c2;
            newr1 = (r1+1)%5;
            newr2 = (r2+1)%5;
        }
        else{
            newr1 = r1;
            newc1 = c2;
            newr2 = r2;
            newc2 = c1;
        }
        return "" + matrix[newr1][newc1] + matrix[newr2][newc2];
    }
    
    String getCipherPairDecrypt(char a,char b){
        int r1=0,c1=0,r2=0,c2=0;
        int newr1,newr2,newc1,newc2;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(matrix[i][j]==a){
                    r1 = i;
                    c1 = j;
                }
                if(matrix[i][j]==b){
                    r2 = i;
                    c2 = j;
                }
            }
        }
        if(r1==r2){
            newr1 = r1;
            newr2 = r2;
            newc1 = (c1-1);
            if(newc1<0)
                newc1 = 4;
            newc2 = (c2-1);
            if(newc2 <0)
                newc2 = 4;
        }
        else if(c1==c2){
            newc1 = c1;
            newc2 = c2;
            newr1 = (r1-1);
            if(newr1<0)
                newr1 = 4;
            
            newr2 = (r2-1);
            if(newr2 <0)
                newr2 = 4;
        }
        else{
            newr1 = r1;
            newc1 = c2;
            newr2 = r2;
            newc2 = c1;
        }
        return "" + matrix[newr1][newc1] + matrix[newr2][newc2];
    }
    String encrypt(String key,String input){
        generateMatrix(key);
        
        print("Input : " + input);
        input = _formatInput(input);
        print("Formatted Input : " + input);
        String cipher="";
        for(int i=0;i<input.length();i+=2 )
        {
            cipher+=getCipherPair(input.charAt(i), input.charAt(i+1));
            break;
        }
        return cipher;
    }
    String decrypt(String input){
        print("Cipher to decrypt : " + input);
       
        String plain="";
        for(int i=0;i+1<input.length();i+=2 ){
            plain+=getCipherPairDecrypt(input.charAt(i), input.charAt(i+1));
          
        }
        return plain; 
    }
    
    
}
public class PlayFairHandler {

    public static void main(String[] args) {
        // TODO code application logic here
        PlayFair playFair = new PlayFair();
        String cipher = playFair.encrypt("networksecurity", "hakunamatataa");
        System.out.println("Cipher : " + cipher);
        String plain = playFair.decrypt(cipher);
        System.out.println("Plain : " + plain);
    }
    
}
