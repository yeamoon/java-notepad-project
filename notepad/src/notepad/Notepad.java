/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Nishi
 */
public class Notepad extends JFrame {

    JTextArea mainarea;
    JMenuBar mbar;
    JMenu mnufile, mnuedit, mnuformat, mnuhelp;
    JMenuItem itmnew, itmopen, itmsave, itmsaveas, itmexit;
    JMenuItem itmcopy, itmcut, itmpaste,itmcolor,itmfind,itmreplace,itmfont;

    JFileChooser fc;
    String filename;
    String filecontent;
    UndoManager undo;
    UndoAction undoaction;
    RedoAction redoaction;
   
    
    JCheckBoxMenuItem wordwrap;
    fonthelper font;
    

    public Notepad() {
        initcomponent();
        itmsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                save();
            }
        });

        itmsaveas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                saveas();
            }
        });
        itmopen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                open();
            }
        });

        itmnew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                open_new();
            }
        });
        /*itmexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(1);
            }
        });
         */
        itmcut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mainarea.cut();
            }
        });
        itmcopy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mainarea.copy();
            }
        });
        itmpaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                mainarea.paste();
            }
        });
       
        wordwrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (wordwrap.isSelected()) {
                    mainarea.setWrapStyleWord(true);
                    mainarea.setLineWrap(true);

                } else {
                    mainarea.setWrapStyleWord(false);
                    mainarea.setLineWrap(false);

                }

            }
        });
        itmcolor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Color c=new JColorChooser().showDialog(rootPane,"choose font color" , Color.yellow);
            mainarea.setForeground(c);
                        }
        });
        mainarea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                   undo.addEdit(e.getEdit());
                   undoaction.update();
                   redoaction.update();
            }
        });

itmfont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               font.setVisible(true);
                        }
        });
font.getok().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mainarea.setFont(font.font());
               font.setVisible(false);
                        }
        });
font.getcancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               font.setVisible(false);
                        }
        });
    }

    private void initcomponent() {
        fc = new JFileChooser(".");
        undo = new UndoManager();
       undoaction =new UndoAction();
       redoaction=new RedoAction();
        mainarea = new JTextArea();
        getContentPane().add(mainarea);
        getContentPane().add(new JScrollPane(mainarea), BorderLayout.CENTER);
        setTitle("untitled notepad");
        setSize(800, 600);

        mbar = new JMenuBar();
        mnufile = new JMenu("File");
        mnuedit = new JMenu("Edit");

        mnuformat = new JMenu("Format");
        mnuhelp = new JMenu("Help");
        itmnew = new JMenuItem("New");
        itmopen = new JMenuItem("Open");
        itmsave = new JMenuItem("Save");
        itmsaveas = new JMenuItem("Save as");
        wordwrap = new JCheckBoxMenuItem("word wrap");
        itmcut = new JMenuItem("Cut");
        itmcopy = new JMenuItem("Copy");
        itmpaste = new JMenuItem("Paste");
        itmcolor=new JMenuItem("Font color");
        itmfind=new JMenuItem("Find");       
        itmreplace=new JMenuItem("Replace");
        itmfont=new JMenuItem("Font");

        


        itmnew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        itmopen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        itmsave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        mnufile.add(itmnew);
        mnufile.add(itmopen);

        mnufile.add(itmsave);
        mnufile.add(itmsaveas);
        //mnufile.addSeparator();
        //mnufile.add(itmexit);
       mnuedit.add(undoaction);
       mnuedit.add(redoaction);
        mnuedit.add(itmcut);
        mnuedit.add(itmcopy);
        mnuedit.add(itmpaste);
        mnuedit.add(wordwrap);
        
                mnuedit.add(itmfind);
        mnuedit.add(itmreplace);
        mnuedit.add(itmfont);

        mnuformat.add(itmcolor);

        mbar.add(mnufile);
        mbar.add(mnuedit);
        mbar.add(mnuformat);
        mbar.add(mnuhelp);
        setJMenuBar(mbar);
        font=new fonthelper();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void save() {
        PrintWriter fout = null;
        int option = -1;
        try {
            if (filename == null) {
                saveas();

            } else {
                fout = new PrintWriter(new FileWriter(filename));

                String s = mainarea.getText();
                StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
                while (st.hasMoreElements()) {
                    fout.println(st.nextToken());
                }
                JOptionPane.showMessageDialog(rootPane, "file saved");
                filecontent = mainarea.getText();
            }
        } catch (IOException e) {
        } finally {
            if (fout != null) {
                fout.close();
            }

        }
    }

    private void saveas() {
        PrintWriter fout = null;
        int option = -1;
        try {
            option = fc.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {

                fout = new PrintWriter(new FileWriter(fc.getSelectedFile()));
            }
            String s = mainarea.getText();
            StringTokenizer st = new StringTokenizer(s, System.getProperty("line.separator"));
            while (st.hasMoreElements()) {
                fout.println(st.nextToken());
            }
            JOptionPane.showMessageDialog(rootPane, "file saved");
            filecontent = mainarea.getText();

            filename = fc.getSelectedFile().getName();
            setTitle(filename);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (fout != null) {
                fout.close();
            }

        }
    }

    private void open() {
        try {
            int option = fc.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                mainarea.setText(null);
                Reader in = new FileReader(fc.getSelectedFile());
                char[] buf = new char[100000];
                int n;
                while ((n = in.read(buf, 0, buf.length)) != -1) {
                    mainarea.append(new String(buf, 0, n));
                }
            }
            filename = fc.getSelectedFile().getName();
            setTitle(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void open_new() {
        try {
            if (!mainarea.getText().equals("") && !mainarea.getText().equals(filecontent)) {
                if (filename == null) {
                    int option = JOptionPane.showConfirmDialog(rootPane, "Do u want to save the changes ?");
                    if (option == 0) {
                        saveas();
                        clear();
                    } else {
                        clear();
                    }
                } else {
                    int option = JOptionPane.showConfirmDialog(rootPane, "Do u want to save the changes ?");
                    if (option == 0) {
                        save();
                        clear();
                    } else {
                        clear();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clear() {
        mainarea.setText(null);
        filecontent = null;
        filename = null;
        setTitle("untitld notepad");
    }

   class UndoAction extends AbstractAction{

        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                undo.undo();
            } catch (CannotUndoException e) {
                e.printStackTrace();
            }
            update();
            redoaction.update();
        }
        private void update(){
            if(undo.canUndo()){
                setEnabled(true);
                putValue(Action.NAME, "Undo");
                
            }
            else{
              setEnabled(false);
                putValue(Action.NAME, "Undo");
                  
            }
        }
       
   }
class RedoAction extends AbstractAction{

        public RedoAction() {
            super("Redo");
            setEnabled(false);
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                undo.redo();
            } catch (CannotRedoException e) {
                e.printStackTrace();
            }
            update();
            undoaction.update();
        }
        private void update(){
            if(undo.canRedo()){
                setEnabled(true);
                putValue(Action.NAME, "Redo");
                
            }
            else{
              setEnabled(false);
                putValue(Action.NAME, "Redo");
                  
            }
        }
       
   }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Notepad n = new Notepad();
    }

}
