/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author mtswe
 */
import com.mycompany.chat_app.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class Unit_Test {

    @Test
    public void testValidRecipient() {
        Message msg = new Message("+27831234567", "Test message");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipient() {
        Message msg = new Message("0731234567", "Test message");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    public void testShortMessageIsValid() {
        Message msg = new Message("+27831234567", "Short message");
        assertTrue(msg.checkMessageLength());
    }

    @Test
    public void testTooLongMessageFails() {
        String longText = "a".repeat(251);
        Message msg = new Message("+27831234567", longText);
        assertFalse(msg.checkMessageLength());
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message("+27831234567", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.createMessageHash();
        assertTrue(hash.matches("\\d{2}:\\d+:HITONIGHT\\?"),
            "Hash should match pattern XX:#:HITONIGHT?");
    }

    @Test
    public void testSendMessageOptionSend() {
        Message msg = new Message("+27831234567", "Hello");
        assertEquals("Message successfully sent.", msg.sendMessageOption("1"));
    }

    @Test
    public void testSendMessageOptionDisregard() {
        Message msg = new Message("+27831234567", "Hello");
        assertEquals("Press 0 to delete message.", msg.sendMessageOption("2"));
    }

    @Test
    public void testSendMessageOptionStore() {
        Message msg = new Message("+27831234567", "Hello");
        assertEquals("Message successfully stored.", msg.sendMessageOption("3"));
    }

    @Test
    public void testSendMessageOptionInvalid() {
        Message msg = new Message("+27831234567", "Hello");
        assertEquals("Invalid choice.", msg.sendMessageOption("x"));
    }

    @Test
    public void testReturnTotalMessagesIncrements() {
        int countBefore = Message.returnTotalMessages();
        new Message("+27830000000", "Test 1");
        new Message("+27830000000", "Test 2");
        int countAfter = Message.returnTotalMessages();
        assertEquals(countBefore + 2, countAfter);
    }

    @Test
    public void testStoredMessageReading() {
        ArrayList<Message> messages = Message.readStoredMessages();
        assertNotNull(messages);
    }

    @Test
    public void testSearchMessageByID() {
        Message msg1 = new Message("+27830000000", "Hi");
        Message msg2 = new Message("+27830000001", "It is dinner time!");
        String targetID = msg2.getMessageID();

        ArrayList<Message> sentMessages = new ArrayList<>();
        sentMessages.add(msg1);
        sentMessages.add(msg2);

        Message found = sentMessages.stream()
            .filter(m -> m.getMessageID().equals(targetID))
            .findFirst()
            .orElse(null);

        assertNotNull(found);
        assertEquals("It is dinner time!", found.getMessageText());
    }

    @Test
    public void testSearchMessagesByRecipient() {
        Message m1 = new Message("+27838884567", "Where are you?");
        Message m2 = new Message("+27838884567", "I have asked you to be on time.");
        Message m3 = new Message("+27831110000", "Hello");

        ArrayList<Message> allMessages = new ArrayList<>();
        allMessages.add(m1);
        allMessages.add(m2);
        allMessages.add(m3);

        long count = allMessages.stream()
            .filter(m -> m.getRecipient().equals("+27838884567"))
            .count();

        assertEquals(2, count);
    }

    @Test
    public void testDeleteMessageByHash() {
        Message m1 = new Message("+27838884567", "Where are you?");
        Message m2 = new Message("+27830000000", "Dinner");

        String targetHash = m1.getMessageHash();

        ArrayList<Message> sentMessages = new ArrayList<>();
        sentMessages.add(m1);
        sentMessages.add(m2);

        sentMessages.removeIf(m -> m.getMessageHash().equals(targetHash));

        boolean stillExists = sentMessages.stream()
            .anyMatch(m -> m.getMessageHash().equals(targetHash));

        assertFalse(stillExists);
    }

    @Test
    public void testLongestMessageDetection() {
        Message shortMsg = new Message("+27830000000", "Short");
        Message longMsg = new Message("+27830000000", "This is the longest message in the list.");

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(shortMsg);
        messages.add(longMsg);

        Message longest = messages.stream()
            .max((m1, m2) -> Integer.compare(m1.getMessageText().length(), m2.getMessageText().length()))
            .orElse(null);

        assertNotNull(longest);
        assertEquals("This is the longest message in the list.", longest.getMessageText());
    }

    @Test
    public void testMessageReportIncludesDetails() {
        Message msg = new Message("+27830000000", "Testing report");
        String details = msg.printDetails();

        assertTrue(details.contains("Message ID"));
        assertTrue(details.contains("Message Hash"));
        assertTrue(details.contains("Recipient"));
        assertTrue(details.contains("Message"));
    }
}
