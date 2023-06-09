import java.io.*;
import java.math.BigInteger;
import java.util.*;
public class RSA {


    public static void main(String[] args) throws IOException, InterruptedException {
        long n;
        int e;
        BigInteger cE;
        BigInteger left = new BigInteger("78");
        BigInteger right = new BigInteger("7878");
        int blockSize = 1;
        String thisString = "";
        char[] thisCharArray;
        char[] alphabet = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
                , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
                , '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '?', '!', ',', ';', ':', '_', '(', ')', '[', ']', '{', '}', '\'', '"', ' ', '\n'};
        File file = new File("text.txt");
        if (!file.exists() && !file.isDirectory()){
            file.createNewFile();
            System.out.println("\nThe file does not exist! We will create a text file called text.txt.\n");
            System.out.println("Please enter e then n, then enter your text in the next line.\n");
            System.out.println("Creating a text file, and terminating the program...\n");
            System.exit(0);
        }
        FileInputStream fis = new FileInputStream("text.txt");

        FileOutputStream fos = new FileOutputStream("text.rsa");
        PrintWriter pw = new PrintWriter(fos);
        Scanner input = new Scanner(fis);
        if (!input.hasNext()){
            System.out.println("\nThe file is empty, Please make sure you enter e then n, then enter your text in the next line.");
            System.out.println("Terminating the program...");
            System.exit(0);
        }

        e = input.nextInt();
        n = input.nextLong();
        input.nextLine();
        int choice = 0, countE = 0, countD = 0;
        Scanner kb = new Scanner(System.in);
        do {
            kb:
            while (choice != 3) {
                System.out.println("\n\nEnter your choice: \n1-Encryption. \n2-Decryption. \n3-Exit.");
                choice = kb.nextInt();
                if (choice == 1 && countE < 1) {
                    System.out.println("Your .rsa file is being generated, please wait...");
                    countE++;
                    break kb;
                } else if (choice == 2 && countD < 1) {
                    decryption();
                    countD++;
                    System.exit(0);
                } else {
                    System.out.println("\nTerminating the program...");
                    System.exit(0);
                }
            }

            int nL = 10;
            char newLine = (char) nL;
            while (input.hasNext()) {
                thisString += input.nextLine() + newLine ;
            }

            while(true) {
                if (n > left.longValue() && n < right.longValue()) {
                    break;
                } else {
                    left =  new BigInteger(left.toString() +"78");
                    right = new BigInteger(right.toString() + "78");
                    blockSize = blockSize + 1;
                }
            }
            thisCharArray = thisString.toCharArray();
            thisString = "";
            for (int i = 0; i < thisCharArray.length; i += blockSize) {
                for (int j = i; j < i + blockSize; j++) {
                    if (j >= thisCharArray.length) {
                        thisString += "X";
                    } else
                        thisString += thisCharArray[j];
                }
                int f;
                String m = "";
                for (f = 0; f <= blockSize - 1; f++) {
                    for (int k = 0; k < alphabet.length; k++) {
                        if (thisString.charAt(f) == alphabet[k]) {
                            if (k < 10) {
                                m += "0" + k;
                            } else
                                m += k ;
                            break;
                        }
                    }
                }
                BigInteger blocks = new BigInteger(m);
                cE = expMod(blocks, e, n);
                String encrypted = cE.toString();
                while (encrypted.length() < blockSize * 2 ) {
                    encrypted = "0" + encrypted;
                }
                pw.print(encrypted);
                thisString = "";
            }
            pw.close();
            input.close();
            System.out.println("\n\nDone!");
            Thread.sleep(1000);
            System.out.println("\nTaking you back to main menu...");
        }while (choice != 3);
    }

    private static void decryption() throws IOException, InterruptedException {
        BigInteger cD;
        FileInputStream fis = new FileInputStream("text.rsa");
        Scanner input = new Scanner(fis);
        FileOutputStream fos = new FileOutputStream("text.dec");
        PrintWriter pw = new PrintWriter(fos);
        char[] alphabet = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
                , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
                , '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '?', '!', ',', ';', ':', '_', '(', ')', '[', ']', '{', '}', '\'', '"', ' ', '\n'};

        Scanner kb =new Scanner(System.in);
        System.out.print("Enter d:");
        long d = kb.nextLong();
        System.out.print("Enter n:");
        long n = kb.nextLong();

        System.out.println("\nYour .dec file is being generated, please wait...\n\n");
        int blockSize = 2;

        BigInteger left = new BigInteger("78");
        BigInteger right = new BigInteger("7878");
        while(true) {
            if (n > left.longValue() && n < right.longValue()) {
                break;
            } else {
                left =  new BigInteger(left.toString() +"78");
                right = new BigInteger(right.toString() + "78");
                blockSize = blockSize + 2;
            }
        }

        String thisString = "";
        while (input.hasNext()) {
            thisString += input.nextLine();
        }

        int boxes = (int) Math.ceil((double) thisString.length() /(double) blockSize);
        String[] array = new String[boxes];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < blockSize; j++) {
                int k = i * blockSize;
                if (array[i] == null) {
                    array[i] = String.valueOf(thisString.charAt(k));
                } else {
                    array[i] += thisString.charAt(k + j);
                }
            }
        }

        BigInteger blocks;
        for (int i = 0; i < array.length; i++) {
            blocks = new BigInteger(array[i]);
            cD = expMod(blocks,d, n);
            String decRes = cD.toString();
            while (decRes.length() < blockSize) {
                decRes = "0" + decRes;
            }
            array[i] = decRes;
        }
        thisString = "";
        for (int i = 0;array.length>i;i++){
            thisString += array[i];
        }
        String decrypted = "";
        for(int i = 0;thisString.length()>i;i=i+2){
            String substring = thisString.substring(i, i+2);
            decrypted = decrypted + alphabet[Integer.parseInt(substring)];
        }


        pw.write(decrypted);
        pw.close();
        input.close();
        fis.close();
        fos.close();
        kb.close();

        System.out.println("Done! Check your file.");
        Thread.sleep(500);
        System.out.println("Terminating the program...");
    }

    private static BigInteger expMod(BigInteger num, long e, long n) {
        if (num.equals(0))
            return BigInteger.valueOf(0);
        if (e == 0)
            return BigInteger.valueOf(1);

        BigInteger x;
        if (e % 2 == 0) {
            x = expMod(num, e / 2, n);
            x = (x.multiply(x)).mod(BigInteger.valueOf(n));
        } else {
            x = num.mod(BigInteger.valueOf(n));
            x = (x.multiply(expMod(num, e - 1, n)).mod(BigInteger.valueOf(n))).mod(BigInteger.valueOf(n));
        }

        return ((x.add(BigInteger.valueOf(n))).mod(BigInteger.valueOf(n)));
    }
}