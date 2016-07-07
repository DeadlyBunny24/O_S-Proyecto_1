/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EspolKeyValueStore
{
	
	public static Mapa map = new Mapa();
	
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
		//BufferedReader reader = null;
                
                ObjectInputStream reader = null;// = new ObjectInputStream(clientSocket.getInputStream())
		PrintWriter writer = null;
		try{
			reader = new ObjectInputStream (socket.getInputStream());
			writer = new PrintWriter(socket.getOutputStream(),true);
			while(true){
				String[] tokens = (String[]) reader.readObject();
				
				                              
                                
			switch(tokens[0]){
					
					case "get":
                                            if(EspolKeyValueStore.map.containsKey(tokens[1])){
                                                writer.println(EspolKeyValueStore.map.get(tokens[1]));
                                                break;
                                            }else{
                                                writer.println(tokens[1]+"=");
                                                break;
                                            }
                                        case "set":
                                            EspolKeyValueStore.map.set(tokens[1], tokens[2]);
                                            writer.println("OK");
                                            break;
                                        case "del":
                                            EspolKeyValueStore.map.del(tokens[1]);
                                            writer.println("OK");
                                            break;
                                        case "list":
                                            writer.println(EspolKeyValueStore.map.list());
                                            break;
				}
				
				
				/*if(line.equals("quit")){
					break;
				}*/
				//writer.println("Echo:"+line);
			}
		}catch (IOException e){
			throw new RuntimeException (e);
		} catch (ClassNotFoundException ex) {
                Logger.getLogger(tpool.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
			try{
				if(reader != null){ reader.close();}
				if(writer != null){ writer.close();}
			}catch(IOException e){
			}
		}
	}


}


class Mapa {
    
    private final HashMap<String,String> map = new HashMap<>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();
    
    public String get(String key){
        String s ="";
        
        r.lock();
            try{
                s = map.get(key);
                                
            }finally{
                r.unlock();
            }
            if(s ==null){
                return "key=";
            }else{
                return  key +"=" + s;
            }
        
    }
    
    public Boolean containsKey(String key){
        
        return map.containsKey(key);
                
    }
    
    public void set(String key, String value){
        
        w.lock();
        try{
            map.put(key, value);
        }finally{
            w.unlock();
        }
        
    }
    
    
    public void del(String key){
        
        w.lock();
        try{
            map.remove(key);
        }finally{
            w.unlock();
        }
        
    }
    
    public String list(){
        //O usare un TreeSet
        Set<String> set = new HashSet<>();
        
        r.lock();
        try{
           set = map.keySet();
           
        }finally{
            r.unlock();
        }
        
        String[] keys = set.toArray(new String[0]);
        Arrays.sort(keys);
        String listaDeKeys="";
        
        for(String s:keys){
            listaDeKeys += s + " ";
        }
        
        return listaDeKeys;
    }
}