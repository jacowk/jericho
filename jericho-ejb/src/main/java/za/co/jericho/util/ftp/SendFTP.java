package za.co.jericho.util.ftp;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.LogManager;

/**
 * This class serves only as POC.
 * 
 * @author Jaco Koekemoer
 * Date: 2015-08-05
 * 
 */
public class SendFTP {
    
    public void ftpFile(String filePath, String filename) {
        LogManager.getRootLogger().info(new StringBuilder()
                .append("SendFTP ftp'ing XML file.")
                .append(filename)
                .toString());
        String serverAddress = "127.0.0.1";
        String userId = "camelpoc";
        String password = "camelpoc";
        String remoteDirectory = ".";
        String localDirectory = ".";
        
        //new ftp client
        FTPClient ftp = new FTPClient();
        try {
            //try to connect
            ftp.connect(serverAddress);
            //login to server
            if(!ftp.login(userId, password))
            {
                ftp.logout();
            }
            int reply = ftp.getReplyCode();
            //FTPReply stores a set of constants for FTP reply codes. 
            if (!FTPReply.isPositiveCompletion(reply))
            {
                ftp.disconnect();
            }
            
            //enter passive mode
            ftp.enterLocalPassiveMode();
            //get system name
            System.out.println("Remote system is " + ftp.getSystemType());
            
            FileInputStream fileInputStream = null;
            
            fileInputStream = new FileInputStream(filePath + filename);
            
            ftp.storeFile(filename, fileInputStream);
        }
        catch (IOException ex) {
            LogManager.getRootLogger().info(new StringBuilder()
                .append(ex)
                .toString());
        }
        
    }
    
}
