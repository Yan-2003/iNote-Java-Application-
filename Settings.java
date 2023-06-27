import java.io.BufferedReader;
import java.io.FileReader;

public class Settings {
    public String Theme;
    public String path;
    public String FilePath;
    public int FontSize;


    Settings(){
        this.path = "Settings.dat"; 
        try (BufferedReader reader = new BufferedReader(new FileReader(this.path))){
            String Line;
            while((Line = reader.readLine()) != null){
                String[] arr = Line.split("=", 2);
                if(arr[0].equals("THEME")){
                    this.Theme = arr[1];
                }
                if(arr[0].equals("FONTSIZE")){
                    this.FontSize = Integer.parseInt(arr[1]);
                }      
                if(arr[0].equals("RECENT")){
                    if(arr[1] != null){
                        this.FilePath = arr[1];
                    }
                }      
            }
                        
        } catch (Exception x) {
            x.printStackTrace();
        }

    }

    public String getTheme(){
        return this.Theme;
    }

    public String getPath(){
        return this.path;
    }

    public int getFontSize(){
        return this.FontSize;
    }

    public void setRecentOpen(String path){
        this.path = path;
    }

    public String getRecentOpen(){
        return this.FilePath;
    }

    public String getName(){
        String[] path = this.FilePath.split("\\\\");
        return path[path.length-1];
    }
}
