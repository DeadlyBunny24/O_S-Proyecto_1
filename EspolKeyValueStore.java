import java.net.*;
import java.io.*;
public class EspolKeyValueStore
{
	public static void main(String[] args) {
		try {
		/* args[0] corresponde al puerto donde escucha el servidor */
		ServerSocket sock = new ServerSocket(Integer.parseInt(args[0]));
		/* now listen for connections */
			while (true) {
				Socket client = sock.accept();
				PrintWriter pout = new
				PrintWriter(client.getOutputStream(), true);
				/* write the Date to the socket */
				pout.println(new java.util.Date().toString());
				/* close the socket and resume */
				/* listening for connections */
				client.close();
			}
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
