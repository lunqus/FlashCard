package com.sigis.flashcards.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashCardBuilder {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame mFrame;

    public FlashCardBuilder() {

        /*
         *  Build the User Interface
         */

        // Create main Frame
        mFrame = new JFrame("Flash Card");
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main Panel
        JPanel mPanel = new JPanel();

        // Create Font
        Font mFont = new Font("Helvetica Neue", Font.BOLD, 21);

        // Initiate text area's and format text in it
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(mFont);

        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(mFont);

        // Creare JscrollPane's
        JScrollPane questionScrollPane = new JScrollPane(question);
        questionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollPane answerJScrollPane = new JScrollPane(answer);
        answerJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerJScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Create Button
        JButton nextButton = new JButton("Next Card");

        // Instantiate Array List
        cardList = new ArrayList<FlashCard>();

        // Create Labels
        JLabel questionLabel = new JLabel("Question");
        JLabel answerLabel = new JLabel("Answer");

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenue = new JMenu("File");

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");

        // Event Listener's
        nextButton.addActionListener(new NextCardListener());
        newMenuItem.addActionListener(new NewMenuActionListener());
        saveMenuItem.addActionListener(new SaveMenuActionListener());

        // Add to Menu
        menuBar.add(fileMenue);
        fileMenue.add(newMenuItem);
        fileMenue.add(saveMenuItem);


        // Add to the Frame
        mFrame.getContentPane().add(BorderLayout.CENTER, mPanel);
        mFrame.setSize(500, 500);
        mFrame.setVisible(true);
        mFrame.setJMenuBar(menuBar);

        // Add components to mPanel
        mPanel.add(questionLabel);
        mPanel.add(questionScrollPane);
        mPanel.add(answerLabel);
        mPanel.add(answerJScrollPane);
        mPanel.add(nextButton);


    }

    private class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Create Flash Card
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
            System.out.println(card.getQuestion() + ", Size: " + cardList.size());
        }
    }

    private class NewMenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("New Menu Item clicked!");

        }
    }

    private class SaveMenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // System.out.println("Save Menu Item clicked!");

            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);

            // File dialog with file chooser
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(mFrame);
            saveFile(fileSave.getSelectedFile());

        }
    }

    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus(); // Focus cursor on the question
    }


    private void saveFile(File selectedFile) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile));

            Iterator<FlashCard> cardIterator = cardList.iterator();
            while(cardIterator.hasNext()) {
                FlashCard card = (FlashCard) cardIterator.next();
                bw.write(card.getQuestion() + "/");
                bw.write(card.getAnswer() + "\n");

            }

            bw.close();

//                for (FlashCard card : cardList) {
//                    bw.write(card.getQuestion() + "/");
//                    bw.write(card.getAnswer() + "\n");
//                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardBuilder();
            }
        });
    }

}
