package org.myamith.StreamIntoSockets;

import java.io.IOException;
import java.util.Random;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;
import java.sql.Timestamp;

public class DataServerPriceTumbling {
    public static void main(String[] args) throws IOException{
        ServerSocket listener1 = new ServerSocket(9999);
        ServerSocket listener2 = new ServerSocket(9898);
        try{
            Socket socket1 = listener1.accept();
            Socket socket2 = listener2.accept();
            System.out.println("Got new connection s1: " + socket1.toString());
            System.out.println("Got new connection s2: " + socket2.toString());
            try {
                PrintWriter out1 = new PrintWriter(socket1.getOutputStream(), true);
                PrintWriter out2 = new PrintWriter(socket2.getOutputStream(), true);
                Random rand = new Random();
                Date d = new Date();
                int val = 3;
                int posVal = 1;
                int posId = 1;
                while (true){
                    if(val == 201)
                    {
                        val = 1;
                    }
                    if(posVal == 201){
                        posVal = 1;
                    }
                    if(posId == 10001){
                        posId = 1;
                    }

                    int price = rand.nextInt(50);
                    String s1 = "" + System.currentTimeMillis() + "," + val + "," + price;
                    String s2 = "" + System.currentTimeMillis() + "," + posId + "," + posVal + "," + price;
                    System.out.println("S1 : " +s1);
                    System.out.println("S2 : " +s2);
                    /* <timestamp>,<random-number> */
                    out1.println(s1);
                    out2.println(s2);
                    Thread.sleep(1000);
                    val += 1;
                    posVal += 1;
                    posId += 1;
                }

            } finally{
                socket1.close();
                socket2.close();
            }

        } catch(Exception e ){
            e.printStackTrace();
        } finally{

            listener1.close();
            listener2.close();
        }
    }
}
