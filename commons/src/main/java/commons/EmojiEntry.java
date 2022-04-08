package commons;

public class EmojiEntry {

    private String username;
    private String emoji;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public EmojiEntry(String username, String emoji) {
        this.username = username;
        this.emoji = emoji;
    }
}
