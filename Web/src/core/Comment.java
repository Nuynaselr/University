package core;

import org.json.*;

import java.io.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Comment implements Serializable, Comparable<Comment> {
    private String User;
    private String CommentsText;
    private LocalDateTime PostTime;
    private int LikesCount;

    //Sorted by time

    public Comment(){}

    public Comment(String User){
        this.User = User;
        this.CommentsText = "";
        this.PostTime = LocalDateTime.now();
        this.LikesCount = 0;
    }

    public Comment(String User, String CommentsText, LocalDateTime Posttime, int Likescount){
        this.User = User;
        this.CommentsText = CommentsText;
        this.PostTime = Posttime;
        this.LikesCount = Likescount;
    }

    //getter for all methods
    public String getUser() {
        return User;
    }

    public String getCommentsText() {
        return CommentsText;
    }

    public LocalDateTime getPostTime() {
        return PostTime;
    }

    public int getLikesCount() {
        return LikesCount;
    }

    //setter for all methods
    public void setUser(String user) {
        this.User = user;
    }

    public void setCommentsText(String commentsText) {
        this.CommentsText = commentsText;
    }

    public void setPostTime(LocalDateTime posttime) {
        this.PostTime = posttime;
    }

    public void setLikesCount(int likescount) {
        this.LikesCount = likescount;
    }

    public static void writeFile(Comment comments){
        try (Writer fileSave = new FileWriter("JsonSave.json")) {
            JSONObject writeFile = new JSONObject();
            writeFile.put("User", comments.getUser());
            writeFile.put("PostTime", comments.getPostTime());
            writeFile.put("Text", comments.getCommentsText());
            writeFile.put("Likes", "" + comments.getLikesCount());
            fileSave.write(writeFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String filePath, Comment comment){
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            JSONObject json = new JSONObject(reader.readLine());
            comment.setUser(json.getString("User"));
            comment.setPostTime(LocalDateTime.parse(json.getString("PostTime")));
            comment.setCommentsText(json.getString("Text"));
            comment.setLikesCount(Integer.parseInt(json.getString("Likes")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int compareTo(Comment element) {
        return this.getPostTime().compareTo(element.getPostTime());
    }

    @Override
    public String toString(){
        return "User: " + this.User +
                " Time: " + this.PostTime +
                " Text: " + this.CommentsText +
                " Likes Count: " + this.LikesCount;
    }
}
