package core;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

public class Comments implements Serializable {
    private List<Comment> comments;
    private final long UID = 1L; //????

    public int getLength(){
        return this.comments.size();
    }

    public long getUID() {
        return this.UID;
    }

    public void addElement(int index, Comment comment){
        this.comments.add(index, comment);
    }

    public void deleteComment(int index){
        this.comments.remove(index);
    }

    public void writeFile(Comment comments){
        try (Writer fileSave = new FileWriter("JsonSave.json")) {
            for (Comment element: this.comments) {
                element.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listSort(){
        Collections.sort(this.comments);
    }
}
