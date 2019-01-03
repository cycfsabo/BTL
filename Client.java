import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static final int BUFSIZE = 2048;
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String host;
        int port;
        try {
            System.out.print("IP: ");
            host = sc.nextLine();
            System.out.print("Port: ");
            port = sc.nextInt();
            InetAddress addrHost = InetAddress.getByName(host);
            Socket socket = new Socket(addrHost, 10000);

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter outSW = new OutputStreamWriter(out);
            BufferedWriter bufferedWriter = new BufferedWriter(outSW);
            InputStream in = socket.getInputStream();
            InputStreamReader inputSR = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputSR);
            String tmp1 = sc.nextLine();
            while (true) {
                System.out.print("Input file name: ");
                String fileName = sc.nextLine();

                if (fileName.isEmpty()) {
                    System.out.println("File name is not empty!");
                    continue;
                }
                String tmp = fileName + "\n";
                bufferedWriter.write(tmp);
                bufferedWriter.flush();
                if (fileName.equals("QUIT")) {
                    break;
                }
                DataInputStream dataIn = new DataInputStream(in);
                long fileSize = dataIn.readLong();

                if (fileSize == 0) {
                    System.out.println("File not exist. Try again !");
                    continue;
                }
                else {
                    String dir = System.getProperty("user.dir");
                    OutputStream newFile = new FileOutputStream(dir + "/" + fileName);
                    byte[] data = new byte[BUFSIZE];
                    int bytesread;
                    long tmpfileSize = 0;

                    while ((bytesread = in.read(data)) > 0) {
                        newFile.write(data, 0, bytesread);
                        tmpfileSize += bytesread;
                        if (fileSize == tmpfileSize) break;
                    }
                    newFile.close();
                    System.out.println("Received '" + fileName + "' from server.");
                }

                String fileNameSend = bufferedReader.readLine();
                if (fileNameSend == null) {
                    break;
                }
                if (fileNameSend.equals("QUIT")) {
                    System.out.println(socket.getInetAddress() + ":" + socket.getPort() + " has disconnected !");
                    break;
                }
                String dir = System.getProperty("user.dir");
                File fileS = new File(dir + "/" + fileNameSend);
                DataOutputStream dataOut = new DataOutputStream(out);

                if (!fileS.exists()) {
                    fileSize = 0L;
                    dataOut.writeLong(fileSize);
                    continue;
                }
                else {
                    long fileSendSize = fileS.length();
                    dataOut.writeLong(fileSendSize);

                    FileInputStream fileInput = new FileInputStream(fileS);
                    byte[] data = new byte[BUFSIZE];
                    int bytesread;
                    while ((bytesread = fileInput.read(data)) > 0) {
                        out.write(data, 0, bytesread);
                    }
                    out.flush();
                    fileInput.close();
                    System.out.println("Sent '" +fileName + "' to " + socket.getInetAddress() + ":" + socket.getPort() + " !");

                }

            }
            socket.close();

        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
