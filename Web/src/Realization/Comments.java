package Realization;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Comments implements Serializable {
    private List<Comment> comments;
    private final long UID = 1L; //????
    private String rowError = "!!Error!!";
    private boolean checkError = false;

    public Comments(){
        this.comments = new ArrayList<Comment>();
    }

    public int getLength(){
        return this.comments.size();
    }

    public long getUID() {
        return this.UID;
    }

    public String getRowError() {
        return rowError;
    }

    public boolean isCheckError() {
        return checkError;
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

    public void writeFile(String pathToFile){
        try (Writer fileSave = new FileWriter(pathToFile)) {
            JSONObject writeFile = new JSONObject();
            int index = this.comments.size();
            fileSave.write('[');
            for (Comment element: this.comments) {
                writeFile.put("User", element.getUser());
                writeFile.put("PostTime", element.getPostTime());
                writeFile.put("Text", element.getCommentsText());
                writeFile.put("Likes", "" + element.getLikesCount());
                fileSave.write(writeFile.toString());
                if (index > 1)  fileSave.write(',');
                index--;
            }
            fileSave.write(']');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(String pathToTheFile){
        try {
            File file = new File(pathToTheFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            JSONArray json = new JSONArray(reader.readLine());
            for (int i = 0; i < json.length(); i++){
                Comment timeElement = new Comment();
                timeElement.setUser(json.getJSONObject(i).getString("User"));
                timeElement.setPostTime(LocalDateTime.parse(json.getJSONObject(i).getString("PostTime")));
                timeElement.setCommentsText(json.getJSONObject(i).getString("Text"));
                timeElement.setLikesCount(Integer.parseInt(json.getJSONObject(i).getString("Likes")));
                this.comments.add(timeElement);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listSort(){
        try {
            Collections.sort(this.comments);
        }catch (Error e){
            this.checkError = true;
            this.rowError += "\n" + e;
        }
    }

    public void deleteDuplicate() {
        try {
            Set<Comment> set = new HashSet<>(this.comments);
            this.comments.clear();
            this.comments.addAll(set);
        }catch (Error e){
            this.checkError = true;
            this.rowError += "\n" + e;
        }
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
