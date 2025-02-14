package Data;

public class Message {
    private long[] content;
    private long[] contentSender;

    public Message(long[] content, long[] contentSender) {
        this.content = content;
        this.contentSender = contentSender;
    }

    public long[] content() {
        return content;
    }
}
