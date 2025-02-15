package Data;

public class Message {
    private long[] content;
    private String signature;
    private int sender_id;

    public Message(long[] content, String signature, int sender_id) {
        this.content = content;
        this.signature = signature;
        this.sender_id = sender_id;
    }

    public long[] content() {
        return content;
    }

    public int getSender_id() {
        return sender_id;
    }
}
