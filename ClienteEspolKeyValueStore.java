import java.net.*;
import java.io.*;
public class ClienteEspolKeyValueStore
{
	public static void main(String[] args) {
		try {
		/* make connection to server socket */
		/* args[0] corresponde a la ip del servidor (127.0.0.1 es una referencia a la misma maquina) */
		/* args[1] es el puerto que escucha el servidor*/
			Socket sock = new Socket(args[0],Integer.parseInt(args[1]));
			InputStream in = sock.getInputStream();
			BufferedReader bin = new
			BufferedReader(new InputStreamReader(in));
			/* read the date from the socket */
			String line;
			while ( (line = bin.readLine()) != null)
			System.out.println(line);
			/* close the socket connection*/
			sock.close();
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
