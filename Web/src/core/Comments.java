package core;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comments implements Serializable {
    private List<Comment> comments;
    private final long UID = 1L; //????

    public Comments(){
        this.comments = new ArrayList<Comment>();
    }

    public int getLength(){
        return this.comments.size();
    }

    public long getUID() {
        return this.UID;
    }

    public void addElementToIndex(int index, Comment comment){
        this.comments.add(index, comment);
    }

    public void addElement(Comment comment){
        this.comments.add(comment);
    }

    public void deleteComment(int index){
        this.comments.remove(index);
    }

    public void writeFile(){
        try (Writer fileSave = new FileWriter("JsonSaveList.json")) {
            JSONObject writeFile = new JSONObject();
            fileSave.write('[');
            for (Comment element: this.comments) {
                writeFile.put("User", element.getUser());
                writeFile.put("PostTime", element.getPostTime());
                writeFile.put("Text", element.getCommentsText());
                writeFile.put("Likes", "" + element.getLikesCount());
                fileSave.write(writeFile.toString() + ',');
            }
            fileSave.write(']');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listSort(){
        Collections.sort(this.comments);
    }

    @Override
    public String toString() {
        String returnString = "Comments{";
        for (Comment element: this.comments){
            returnString += element.toString();
        }
        return returnString + ", UID=" + UID + '}';
    }
}
