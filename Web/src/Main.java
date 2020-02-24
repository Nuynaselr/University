import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Main {
    public static void main(String arg[]){
        try {
            Date date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").parse("Mon, 23 Feb 2020 10:26:36 SAMT");
            //                                   Tue 02 Jan 2018 18:07:59 IST         Tue 02 Jan 2018 18:07:59 IST
            System.out.println(date.toString());
        } catch (ParseException e){
            System.out.println(e);
        }

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toString());
        localDateTime = LocalDateTime.parse("2020-02-18T12:10:13.115");
        System.out.println(localDateTime.toString());

        //E MMM dd h:m:s
        //E, dd MMM yyyy HH:mm:ss z
    }
}
