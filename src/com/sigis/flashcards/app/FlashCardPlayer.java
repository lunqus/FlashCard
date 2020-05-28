package com.sigis.flashcards.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashCardPlayer {

    private JTextArea display;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private Iterator cardIterator;
    private FlashCard currentCard;
    private int currentCardIndex;
    private JButton showAnswer;
    private JFrame playFrame;
    private boolean isShowAnswer;

    public FlashCardPlayer() {

        // Build UI
        playFrame = new JFrame("Flash Card Player");
        playFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel playPanel = new JPanel();
        Font playFont = new Font("Helvetica Neue", Font.BOLD, 22);

        display = new JTextArea(10,20);
        display.setFont(playFont);

        JScrollPane qJScrollPane = new JScrollPane(display);
        qJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        showAnswer = new JButton("Show Answer");

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenue = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load Card Set");

        // Event Listener's
        showAnswer.addActionListener(new NextCarsListener());
        loadMenuItem.addActionListener(new OpenMenuListener());

        // Add to Menu
        menuBar.add(fileMenue);
        fileMenue.add(loadMenuItem);

        // Add to the playFrame
        playFrame.add(BorderLayout.CENTER, playPanel);
        playFrame.setSize(640, 500);
        playFrame.setVisible(true);
        playFrame.setJMenuBar(menuBar);

        // Add components to the playPanel
        playPanel.add(qJScrollPane);
        playPanel.add(showAnswer);

    }

    public class NextCarsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class OpenMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(playFrame);
            loadFile(fileOpen.getSelectedFile());

        }
    }

    private void loadFile(File selectedFile) {

        cardList = new ArrayList<FlashCard>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
            String line = null;

            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeCard(String lineToParce) {

        String[] result = lineToParce.split("/");

        FlashCard card = new FlashCard(result[0], result[1]);
        cardList.add(card);
        System.out.println("Made a Card");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardPlayer();
            }
        });
    }
}
