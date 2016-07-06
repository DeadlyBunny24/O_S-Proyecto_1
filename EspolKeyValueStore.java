import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class EspolKeyValueStore
{
	public static void main(String[] args) {
		try {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		/* args[0] corresponde al puerto donde escucha el servidor */
		ServerSocket sock = new ServerSocket(Integer.parseInt(args[0]));
		/* now listen for connections */
			while (true) {
				Socket client = sock.accept();
				executorService.execute(new tpool(client));

				//PrintWriter pout = new
				//PrintWriter(client.getOutputStream(), true);
				/* write the Date to the socket */
				//pout.println(new java.util.Date().toString());
				/* close the socket and resume */
				/* listening for connections */
				//client.close();
			}
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
class tpool implements Runnable{

	private Socket socket;

	public tpool (Socket s){
		this.socket = s;
	}

	public void run(){
		BufferedReader reader = null;
		PrintWriter writer = null;
		try{
			reader = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(),true);
			while(true){
				String line = reader.readLine();
				if(line.equals("quit")){
					break;
				}
				writer.println("Echo:"+line);
			}
		}catch (IOException e){
			throw new RuntimeException (e);
		}finally{
			try{
				if(reader != null){ reader.close();}
				if(writer != null){ writer.close();}
			}catch(IOException e){
			}
		}
	}
}
