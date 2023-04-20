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

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        //2 arguments
        //1 for file 1 for starting server port 
        String fileName = args[0];
        String port = args[1];

        File fortuneCookie = new File(fileName);
        if (!fortuneCookie.exists()) {
            System.out.println("Cookies not found. Please go exercise instead.");
            System.exit(0);
        } else {
            System.out.println("Fortune Cookie found. Preparing fortunes for dispensing.");
        }

        Cookie cooks = new Cookie();
        cooks.cookieRead(fileName); // use the fileName for this function
        String fortune = cooks.getRandomCookie();
        System.out.println(fortune);

        ServerSocket server = new ServerSocket(Integer.parseInt(port));
        Socket socket = server.accept();

        //allows server to read and write over comms channel
        try (InputStream is = socket.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream (is);
            DataInputStream disNuts = new DataInputStream (bis);

            //stores data sent over from client like commands and stuff
            //this particular command can be outside the server code nest also
            String clientMSG = "";

            try(OutputStream os = socket.getOutputStream()) {
                BufferedOutputStream bos = new BufferedOutputStream (os);
                DataOutputStream dos = new DataOutputStream (bos);
                

                //code here will deal with sending and receiving
                while (!clientMSG.equals("close")) {
                    //receiving message
                    clientMSG = disNuts.readUTF();
                    if (clientMSG.equals("get-cookie")) {
                        //instantiate cookie.java 
                        //deliver the inspo
                        //send the cookie to client side with data output
                    }   dos.writeUTF("What you want");
                        dos.flush();
                }

                //closes all output streams in reverse order
                dos.close();
                bos.close();
                os.close();

            } catch (EOFException ex) {
                ex.printStackTrace();
            }
            //closes all input streams in reverse order
            disNuts.close();
            bis.close();
            is.close();

        } catch (EOFException ex) {
            socket.close();
        }


    }  
}
