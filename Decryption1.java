import java.io.*;
import java.math.*;
import java.util.*;

public class Decryption1 {
    public static void main(String[] args) throws InterruptedException, IOException {
        BigInteger cD;
        FileInputStream fis = new FileInputStream("p1.rsa");
        Scanner input = new Scanner(fis);
        FileOutputStream fos = new FileOutputStream("text.dec");
        PrintWriter pw = new PrintWriter(fos);
        char[] alphabet = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
                , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
                , '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '?', '!', ',', ';', ':', '_', '(', ')', '[', ']', '{', '}', '\'', '"', ' ', '\n'};


        long n = 797527;
        long d;
        System.out.println("\n\nYour .dec file is being generated, please wait...");

        int blockSize = 6;

        System.out.println(blockSize);
        String thisString = "";
        while (input.hasNext()) {
            thisString += input.nextLine();
        }
    for (int count = 2; count < n; count++){
        d = count;
        try {


            int boxes = (int) Math.ceil((double) thisString.length() / (double) blockSize);
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
                cD = expMod(blocks, d, n);
                String decRes = cD.toString();
                while (decRes.length() < blockSize) {
                    decRes = "0" + decRes;
                }
                array[i] = decRes;
            }
            String atLine = "";
            for (int i = 0; array.length > i; i++) {
                atLine += array[i];
            }
            String decrypted = "";
            for (int i = 0; atLine.length() > i; i = i + 2) {
                String substring = atLine.substring(i, i + 2);
                decrypted += alphabet[Integer.parseInt(substring)];
            }


            pw.write(decrypted);
            pw.print("\n\n\n\n\n" + d +" \n\n\n\n");
        }catch (ArrayIndexOutOfBoundsException e){
        }
    }
        pw.close();
        input.close();
        fis.close();
        fos.close();


        System.out.println("Done! Check your file.");
        Thread.sleep(500);
        System.out.println("Terminating the program...");
    }
    private static BigInteger expMod(BigInteger num, long e, long n) {
        if (num.equals(0))
            return BigInteger.valueOf(0);
        if (e == 0)
            return BigInteger.valueOf(1);

        BigInteger temp;
        if (e % 2 == 0) {
            temp = expMod(num, e / 2, n);
            temp = (temp.multiply(temp)).mod(BigInteger.valueOf(n));
        } else {
            temp = num.mod(BigInteger.valueOf(n));
            temp = (temp.multiply(expMod(num, e - 1, n)).mod(BigInteger.valueOf(n))).mod(BigInteger.valueOf(n));
        }

        return ((temp.add(BigInteger.valueOf(n))).mod(BigInteger.valueOf(n)));
    }
}
