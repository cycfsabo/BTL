import java.net.*;
import java.io.*;
import java.util.*;


public class Server {
    public static int totalFile = 0;
    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (Exception ex) {
            port = 10000;
        }
        try {
            ServerSocket servSocket = new ServerSocket(port, 1024);
            System.out.println("Listening for connections on port " + servSocket.getLocalPort());
            while (true) {
                Socket connection = servSocket.accept();
                System.out.println("Connection established with " + connection);
                Thread echoThread = new EchoThread(connection);
                echoThread.start();

            }
        }
        catch (SocketException e) {
            System.err.println(e);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}

class EchoThread extends Thread {
    static final int BUFSIZE = 2048;
    public static Scanner sc = new Scanner(System.in);
    private Socket connSock;

    public EchoThread(Socket connSock) {
        this.connSock = connSock;
    }

    public void run() {
        try {
            InputStream in = this.connSock.getInputStream();
            InputStreamReader inputSR = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputSR);
            OutputStream out = this.connSock.getOutputStream();
            OutputStreamWriter outSW = new OutputStreamWriter(out);
            BufferedWriter bufferedWriter = new BufferedWriter(outSW);
            while (true) {
                String fileName = bufferedReader.readLine();
                if (fileName == null) {
                    break;
                }
                if (fileName.equals("QUIT")) {
                    System.out.println(this.connSock.getInetAddress() + ":" + this.connSock.getPort() + " has disconnected !");
                    break;
                }
                String dir = System.getProperty("user.dir");
                File file = new File(dir + "/" + fileName);
                DataOutputStream dataOut = new DataOutputStream(out);

                if (!file.exists()) {
                    long fileSize = 0L;
                    dataOut.writeLong(fileSize);
                    continue;
                }
                else {
                    long fileSize = file.length();
                    dataOut.writeLong(fileSize);

                    FileInputStream fileInput = new FileInputStream(file);
                    byte[] data = new byte[BUFSIZE];
                    int bytesread;
                    while ((bytesread = fileInput.read(data)) > 0) {
                        out.write(data, 0, bytesread);
                    }
                    out.flush();
                    fileInput.close();
                    System.out.println("Sent '" +fileName + "' to " + connSock.getInetAddress() + ":" + connSock.getPort() + " !");
                    countFile();
                }

                System.out.print("Input file name: ");
                String fileNamereceive = sc.nextLine();

                if (fileNamereceive.isEmpty()) {
                    System.out.println("File name is not empty!");
                    continue;
                }
                String tmp = fileNamereceive + "\n";
                bufferedWriter.write(tmp);
                bufferedWriter.flush();
                if (fileNamereceive.equals("QUIT")) {
                    break;
                }
                DataInputStream dataIn = new DataInputStream(in);
                long fileSize = dataIn.readLong();

                if (fileSize == 0) {
                    System.out.println("File not exist. Try again !");
                    continue;
                }
                else {
                    String dir2 = System.getProperty("user.dir");
                    OutputStream newFile = new FileOutputStream(dir2 + "/" + fileNamereceive);
                    byte[] data = new byte[BUFSIZE];
                    int bytesread;
                    long tmpfileSize = 0;

                    while ((bytesread = in.read(data)) > 0) {
                        newFile.write(data, 0, bytesread);
                        tmpfileSize += bytesread;
                        if (fileSize == tmpfileSize) break;
                    }
                    newFile.close();
                    System.out.println("Received '" + fileNamereceive + "' from server.");
                }

            }
            connSock.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void countFile() {
        Server.totalFile++;
        System.out.println("Total files have been downloaded: " + Server.totalFile);
    }
}






