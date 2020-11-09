import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WatchFileSystem {
    public static void main(String[] args){
        try {
            Path p = Paths.get(".");
            WatchKey wk = p.register(p.getFileSystem().newWatchService(), 
                                     StandardWatchEventKinds.ENTRY_MODIFY);
            while (true){
                for (WatchEvent<?> event: wk.pollEvents()){
                    Path x = (Path)event.context();
                    System.out.println(x.toString());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
