import Realization.Comment;
import Realization.Comments;

import java.time.LocalDateTime;

public class Main {
    public static void main(String arg[]){
//        try {
//            Date date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").parse("Mon, 23 Feb 2020 10:26:36 SAMT");
//            //                                   Tue 02 Jan 2018 18:07:59 IST         Tue 02 Jan 2018 18:07:59 IST
//            System.out.println(date.toString());
//        } catch (ParseException e){
//            System.out.println(e);
//        }
//
//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime.toString());
//        localDateTime = LocalDateTime.parse("2020-02-18T12:10:13.115");
//        System.out.println(localDateTime.toString());

        //E MMM dd h:m:s
        //E, dd MMM yyyy HH:mm:ss z

        Comment com = new Comment("Usy");
        Comment comRead = new Comment();
        System.out.println(com.toString());
        Comment.writeFile(com);
        Comment.readFile("/home/np/University/Web/JsonSave.json", comRead);
        System.out.println(comRead.toString());
        comRead.setCommentsText("AAAAAAA");
        com.setPostTime(LocalDateTime.now());


        Comments listCom = new Comments();
        listCom.addElement(com);
        listCom.addElement(comRead);
        listCom.addElement(com);
        System.out.println(listCom.toString());
        listCom.listSort();
        System.out.println(listCom.toString());
        listCom.writeFile("/home/np/University/Web/JsonSaveList.json");
//        Comments listComRead = new Comments();
//        listComRead.readFile("/home/np/University/Web/JsonSaveList.json");
//        System.out.println(listComRead.toString());

        System.out.println(listCom.getComments(0) == listCom.getComments(1));

    }
}
