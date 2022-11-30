package Validation.gui;

import Validation.util.Pair;
import Validation.validate.Validator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class MyFrame extends JFrame {

    private Pair<File> currentPair;

    private final Validator validator;

    private final JPanel contentPane = new JPanel(new GridLayout(1, 2));

    private final JPanel buttonPanel = new JPanel();

    DefaultStyledDocument document = new DefaultStyledDocument(), document1 = new DefaultStyledDocument();
    JTextPane pane = new JTextPane(document), pane1 = new JTextPane(document1);

    public MyFrame(Validator validator) {
        this.validator = validator;
        setTitle("Programs To Validate");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 800);
        add(contentPane);

        pane.setBorder(new LineBorder(Color.DARK_GRAY));
        pane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(pane);
        contentPane.add(scrollPane);

        pane1.setBorder(new LineBorder(Color.DARK_GRAY));
        pane1.setEditable(false);
        JScrollPane scrollPane1 = new JScrollPane(pane1);
        contentPane.add(scrollPane1);
        contentPane.setBounds(0, 100, 1000, 600);

        JButton equalButton = new JButton("equal");
        equalButton.addActionListener((ActionEvent e) -> {
            if (currentPair != null) {
                validator.validatePair(currentPair);
                // update the panel
                updateScreen();
            }
        });
        JButton inequalButton = new JButton("inequal");
        inequalButton.addActionListener((ActionEvent e) -> {
            if (currentPair != null) {
                validator.invalidatePair(currentPair);
                // update the panel
                updateScreen();
            }
        });
        JButton unsureButton = new JButton("unsure");
        unsureButton.addActionListener((ActionEvent e) -> {
            if (currentPair != null) {
                validator.suspectPair(currentPair);
                updateScreen();
            }
        });
        buttonPanel.add(equalButton);
        buttonPanel.add(inequalButton);
        buttonPanel.add(unsureButton);
        add(buttonPanel);
//        buttonPanel.setBounds(500, 800, 800, 50);
        updateScreen();
    }

    private void updateScreen() {
        currentPair = validator.getNextProgramPair();
        if (currentPair != null) {
            try {
                File file1 = currentPair.first();
                File file2 = currentPair.second();
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
                String str;
                StringBuffer sb = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    sb.append(str).append("\n");
                }
                StyleContext context = new StyleContext();
                Style style = context.addStyle("test", null);
                document = new DefaultStyledDocument();
                StyleConstants.setForeground(style, Color.BLACK);
                document.insertString(0, sb.toString(), style);
                StyleConstants.setForeground(style, Color.BLUE);
                document.insertString(0, "\n\n", style);
                document.insertString(0, getLastThreeDir(file1.getAbsolutePath()) + "\n", style);
                pane.setDocument(document);

                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file2), StandardCharsets.UTF_8));
                sb = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    sb.append(str).append("\n");
                }
                document1 = new DefaultStyledDocument();
                StyleConstants.setForeground(style, Color.BLACK);
                document1.insertString(0, sb.toString(), style);
                StyleConstants.setForeground(style, Color.BLUE);
                document1.insertString(0, "\n\n", style);
                document1.insertString(0, getLastThreeDir(file2.getAbsolutePath()) + "\n", style);
                pane1.setDocument(document1);
            } catch (IOException | BadLocationException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            remove(contentPane);
            remove(buttonPanel);
            repaint();
            System.out.println(validator.getFinalResult().disjointLists());
        }
    }

    String getLastThreeDir(String path) {
        String[] dirs = path.split("/");
        int tot = dirs.length;
        return dirs[tot - 3] + "/" + dirs[tot - 2] + "/" + dirs[tot - 1];
    }

}
