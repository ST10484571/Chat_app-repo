/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chat_app;


/**
 *
 * @author mtswe
 */
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Chat_app {
    public static void main(String[] args) {
        User user = new User();
        Login login = new Login();
        login.register(user);

        if (user.getUsername() == null || user.getPassword() == null) {
            return; // Login failed
        }

        ArrayList<Message> sentMessages = new ArrayList<>();
        ArrayList<Message> disregardedMessages = new ArrayList<>();
        ArrayList<Message> storedMessages = Message.readStoredMessages();
        ArrayList<String> messageHashes = new ArrayList<>();
        ArrayList<String> messageIDs = new ArrayList<>();

        JOptionPane.showMessageDialog(null, "Welcome to QuickChat!");

        while (true) {
            String menu = JOptionPane.showInputDialog("""
                    Choose an option:
                    1) Send Messages
                    2) Show Recently Sent Messages
                    3) Quit
                    4) Search Message by ID
                    5) Search Messages by Recipient
                    6) Delete Message by Hash
                    7) Display Report""");

            if (menu == null || menu.equals("3")) break;

            switch (menu) {
                case "1" -> {
                    int numMessages = Integer.parseInt(
                            JOptionPane.showInputDialog("How many messages do you want to send?")
                    );

                    for (int i = 0; i < numMessages; i++) {
                        String recipient = JOptionPane.showInputDialog("Enter recipient phone number (include +country code):");
                        String messageText = JOptionPane.showInputDialog("Enter your message (max 250 chars):");

                        Message msg = new Message(recipient, messageText);

                        if (!msg.checkRecipientCell()) {
                            JOptionPane.showMessageDialog(null, "Invalid recipient phone number.");
                            continue;
                        }

                        if (!msg.checkMessageLength()) {
                            int over = messageText.length() - 250;
                            JOptionPane.showMessageDialog(null,
                                    "Message exceeds 250 characters by " + over + ". Please shorten it.");
                            continue;
                        }

                        String choice = JOptionPane.showInputDialog("""
                                What do you want to do?
                                1) Send Message
                                2) Disregard Message
                                3) Store Message""");

                        String result = msg.sendMessageOption(choice);
                        JOptionPane.showMessageDialog(null, result);

                        switch (choice) {
                            case "1" -> {
                                sentMessages.add(msg);
                                messageHashes.add(msg.getMessageHash());
                                messageIDs.add(msg.getMessageID());
                                JOptionPane.showMessageDialog(null, msg.printDetails());
                            }
                            case "2" -> disregardedMessages.add(msg);
                            case "3" -> {
                                storedMessages.add(msg);
                                msg.storeMessage();
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(null,
                            "Total messages sent: " + Message.returnTotalMessages());

                    Message longest = sentMessages.stream()
                            .max((m1, m2) -> Integer.compare(m1.getMessageText().length(), m2.getMessageText().length()))
                            .orElse(null);

                    if (longest != null) {
                        JOptionPane.showMessageDialog(null, "Longest message:\n" + longest.getMessageText());
                    }
                }
                case "2" -> JOptionPane.showMessageDialog(null, "Coming Soon.");

                case "4" -> {
                    String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
                    for (Message m : sentMessages) {
                        if (m.getMessageID().equals(searchID)) {
                            JOptionPane.showMessageDialog(null, "Recipient: " + m.getRecipient() + "\nMessage: " + m.getMessageText());
                            break;
                        }
                    }
                }
                case "5" -> {
                    String searchRecipient = JOptionPane.showInputDialog("Enter recipient number to search:");
                    StringBuilder results = new StringBuilder();
                    for (Message m : sentMessages) {
                        if (m.getRecipient().equals(searchRecipient)) {
                            results.append(m.getMessageText()).append("\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, results.length() > 0 ? results.toString() : "No messages found.");
                }
                case "6" -> {
                    String hashToDelete = JOptionPane.showInputDialog("Enter message hash to delete:");
                    Message toRemove = null;
                    for (Message m : sentMessages) {
                        if (m.getMessageHash().equals(hashToDelete)) {
                            toRemove = m;
                            break;
                        }
                    }
                    if (toRemove != null) {
                        sentMessages.remove(toRemove);
                        JOptionPane.showMessageDialog(null, "Message \"" + toRemove.getMessageText() + "\" successfully deleted.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Message not found.");
                    }
                }
                case "7" -> {
                    StringBuilder report = new StringBuilder("Message Report:\n");
                    for (Message m : sentMessages) {
                        report.append(m.printDetails()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(null, report.length() > 0 ? report.toString() : "No messages sent yet.");
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Try again.");
            }
        }
    }
}

/* Refernce List
    IIE Varasity College
    Chat GPt
*/