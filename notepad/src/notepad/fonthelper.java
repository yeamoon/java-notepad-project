/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Nishi
 */
public class fonthelper extends JDialog implements ListSelectionListener {

    JPanel pan1, pan2, pan3;
    JTextField label, fonttext, typetext, sizetext;
    JLabel fontlabel, sizelabel, typelabel, previewlabel;
    JScrollPane fontscroll, sizescroll, typescroll;
    JList fontlist, sizelist, typelist;
    JButton ok, cancel;
    GridBagLayout gbl;
    GridBagConstraints gbc;

    public fonthelper() {
        setTitle("choose font");
        setSize(300, 400);
        setResizable(false);
        gbl = new GridBagLayout();
        setLayout(gbl);
        gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fontlabel = new JLabel("fonts:");
        getContentPane().add(fontlabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        sizelabel = new JLabel("sizes:");
        getContentPane().add(sizelabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        typelabel = new JLabel("type:");
        getContentPane().add(typelabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        fonttext = new JTextField("Arial", 12);
        getContentPane().add(fonttext, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        sizetext = new JTextField("8", 4);
        getContentPane().add(sizetext, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        typetext = new JTextField("Regular", 6);
        getContentPane().add(typetext, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontlist = new JList(fonts);
        fontlist.setSelectedIndex(0);
        fontlist.setFixedCellWidth(110);
        fontlist.addListSelectionListener(this);
        fontscroll = new JScrollPane(fontlist);
        getContentPane().add(fontscroll, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] sizes = {"8", "10", "12", "16", "18", "20", "24"};
        sizelist = new JList(sizes);
        sizelist.setSelectedIndex(0);
        sizelist.setFixedCellWidth(20);
        sizelist.addListSelectionListener(this);
        sizescroll = new JScrollPane(sizelist);
        getContentPane().add(sizescroll, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        String[] types = {"Regular", "Bold", "Italic", "Bold Italic"};
        typelist = new JList(types);
        typelist.setSelectedIndex(0);
        typelist.setFixedCellWidth(60);
        typelist.addListSelectionListener(this);
        typescroll = new JScrollPane(typelist);
        getContentPane().add(typescroll, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        pan1 = new JPanel();
        pan1.setLayout(new FlowLayout());
        previewlabel = new JLabel("preview:");
        pan1.add(previewlabel);
        getContentPane().add(pan1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());
        label = new JTextField("AaBbCcDdEeFfGgHhIiJjKk");
        label.setEditable(false);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        pan2.add(label);
        getContentPane().add(pan2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        pan3 = new JPanel();
        pan3.setLayout(new FlowLayout());
        ok = new JButton("ok");
        cancel = new JButton("cancel");

        pan3.add(ok);
        pan3.add(cancel);
        getContentPane().add(pan3, gbc);

    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == fontlist) {
            Font f1 = new Font(String.valueOf(fontlist.getSelectedValue()), typelist.getSelectedIndex(), Integer.parseInt(String.valueOf(sizelist.getSelectedValue())));
             fonttext.setText(String.valueOf(fontlist.getSelectedValue()));
            label.setFont(f1);
        
        }
        else  if (e.getSource() == sizelist) {
            Font f2 = new Font(String.valueOf(fontlist.getSelectedValue()), typelist.getSelectedIndex(), Integer.parseInt(String.valueOf(sizelist.getSelectedValue())));
             sizetext.setText(String.valueOf(sizelist.getSelectedValue()));
            label.setFont(f2);
        
        }
         else  {
            Font f3 = new Font(String.valueOf(fontlist.getSelectedValue()), typelist.getSelectedIndex(), Integer.parseInt(String.valueOf(sizelist.getSelectedValue())));
             typetext.setText(String.valueOf(typelist.getSelectedValue()));
            label.setFont(f3);
        
        }

    }

    public Font font() {
        Font font = new Font(String.valueOf(fontlist.getSelectedValue()), typelist.getSelectedIndex(), Integer.parseInt(String.valueOf(sizelist.getSelectedValue())));
        return font;
    }

    public JButton getok() {
        return ok;
    }

    public JButton getcancel() {
        return cancel;
    }
}
