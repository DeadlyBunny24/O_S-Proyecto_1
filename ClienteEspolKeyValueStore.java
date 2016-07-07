import java.net.*;
import java.io.*;
import java.util.Arrays;
public class ClienteEspolKeyValueStore
{
	public static void main(String[] args) {
		try {
		/* make connection to server socket */
		/* args[0] corresponde a la ip del servidor (127.0.0.1 es una referencia a la misma maquina) */
		/* args[1] es el puerto que escucha el servidor*/
			Socket sock = new Socket(args[0],Integer.parseInt(args[1]));
			PrintWriter out=null;
			out=new PrintWriter(sock.getOutputStream(),true);
			InputStream in = sock.getInputStream();
			BufferedReader bin = new
			BufferedReader(new InputStreamReader(in));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			/* read the date from the socket */
			//Preparando el string
				String line = stdIn.readLine();
				line = line.trim().replaceAll(" +"," ");
				String arr[] = line.split(" ");
				arr[0] = arr[0].toLowerCase();
				String str1;
				if(arr.length>2){
					str1 = Arrays.toString(Arrays.copyOfRange(arr, 2, arr.length));
					arr[2] = str1.substring(1, str1.length()-1).replaceAll(",", " ");
					arr = Arrays.copyOfRange(arr, 0, 3);
				}
			while (!(arr[0].equals("exit"))){
				switch (arr[0]) {
					case "get":
						if(arr.length==2){
							out.println(line);
							System.out.println(bin.readLine());
						}else{
							System.out.println("ERROR: Numero invalido de parametros");
						}
					 break;
					case "set":
						if(arr.length==3){
								if(!(arr[2].contains("\\n")) && !(arr[1].contains("\\t")) && !(arr[1].contains("\\n")) && !(arr[1].contains(" "))){
									if(arr[1].getBytes("UTF-8").length<=128e6 && arr[2].getBytes("UTF-8").length<=2e9){
										out.println(line);
										System.out.println(bin.readLine());
								}else{
										System.out.println("ERROR: \"key\" no puede exceder los 128MB y \"value\" no puede exceder los 2GB ");
								}
							}else{
								System.out.println("ERROR: \"key\" no puede contener saltos de linea, espacios en blanco, ni tabs; y \"value\" no puede contener saltos de linea");
							}
						}else{
							System.out.println("ERROR: Numero invalido de parametros");
						}
                     break;
					case "del":
						if(arr.length==2){
							out.println(line);
							System.out.println(bin.readLine());
						}else{
							System.out.println("ERROR: Numero invalido de parametros");
						}
                     break;
					case "list":
						if(arr.length==1){
							out.println(line);
							i_line = bin.readLine();
							i_line = i_line.trim().replaceAll(" ","\\n");
							System.out.println(i_line);
						}else{
							System.out.println("ERROR: Numero invalido de parametros");
						}
                     break;
					case "help":
						if(arr.length==1){
							System.out.println("------------------------Lista de Comandos------------------------");
							System.out.println("get key:         		Operacion get . Retorna el valor asociado adicha clave.");
							System.out.println("set key value:  		Almacena (en memoria) la clave, con el valor asociado.\n El valor puede contener cualquier caracter, incluso caracteres especiales, tabs y espaciones en blanco.\n key no puede exceder los 128 MB, y value no puede exceder los 2GB.");
							System.out.println("del key:          	Elimina la clave con su valor asociado.");
							System.out.println("list: 							Retorna la lista de claves almacenadas.");
							System.out.println("exit: 							Cierra la conexion con el servidor.");
							System.out.println("help: 							Muestra lista de comandos disponibles y una breve descripcion de su operacion.");
							System.out.println("-----------------------------------------------------------------");
						}else{
							System.out.println("ERROR: Numero invalido de parametros");
						}
                     break;
					 default:
							System.out.println("Comando no reconocido. Utilize el comando: help para mostrar ayuda");
				}
				line = stdIn.readLine();
				line = line.trim().replaceAll(" +"," ");
				arr = line.split(" ");
				arr[0] = arr[0].toLowerCase();
				if(arr.length>2){
					str1 = Arrays.toString(Arrays.copyOfRange(arr, 2, arr.length));
					arr[2] = str1.substring(1, str1.length()-1).replaceAll(",", " ");
					arr = Arrays.copyOfRange(arr, 0, 3);
				}
			}

			/* close the socket connection*/
			sock.close();
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
