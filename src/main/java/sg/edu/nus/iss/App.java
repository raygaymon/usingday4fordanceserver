package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException {
        // 2 arguments
        // 1 for file 1 for starting server port
        String fileName = args[0];
        String port = args[1];

        File fortuneCookie = new File(fileName);
        if (!fortuneCookie.exists()) {
            System.out.println("Syllabus not found. Please go pay for a course first.");
            System.exit(0);
        } else {
            System.out.println("Training syllabus found. Time to stop being shit.");
        }

        //creating executor service to access thread
        ExecutorService thrpool = Executors.newFixedThreadPool(2);
        
        ServerSocket server = new ServerSocket(Integer.parseInt(port));

        while (true) {

            System.out.println("Waiting for connection");
            Socket socket = server.accept();
            //create new handler object
            ClientHandler handler = new ClientHandler(socket, fileName);
            System.out.println("Sending client to thread");
            //allocating resources to the thread program to handle
            thrpool.submit(handler);

        }
        

        // allows server to read and write over comms channel
        // "try (InputStream is = socket.getInputStream()) {
        //     BufferedInputStream bis = new BufferedInputStream(is);
        //     DataInputStream disNuts = new DataInputStream(bis);

        //     // stores data sent over from client like commands and stuff
        //     // this particular command can be outside the server code nest also
        //     String clientMSG = "";

        //     try (OutputStream os = socket.getOutputStream()) {
        //         BufferedOutputStream bos = new BufferedOutputStream(os);
        //         DataOutputStream dos = new DataOutputStream(bos);

        //         // code here will deal with sending and receiving
        //         while (!clientMSG.equals("close")) {
        //             // receiving message
        //             clientMSG = disNuts.readUTF();

        //             if (clientMSG.equals("2")) {
        //                 // instantiate cookie.java
        //                 Cookie cooks = new Cookie();
        //                 cooks.cookieRead(fileName); // use the fileName for this function

        //                 // deliver the inspo
        //                 String fortune = cooks.getRandomCookie();
        //                 //System.out.println(fortune);

        //                 // send the cookie to client side with data output
        //                 System.out.println("Telling them what to train today...");
        //                 dos.writeUTF(fortune);
        //                 dos.flush();

        //             } else if (clientMSG.equals("0")){
        //                 dos.writeUTF("Please pay $2 or kindly f- off this is a business");
        //                 dos.flush();
        //             } else {
        //                 dos.writeUTF("WTF are you trying to do");
        //                 dos.flush();
        //             }
        //         }

        //         // closes all output streams in reverse order
        //         dos.close();
        //         bos.close();
        //         os.close();

        //     } catch (EOFException ex) {
        //         ex.printStackTrace();
        //     }
        //     // closes all input streams in reverse order
        //     disNuts.close();
        //     bis.close();
        //     is.close();

        // } catch (EOFException ex) {
        //     socket.close();
        // }"

    }
}
