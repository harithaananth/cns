
public class RailFence {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RailFence rf = new RailFence();
        String cipher = rf.encrypt(5, "iloveyoubuttcheeks");
        
        rf.decrypt(5,"ibkluuesootevythec");
    }
    
    String encrypt(int n,String msg){
        
        char mat[][] = new char[n][msg.length()];
        for(int i=0;i<n;i++){

            for(int j=0;j<msg.length();j++){
                mat[i][j] = '0';
            }
        }
     
        int k=0;
        int i=0;
        boolean down = true;
        while(k<msg.length()){
            mat[i][k] = msg.charAt(k);
            k++;
            if(down){
                i++;
                if(i==n){
                    down=false;
                    i-=2;
                        }
            }
            else{
                i--;
                if(i==-1){
                    i+=2;
                    down=true;
                }
            }
        }
        for(int i1=0;i1<n;i1++)
        {
            for(int j=0;j<msg.length();j++){
                System.out.print(mat[i1][j] + " ");
            }
            System.out.println("");
        }
        String cipher="";
        for(int i1=0;i1<n;i1++){
            for(int j=0;j<msg.length();j++){
                if(mat[i1][j]!='0')
                    cipher+=mat[i1][j];
            }
            
        }
        System.out.println("Cipher : " + cipher);
        return cipher;
        
        
    }
    void decrypt(int n,String cipher){
        int m = cipher.length();
        char mat[][] = new char[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                mat[i][j] = '0';
            }
        }
        
        int k=0;
        int i=0;
        boolean down = true;
        while(k<m)
        {
            mat[i][k] = '1';
            k++;
            if(down){
                i++;
                if(i==n){
                    down=false;
                    i-=2;
                }
            }
            else{
                i--;
                if(i==-1){
                    i+=2;
                    down=true;
                }
            }
        }
        for(int i1=0;i1<n;i1++){
            for(int j=0;j<m;j++){
                System.out.print(mat[i1][j] + " ");
            }
            System.out.println("");
        }
        int index=0;
        for(int i1=0;i1<n;i1++){
            for(int j=0;j<m;j++){
                if(mat[i1][j]=='1')
                {
                    mat[i1][j] = cipher.charAt(index);
                    index++;
                }
            }
//            System.out.println("");
        }
        
        for(int i1=0;i1<n;i1++){
            for(int j=0;j<m;j++){
                System.out.print(mat[i1][j] + " ");
            }
            System.out.println("");
        }
        
        
        String plain="";
        
        k=0;
        i=0;
        down = true;
        while(k<m){
            plain+=mat[i][k];
            k++;
            if(down){
                i++;
                if(i==n){
                    down=false;
                    i-=2;
                }
            }
            else{
                i--;
                if(i==-1){
                    i+=2;
                    down=true;
                }
            }
        }
        System.out.println("Plain : " + plain);
    }
    
}
