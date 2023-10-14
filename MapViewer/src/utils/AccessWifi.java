/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 *
 * @author Zepeda22
 */
public class AccessWifi {
    public static boolean isInternetAvailable() throws IOException
    {
        return isHostAvailable("google.com"); 
        //|| isHostAvailable("amazon.com")
        //|| isHostAvailable("facebook.com")|| isHostAvailable("apple.com");
    }
    
    private static boolean isHostAvailable(String hostName) throws IOException
    {
        
        try(Socket socket = new Socket())
        {
            int port = 80;
            InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(hostName), port);
            socket.connect(socketAddress, port);
            return true;
        }
        catch(UnknownHostException | SocketTimeoutException | ConnectException  unknownHost)
        {
            return false;
        }
        
    }
}
