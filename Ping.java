import java.net.*;
import java.io.*;
import java.util.Vector;


public class Ping implements Runnable {
    private Socket socket;
    private String address;

    public Ping (String address){
        this.socket = null;
        this.address = address;
    }

    @Override
    public void run() {
        try {
            long startTime = System.currentTimeMillis();
            socket =  new Socket(address,80);
            InetAddress ipaddress = socket.getInetAddress();
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.printf("Reply from %s: time=%dms\n",ipaddress.getHostAddress(),elapsedTime);
            socket.close();
        } catch (Exception e) {
            System.out.println("Ping request couldn't find the host!");
        }
    }


    public static void main(String[] args){
        if(args.length == 1){
            System.out.printf("Pinging %s\n",args[0]);
            Vector<Thread> threads = new Vector<>();
            for(int i=0;i<4;i++){
                threads.add(new Thread(new Ping(args[0])));
            }

            for (int i=0;i<4;i++){
                threads.get(i).start();
            }


            for (int i=0;i<4;i++){
                try {
                    threads.get(i).join();
                }catch (Exception e){

                }
            }
        }
    }
}
