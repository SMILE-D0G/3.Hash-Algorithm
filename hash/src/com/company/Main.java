//  SHA-1
package com.company;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String originalMessage = scanner.nextLine();
        byte[] message = originalMessage.getBytes();
        int m1 = message.length;
        long h0 = 0x67452301;
        long h1 = 0xEFCDAB89;
        long h2 = 0x98BADCFE;
        long h3 = 0x10325476;
        long h4 = 0xC3D2E1F0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= m1; i = i + 512) {   //разбитие на чанки
            sb.append(Arrays.toString(Arrays.copyOfRange(message, i, i + 512)));
        }
        String chunks = sb.toString();
        byte[] chunkMessage = chunks.getBytes();
        int part = chunkMessage.length / 16;
        long[] w = new long[80];
        String str;
        for (int i = 0; i < 16; i++) {
            str = Arrays.toString(Arrays.copyOfRange(chunkMessage, i * part, (i + 1) * part - 1));
            str = str.replaceAll("\\p{P}", "");
            str = str.replaceAll(" ", "");
            BigInteger bi = new BigInteger(str, 16);
            w[i] = bi.longValue();
        }
        for (int i = 16; i <= 79; i++) {
            w[i] = (w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16]) << 5;
        }
        long a = h0;
        long b = h1;
        long c = h2;
        long d = h3;
        long e = h4;
        long f = 0;
        long k = 0;
        for (int i = 0; i <= 79; i++) {
            if (i <= 19) {
                f = (b & c) | ((~b) & d);
                k = 0x5A827999;
            } else if ((i >= 20) && (i <= 39)) {
                f = b ^ c ^ d;
                k = 0x6ED9EBA1;
            } else if ((i >= 40) && (i <= 59)) {
                f = (b & c) | (b & d) | (c & d);
                k = 0x8F1BBCDC;
            } else if (i >= 60) {
                f = b ^ c ^ d;
                k = 0xCA62C1D6;
            }
            long temp = (a << 5) + f + e + k + w[i];
            e = d;
            d = c;
            c = b << 30;
            b = a;
            a = temp;
        }
        h0 += a;
        h1 += b;
        h2 += c;
        h3 += d;
        h4 += e;
        System.out.printf("%x%n", (h0 << 128) | (h1 << 96) | (h2 << 64) | (h3 << 32) | h4);// concat
    }
}
