import java.io.*;
import java.util.*;
class Vignere{
    char [][] vignereTable = new char[26][26];
    void print(String str){
        System.out.println(str);
    }
    void populateTable(){
        char c = 'a';
        for(int i=0;i<26;i++){
            for(int j=0;j<26;j++)
            {
                vignereTable[i][j] = c;
                System.out.print(c + ",");
                if(c=='z')
                    c='a';
                else
                    c++;
            }
            c++;
            print("");
        }
    }
    int getIndex(char c){
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        for(int i=0;i<26;i++){
            if(alpha.charAt(i)==c)
                return i;
        }
        return 0;
    }
    int getColumn(int r,char inputChar){
        for(int i=0;i<26;i++){
            if(vignereTable[r][i]==inputChar)
                return i;
        }
        return 0;
    }
    String encrypt(String key,String input){
        populateTable();
        while(key.length()<input.length())
        {
            key+=key;
        }

        int r=0,c=0;
        String cipher = "";
        for(int i=0;i<input.length();i++)
        {
            char keyChar = key.charAt(i);
            char inputChar = input.charAt(i);
            r = getIndex(keyChar);
            c = getIndex(inputChar);
            cipher+= vignereTable[r][c];
            
        }
        print("Cipher : " + cipher);
        return cipher;   
    }
    
    String decrypt(String key,String cipher){
        int r=0,c=0;
        while(key.length()<cipher.length()){
            key+=key;
        }
        String plain = "";
        for(int i=0;i<cipher.length();i++){
            char keyChar = key.charAt(i);
            char inputChar = cipher.charAt(i);
            r = getIndex(keyChar);
            c = getColumn(r,inputChar);
            
            plain+= vignereTable[0][c];
            
        }
        print("Plain : " + plain);
        return plain;   
    }
}


public class VignereHandler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Vignere vignere = new Vignere();
        String key = "deceptive";
        String input = "wearediscoveredsaveyourself";
        String cipher = vignere.encrypt(key,input);
        String plain = vignere.decrypt(key, cipher);
    }
    
}
