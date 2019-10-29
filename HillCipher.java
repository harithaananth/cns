import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;
class HC{
    String key="";
    int n;
    int m;
    int keyMatrix[][];      //n*n
    int plainMatrix[][];    //n*m
    int cipherMatrix[][];   //n*m
    int adjMatrix[][];      //n*n
    int keyInverseMatrix[][]; //n*n
    int decodedMatrix[][];
    void printMat(String str,int arr[][],int n,int m){
        print(str);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(arr[i][j] + " ");
            }
            print("");
        }
    }
    void printMatChar(String str,int arr[][],int n,int m){
        print(str);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                System.out.print(toChar(arr[i][j]) + " ");
            }
            print("");
        }
    }
    void setKey(String keyStr){
        key = keyStr;
        n =(int) Math.sqrt(keyStr.length());
        keyMatrix = new int[n][n];
        int k=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                int val = toVal(keyStr.charAt(k));
                k++;
                keyMatrix[i][j] = val;
            }
        }
        printMat("Key Matrix :",keyMatrix,n,n);
    }
    void print(String str){
        System.out.println(str);
    }
    char toChar(int val){
        val = val%26;
        if(val<0)
            val+=26;
        char c = (char)(val+97);
        return c;
    }
    int toVal(char c){
        int ascii = (int)c;
        return ascii-97;
    }
    void setPlain(String msg){
        m = msg.length()/n;
        plainMatrix = new int[n][m];
        int k=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                int val = toVal(msg.charAt(k));
                k++;
                plainMatrix[i][j] = val;
            }
        }
        printMat("Plain Matrix :",plainMatrix,n,m);
        
    }

    void matMul(){
        cipherMatrix = new int[n][m];
//        print("" + n +m);
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                cipherMatrix[i][j] = 0;
                for(int k=0;k<n;k++){
                    cipherMatrix[i][j]+= keyMatrix[i][k] * plainMatrix[k][j];
                }
            }
        }
        printMatChar("Cipher Matrix : ", cipherMatrix, n, m);
    }
    String encode(String msg){
        setPlain(msg);
        matMul();
        String cipher="";
        for(int j=0;j<m;j++){
            for(int i=0;i<n;i++){
                cipher+= toChar(cipherMatrix[i][j]);
            }
        }
        print("Cipher : " + cipher);
        return cipher;
        
    }
    void findAdj(){
        adjMatrix = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                //(11*22) - (12*21)
                adjMatrix[i][j] = (keyMatrix[(i+1)%n][(j+1)%n]*keyMatrix[(i+2)%n][(j+2)%n]) - (keyMatrix[(i+1)%n][(j+2)%n]*keyMatrix[(i+2)%n][(j+1)%n]); 
                adjMatrix[i][j]%=26;
                if(adjMatrix[i][j]<0)
                    adjMatrix[i][j]+=26;
            }
        }
        printMat("Adjoint of key : ",adjMatrix,n,n);
    }
    void findInverse(){
        int determinant = 0;
        for(int j=0;j<n;j++){
                determinant += keyMatrix[0][j]*adjMatrix[0][j];
        }
        determinant = determinant%26;
        if(determinant<0)
            determinant+=26;
        print("Determinant : "+ determinant);
        int detInverse=1;
        for(int i=1;i<26;i++){
            if((determinant*i)%26 == 1){
                detInverse = i;
                break;
            }
        }
        print("Determinant Inverse : "+ detInverse);
        int keyInverseMatrix1[][] = new int[n][n];
        keyInverseMatrix = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                keyInverseMatrix1[i][j] = detInverse*adjMatrix[i][j];
                keyInverseMatrix1[i][j]%=26;
                if(keyInverseMatrix1[i][j]<0)
                    keyInverseMatrix1[i][j]+=26;
                keyInverseMatrix[j][i] = keyInverseMatrix1[i][j];
            }
        }
        printMat("Key Inverse Matrix",keyInverseMatrix,n,n);
        printMatChar("Key Inverse Matrix",keyInverseMatrix,n,n);
        
    }
    void matMulDecode(){
        decodedMatrix = new int[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                decodedMatrix[i][j] = 0;
                for(int k=0;k<n;k++){
                    decodedMatrix[i][j]+= keyInverseMatrix[i][k] * cipherMatrix[k][j];
                    decodedMatrix[i][j]%=26;
                    if(decodedMatrix[i][j]<0)
                        decodedMatrix[i][j]+=26;
                }
            }
        }
        printMat("Decoded Plain Matrix", decodedMatrix, n, m);
        printMatChar("Decoded Plain Matrix : ", decodedMatrix, n, m);
    }
    String decode(String cipher){
//        setCipher(cipher);
        findAdj();
        findInverse();
        matMulDecode();
        String decodedString="";
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                decodedString+= toChar(decodedMatrix[i][j]);
            }
        }
        print("Plain : " + decodedString);
        return decodedString;
    }
    
}
public class HillCipher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HC hc = new HC();
        
        hc.setKey("activateactivat");
        String cipher = hc.encode("activateu");
        hc.decode(cipher);
    }
    
    
    
    
    
}
