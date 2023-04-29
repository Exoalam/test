package org.example;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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

    }
    public int fun_F(int x, int y, int z){
        return (((x) & (y)) | ((~x) & (z)));
    }

    public int fun_G(int x, int y, int z){
        return (((x) & (y)) | ((x) & (z)) | ((y) & (z)));
    }

    public int fun_H(int x, int y, int z){
        return ((x) ^ (y) ^ (z));
    }

    public int fun_rot_left(int x, int n){
        return (((x) << (n)) | ((x) >> (32-(n))));
    }

    public ArrayList<Integer> decode_block(ArrayList<Integer> raw){
        ArrayList<Integer> processed_block = new ArrayList<>();
        for(int i = 0; i < raw.size(); i+=4){
            processed_block.add((raw.get(i)) | ((raw.get(i+1)) << 8) | ((raw.get(i+2)) << 16) | ((raw.get(i+3)) << 24));
        }
        return processed_block;
    }

}