import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileMaker implements FileOP {
    protected String Name;
    protected String FilePath;

    public String getFileName(){
        if(this.Name != null){
            return this.Name;
        }
        return "";
    }

    public String getFilePath(){
        if(this.FilePath != null){
            return this.FilePath;
        }
        return "";
    }

    public void setFilePath(String path){
        this.FilePath = path;
    }
    public void setFileName(String name){
        this.Name = name;
    }

    @Override
    public void Create() {
        String path = "./Files/";
        JFileChooser fileChooser = new JFileChooser(path);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File selected = fileChooser.getSelectedFile(); 
            System.out.println(selected);  
            String name = JOptionPane.showInputDialog("Enter File name: ");
            this.Name = (name += ".txt");
            this.FilePath = selected.getAbsolutePath() + "\\" + this.Name;
            System.out.println(this.FilePath);
        }
    }

    @Override
    public void Read() {
        String path = "./Files/";
        JFileChooser fileChooser = new JFileChooser(path);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File selected = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selected.getAbsolutePath());
            this.Name = selected.getName();
            this.FilePath = selected.getAbsolutePath();
        }else{
            System.out.println("No file selected.");
        }

    }

    @Override
    public void Delete() {

    }

    @Override
    public String FileExtention() {
        String filename = this.Name;
        String[] arr = filename.split("\\.");
        System.out.println(arr[1]);
        return arr[1];
    }
    
}
