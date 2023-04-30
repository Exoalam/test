package org.example;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int Block_Size = 64;
        ArrayList<Integer> Padding = new ArrayList<>(List.of(
                128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        String raw_msg = "The saddest aspect of life right now is that science gathers knowledge faster than society gathers wisdom.";
        ArrayList<Integer> msg = new ArrayList<>();
        for (int i = 0; i < raw_msg.length(); i++) {
            msg.add((int)raw_msg.charAt(i));
        }
        int msg_len = msg.size();
        int index = msg_len % 64;

        if(index < 56){
            msg.addAll(Padding.subList(0, 56-index));
        }
        else{
            msg.addAll(Padding.subList(0, 120-index));
        }
        ArrayList<Integer> ender = new ArrayList<>();
        String end = Integer.toBinaryString(msg_len*8);
        StringBuilder stringBuilder = new StringBuilder(end);
        stringBuilder.reverse();

        for(int i = 0; i < end.length(); i=i+8){
            if(i+8<=end.length()){
                ender.add(Integer.parseInt(String.valueOf(new StringBuilder(stringBuilder.substring(i, i + 8)).reverse()),2));
            }
            else{
                ender.add(Integer.parseInt(String.valueOf(new StringBuilder(stringBuilder.substring(i, end.length())).reverse()),2));
            }
        }
        int ender_len = ender.size();
        for(int i = 0; i < (8-ender_len); i++){
            ender.add(0);
        }

        msg.addAll(ender);
        String A = "0x67452301";
        String B = "0xefcdab89";
        String C = "0x98badcfe";
        String D = "0x10325476";
        long word_A = Long.decode(A);
        long word_B = Long.decode(B);
        long word_C = Long.decode(C);
        long word_D = Long.decode(D);

        ArrayList<Integer> block = new ArrayList<>();
        for(int i  = 0; i < Math.ceil(msg.size()/Block_Size); i++){
            block = decode_block(msg.subList(i*Block_Size,(i+1)*Block_Size));
            long AA = word_A;
            long BB = word_B;
            long CC = word_C;
            long DD = word_D;
            word_A = fun_rot_left((word_A + fun_F(word_B, word_C, word_D) + block.get(i)) % Long.decode("0x100000000") , 3);
            System.out.println(word_A);
            word_D = fun_rot_left((word_D + fun_F(word_A, word_B, word_C) + block.get(1)) % Long.decode("0x100000000") , 7);
            word_C = fun_rot_left((word_C + fun_F(word_D, word_A, word_B) + block.get(2)) % Long.decode("0x100000000") , 11);
            word_B = fun_rot_left((word_B + fun_F(word_C, word_D, word_A) + block.get(3)) % Long.decode("0x100000000") , 19);

            word_A = fun_rot_left((word_A + fun_F(word_B, word_C, word_D) + block.get(4)) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_F(word_A, word_B, word_C) + block.get(5)) % Long.decode("0x100000000"), 7);
            word_C = fun_rot_left((word_C + fun_F(word_D, word_A, word_B) + block.get(6)) % Long.decode("0x100000000"), 11);
            word_B = fun_rot_left((word_B + fun_F(word_C, word_D, word_A) + block.get(7)) % Long.decode("0x100000000"), 19);

            word_A = fun_rot_left((word_A + fun_F(word_B, word_C, word_D) + block.get(8)) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_F(word_A, word_B, word_C) + block.get(9)) % Long.decode("0x100000000"), 7);
            word_C = fun_rot_left((word_C + fun_F(word_D, word_A, word_B) + block.get(10)) % Long.decode("0x100000000"), 11);
            word_B = fun_rot_left((word_B + fun_F(word_C, word_D, word_A) + block.get(11)) % Long.decode("0x100000000"), 19);

            word_A = fun_rot_left((word_A + fun_F(word_B, word_C, word_D) + block.get(12)) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_F(word_A, word_B, word_C) + block.get(13)) % Long.decode("0x100000000"), 7);
            word_C = fun_rot_left((word_C + fun_F(word_D, word_A, word_B) + block.get(14)) % Long.decode("0x100000000"), 11);
            word_B = fun_rot_left((word_B + fun_F(word_C, word_D, word_A) + block.get(15)) % Long.decode("0x100000000"), 19);
                    
            word_A = fun_rot_left((word_A + fun_G(word_B, word_C, word_D) + block.get(0) + 0x5A827999) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_G(word_A, word_B, word_C) + block.get(4) + 0x5A827999) % Long.decode("0x100000000"), 5);
            word_C = fun_rot_left((word_C + fun_G(word_D, word_A, word_B) + block.get(8) + 0x5A827999) % Long.decode("0x100000000"), 9);
            word_B = fun_rot_left((word_B + fun_G(word_C, word_D, word_A) + block.get(12) + 0x5A827999) % Long.decode("0x100000000"), 13);

            word_A = fun_rot_left((word_A + fun_G(word_B, word_C, word_D) + block.get(1) + 0x5A827999) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_G(word_A, word_B, word_C) + block.get(5) + 0x5A827999) % Long.decode("0x100000000"), 5);
            word_C = fun_rot_left((word_C + fun_G(word_D, word_A, word_B) + block.get(9) + 0x5A827999) % Long.decode("0x100000000"), 9);
            word_B = fun_rot_left((word_B + fun_G(word_C, word_D, word_A) + block.get(13) + 0x5A827999) % Long.decode("0x100000000"), 13);

            word_A = fun_rot_left((word_A + fun_G(word_B, word_C, word_D) + block.get(2) + 0x5A827999) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_G(word_A, word_B, word_C) + block.get(6) + 0x5A827999) % Long.decode("0x100000000"), 5);
            word_C = fun_rot_left((word_C + fun_G(word_D, word_A, word_B) + block.get(10) + 0x5A827999) % Long.decode("0x100000000"), 9);
            word_B = fun_rot_left((word_B + fun_G(word_C, word_D, word_A) + block.get(14) + 0x5A827999) % Long.decode("0x100000000"), 13);

            word_A = fun_rot_left((word_A + fun_G(word_B, word_C, word_D) + block.get(3) + 0x5A827999) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_G(word_A, word_B, word_C) + block.get(7) + 0x5A827999) % Long.decode("0x100000000"), 5);
            word_C = fun_rot_left((word_C + fun_G(word_D, word_A, word_B) + block.get(11) + 0x5A827999) % Long.decode("0x100000000"), 9);
            word_B = fun_rot_left((word_B + fun_G(word_C, word_D, word_A) + block.get(15) + 0x5A827999) % Long.decode("0x100000000"), 13);
                    
            word_A = fun_rot_left((word_A + fun_H(word_B, word_C, word_D) + block.get(0) + 0x6ED9EBA1) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_H(word_A, word_B, word_C) + block.get(8) + 0x6ED9EBA1) % Long.decode("0x100000000"), 9);
            word_C = fun_rot_left((word_C + fun_H(word_D, word_A, word_B) + block.get(4) + 0x6ED9EBA1) % Long.decode("0x100000000"), 11);
            word_B = fun_rot_left((word_B + fun_H(word_C, word_D, word_A) + block.get(12) + 0x6ED9EBA1) % Long.decode("0x100000000"), 15);

            word_A = fun_rot_left((word_A + fun_H(word_B, word_C, word_D) + block.get(2) + 0x6ED9EBA1) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_H(word_A, word_B, word_C) + block.get(10) + 0x6ED9EBA1) % Long.decode("0x100000000"), 9);
            word_C = fun_rot_left((word_C + fun_H(word_D, word_A, word_B) + block.get(6) + 0x6ED9EBA1) % Long.decode("0x100000000"), 11);
            word_B = fun_rot_left((word_B + fun_H(word_C, word_D, word_A) + block.get(14) + 0x6ED9EBA1) % Long.decode("0x100000000"), 15);

            word_A = fun_rot_left((word_A + fun_H(word_B, word_C, word_D) + block.get(1) + 0x6ED9EBA1) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_H(word_A, word_B, word_C) + block.get(9) + 0x6ED9EBA1) % Long.decode("0x100000000"), 9);
            word_C = fun_rot_left((word_C + fun_H(word_D, word_A, word_B) + block.get(5) + 0x6ED9EBA1) % Long.decode("0x100000000"), 11);
            word_B = fun_rot_left((word_B + fun_H(word_C, word_D, word_A) + block.get(13) + 0x6ED9EBA1) % Long.decode("0x100000000"), 15);

            word_A = fun_rot_left((word_A + fun_H(word_B, word_C, word_D) + block.get(3) + 0x6ED9EBA1) % Long.decode("0x100000000"), 3);
            word_D = fun_rot_left((word_D + fun_H(word_A, word_B, word_C) + block.get(11) + 0x6ED9EBA1) % Long.decode("0x100000000"), 9);
            word_C = fun_rot_left((word_C + fun_H(word_D, word_A, word_B) + block.get(7) + 0x6ED9EBA1) % Long.decode("0x100000000"), 11);
            word_B = fun_rot_left((word_B + fun_H(word_C, word_D, word_A) + block.get(15) + 0x6ED9EBA1) % Long.decode("0x100000000"), 15);

            word_A = (word_A + AA) % Long.decode("0x100000000");
            word_B = (word_B + BB) % Long.decode("0x100000000");
            word_C = (word_C + CC) % Long.decode("0x100000000");
            word_D = (word_D + DD) % Long.decode("0x100000000");

        }

    }
    public static long fun_F(long x, long y, long z){
        return (((x) & (y)) | ((~x) & (z)));
    }

    public static long fun_G(long x, long y, long z){
        return (((x) & (y)) | ((x) & (z)) | ((y) & (z)));
    }

    public static long fun_H(long x, long y, long z){
        return ((x) ^ (y) ^ (z));
    }

    public static long fun_rot_left(long x, long n){
        return (((x) << (n)) | ((x) >> (32-(n))));
    }

    public static ArrayList<Integer> decode_block(List<Integer> raw){
        ArrayList<Integer> processed_block = new ArrayList<>();
        for(int i = 0; i < raw.size(); i+=4){
            processed_block.add((raw.get(i)) | ((raw.get(i+1)) << 8) | ((raw.get(i+2)) << 16) | ((raw.get(i+3)) << 24));
        }
        return processed_block;
    }


}