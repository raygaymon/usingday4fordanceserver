package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    // clienthanndler requires socket from client/server as constructor
    private final Socket sock;
    private String file;

    public ClientHandler(Socket s, String f) {
        sock = s;
        file = f;

    }

    @Override
    public void run() {

        // use objectIOstream bcs each connection is an object
        try {
            System.out.println("Client connected");
            InputStream is = sock.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            // stores data sent over from client like commands and stuff
            // this particular command can be outside the server code nest also
            String clientMSG = "";

            OutputStream os = sock.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            boolean exit = false;

            Cookie cookie = new Cookie();
            cookie.cookieRead(file);

            while (!exit) {
                
                clientMSG = dis.readUTF();

                // dos.writeUTF("Connection successful, commencing training");
                // dos.flush();

                if (clientMSG.equals("close".trim().toLowerCase())) {

                    System.out.println("Closing client connection");
                    //close the inputstream when client disconnects
                    dis.close();
                    bis.close();
                    is.close();
                    exit = true;
                    continue;
                }

                if (clientMSG.equals("2")) {

                    System.out.println("Telling them what to train today...");
                    System.out.println(clientMSG);
                    String training = cookie.getRandomCookie();
                    dos.writeUTF("Today you train: " + training);
                    dos.flush();
                }
                
                else if (clientMSG.equals("no")) {
                    System.out.println(clientMSG);
                    dos.writeUTF("Please pay $2 or kindly f- off this is a business");
                    dos.flush();
                    
                }

                else {
                System.out.println(clientMSG);
                dos.writeUTF("What you trying to do");
                dos.flush();
                }
        
            }

            dos.close();
            bos.close();
            os.close();
            dis.close();
            bis.close();
            is.close();
                //remember to keep the closes outside the while loop
        } catch (IOException e) {
            e.printStackTrace();
        } 
        //finally {
        //     try {
        //         System.out.println("Closing connection");
        //         sock.close();
        //     } catch (Exception e) {

        //     }
        // }
    }
}
// closes all input streams in reverse order
