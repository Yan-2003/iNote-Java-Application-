import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class App{
    public static void main(String[] args) throws Exception {
        Settings MySettings = new Settings();
        FileMaker MyFile = new FileMaker();

        
        JTextArea NoteInput = new JTextArea("");
        EmptyBorder border = new EmptyBorder(10, 10, 10, 10);
        NoteInput.setBorder(border);
        NoteInput.setLineWrap(true);

        

        NoteInput.setFont(new Font("Arial", Font.PLAIN, MySettings.getFontSize()));
        NoteInput.setEditable(true);



        JFrame MainFrame = new JFrame("iNote");
        JMenuBar Navbar = new JMenuBar();

        JMenu File = new JMenu("File");
        JMenuItem New = new JMenuItem("New");
        JMenuItem Open = new JMenuItem("Open");
        JMenuItem OpenRecent = new JMenuItem("Open Recent");
        JMenuItem Save = new JMenuItem("Save");
        JMenuItem SaveAs = new JMenuItem("Save as");
        JMenuItem Delete = new JMenuItem("Delete");


        File.add(New);
        File.add(Open);
        File.add(OpenRecent);
        File.add(Save);
        File.add(SaveAs);
        File.add(Delete);

        JMenu Theme = new JMenu("Theme");
        JMenuItem Dark = new JMenuItem("Dark Theme");
        JMenuItem Light = new JMenuItem("Light Theme");
        JMenuItem Blue = new JMenuItem("Blue Theme");

        Theme.add(Dark);
        Theme.add(Light);
        Theme.add(Blue);

        JMenu Option = new JMenu("Option");
        JMenuItem FontSize = new JMenuItem("Font size");

        Option.add(FontSize);

        Navbar.add(File);
        Navbar.add(Theme);
        Navbar.add(Option);
        
        JScrollPane scrollable =  new JScrollPane(NoteInput);
        
        MainFrame.getContentPane().add(scrollable);

        
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setVisible(true);
        MainFrame.setLocationRelativeTo(null);
        MainFrame.setSize(700, 650);
        MainFrame.setResizable(false);
        MainFrame.setJMenuBar(Navbar);

        // Settings Loader 

        if(MySettings.getTheme().equals("LIGHT")){
            NoteInput.setBackground(Color.WHITE);
            NoteInput.setForeground(Color.BLACK);
        }else if(MySettings.getTheme().equals("DARK")){
            NoteInput.setBackground(Color.BLACK);
            NoteInput.setForeground(Color.WHITE);
            Navbar.setBackground(new Color(20, 20, 20));
            File.setForeground(Color.white);
            Theme.setForeground(Color.white);
            Option.setForeground(Color.white);
        }else if(MySettings.getTheme().equals("BLUE")){
            NoteInput.setBackground(Color.BLUE);
            NoteInput.setForeground(Color.WHITE);
        }else{
            NoteInput.setBackground(Color.WHITE);
            NoteInput.setForeground(Color.BLACK);
        }



         // events for the navigation
        OpenRecent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NoteInput.setText(null);
                MyFile.setFilePath(MySettings.getRecentOpen());
                MyFile.setFileName(MySettings.getName());
                MainFrame.setTitle("iNote " + MyFile.getFileName());
                if(!MySettings.getRecentOpen().isEmpty()){
                    try (BufferedReader reader = new BufferedReader(new FileReader(MySettings.getRecentOpen()))){
                        String Line;
                        while((Line = reader.readLine()) != null){
                            NoteInput.append(Line + "\n");
                        }
                        JOptionPane.showMessageDialog(null, "Open Recent file: " + MySettings.getName(), "Open", JOptionPane.INFORMATION_MESSAGE);
                
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No Recent file open", "Open", JOptionPane.INFORMATION_MESSAGE);
                }
               
            }
            
        });


        New.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MyFile.Create();
                NoteInput.setText(null);
                MainFrame.setTitle("iNote " + MyFile.getFileName());


                // this will create a recent to a new file
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(MySettings.getPath()))){   
                    String change = "THEME="+MySettings.getTheme()+"\nFONTSIZE="+MySettings.getFontSize()+"\nRECENT="+MyFile.getFilePath();
                    writer.write(change);
                } catch (Exception x) {
                    x.printStackTrace();
                }

            }
            
        });



        Open.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                MyFile.Read();

               if(MyFile.FileExtention().equals("txt")){
                    NoteInput.setText(null);
                    MainFrame.setTitle("iNote " + MyFile.getFileName());
                    try (BufferedReader reader = new BufferedReader(new FileReader(MyFile.getFilePath()))){
                        String Line;
                        while((Line = reader.readLine()) != null){
                            NoteInput.append(Line + "\n");
                            
                        }
                        
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }else{
                   JOptionPane.showMessageDialog(null, "The File your are trying to open is not a txt", "Error", JOptionPane.WARNING_MESSAGE);
                }


                // this will set a recent open file
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(MySettings.getPath()))){   
                    String change = "THEME="+MySettings.getTheme()+"\nFONTSIZE="+MySettings.getFontSize()+"\nRECENT="+MyFile.getFilePath();
                    writer.write(change);
                } catch (Exception x) {
                    x.printStackTrace();
                }
                

            }
            
        });


        Save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!MyFile.getFilePath().isEmpty()){
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(MyFile.getFilePath()))){   
                         String Text = NoteInput.getText();
                         writer.write(Text);
                         JOptionPane.showMessageDialog(null , "Save Successfully", "Save", JOptionPane.INFORMATION_MESSAGE);
                     
                    } catch (Exception x) {
                         x.printStackTrace();
                    }
                }else{
                    MyFile.Create();
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(MyFile.getFilePath()))){   
                        String Text = NoteInput.getText();
                        writer.write(Text);
                        JOptionPane.showMessageDialog(null , "Save Successfully","Save", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception x) {
                            x.printStackTrace();
                    }
                }

            }
            
        });


        SaveAs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MyFile.Create();
                 try(BufferedWriter writer = new BufferedWriter(new FileWriter(MyFile.getFilePath()))){   
                    String Text = NoteInput.getText();
                    writer.write(Text);
                    JOptionPane.showMessageDialog(null , "Save Successfully","Save as", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception x) {
                         x.printStackTrace();
                }
            }
            
        });


        Delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               if(MyFile.getFilePath().isEmpty()){
                    JOptionPane.showMessageDialog(null, "There is no file Selected.", null, JOptionPane.QUESTION_MESSAGE);
               }else{

                    File toDelete = new File(MyFile.getFilePath());
                    if(toDelete.delete()){
                        NoteInput.setText(null);
                        JOptionPane.showMessageDialog(null, "File Successfully Deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "File Failed to Delete.", "Delete", JOptionPane.ERROR_MESSAGE);
                    }

                    MainFrame.setTitle("iNote");
                    MyFile.setFileName(null);
                    MyFile.setFilePath(null);
                    // this will creal the recent file open
                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(MySettings.getPath()))){   
                        String change = "THEME="+MySettings.getTheme()+"\nFONTSIZE="+MySettings.getFontSize()+"\nRECENT=";
                        writer.write(change);
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
               }
            }
             
        });

        FontSize.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               String size = JOptionPane.showInputDialog(null, "Set FontSize" , MySettings.getFontSize());
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(MySettings.getPath()))){   
                    String change = "THEME="+MySettings.getTheme()+"\nFONTSIZE="+size+"\nRECENT="+MyFile.getFilePath();
                    writer.write(change);
                    JOptionPane.showMessageDialog(null , "This will require to restart the program.", "Restart", JOptionPane.WARNING_MESSAGE);
                } catch (Exception x) {
                    x.printStackTrace();
                }
                MainFrame.dispose();
            }
            
        });

        // theme functions 

        Dark.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(MySettings.getPath()))){   
                    String change = "THEME=DARK\nFONTSIZE="+MySettings.getFontSize()+"\nRECENT="+MySettings.getRecentOpen();
                    writer.write(change);
                    JOptionPane.showMessageDialog(null , "This will require to restart the program.", "Restart", JOptionPane.WARNING_MESSAGE);
                } catch (Exception x) {
                    x.printStackTrace();
                }
                MainFrame.dispose();
            }
            
        });

        Light.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(MySettings.getPath()))){   
                    String change = "THEME=LIGHT\nFONTSIZE="+MySettings.getFontSize()+"\nRECENT="+MySettings.getRecentOpen();
                    writer.write(change);
                    JOptionPane.showMessageDialog(null , "This will require to restart the program.", "Restart", JOptionPane.WARNING_MESSAGE);
                } catch (Exception x) {
                    x.printStackTrace();
                }
                MainFrame.dispose();
            }
            
        });

        Blue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(MySettings.getPath()))){   
                    String change = "THEME=BLUE\nFONTSIZE="+MySettings.getFontSize()+"\nRECENT="+MySettings.getRecentOpen();
                    writer.write(change);
                    JOptionPane.showMessageDialog(null , "This will require to restart the program.", "Restart", JOptionPane.WARNING_MESSAGE);
                } catch (Exception x) {
                    x.printStackTrace();
                }
                MainFrame.dispose();
            }
            
        });



    }

}
