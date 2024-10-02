import java.util.*;
import java.io.*;
import java.awt.*;

public class Driver{
  //format user1 csv1 user2 csv2 etc.
  public static void main(String args[]){
    if (args.length >= 2) {
      try {
        Bookshelf one = parse(new Scanner(new File(args[1])), args[0]);
        Bubble bubble = new Bubble(one);

        for (int i = 2; i < args.length-1; i+=2){
          bubble.addUser(parse(new Scanner(new File(args[i+1])), args[i]));
        }

        System.out.println(bubble.toString());

      }
      catch(FileNotFoundException e) {}
    }
  }

  public static Bookshelf parse(Scanner input, String username){
    Bookshelf b = new Bookshelf(username);
    Book tempBook;
    String line=input.nextLine();
    String title;
    String authorList;
    String[] tempAuthors = new String[10];
    String[] authors;
    long isbn;
    int status; //1 if read, 0 if to read
    double rating;
    Scanner lineScan;
    Scanner fieldScan;
    int count;
    String temp;

    while(input.hasNextLine()){
      //System.out.println();
      line = input.nextLine();
      lineScan = new Scanner(line);
      //System.out.println(line);
      //Title
      if (line.charAt(0)=='\"'){
        lineScan.useDelimiter("\"");
        title = lineScan.next();
        //System.out.println(title);
        line = line.substring(title.length()+3);
      } else{
        lineScan.useDelimiter(",");
        title = lineScan.next();
        //System.out.println(title);
        line = line.substring(title.length()+1);
      }

      //AUTHOR
      lineScan = new Scanner(line);
      if(line.substring(0, 2).equals("\"\"")){
        authors = new String[1];
        authors[0] = "UNKNOWN";
      }else if (line.charAt(0)=='\"'){
        lineScan.useDelimiter("\"");
        authorList = lineScan.next();
        line = line.substring(authorList.length()+3);
        //System.out.println(authorList);
        fieldScan = new Scanner(authorList);
        fieldScan.useDelimiter(", ");
        count = 0;
        while(fieldScan.hasNext()){
          tempAuthors[count]=fieldScan.next();
          count++;
        }
        authors = new String[count];
        for (int i =0; i <count; i++){
          authors[i] = tempAuthors[i];
          //System.out.print(authors[i]+", ");
        }
      } else{
        lineScan.useDelimiter(",");
        authors = new String[1];
        authors[0]=lineScan.next();
        line = line.substring(authors[0].length()+1);
        //System.out.println(authors[0]);
      }

      //CONTRIBUTORS
      lineScan = new Scanner(line);
      if (line.charAt(0)=='\"'){
        lineScan.useDelimiter("\"");
        authorList = lineScan.next();
        line = line.substring(authorList.length()+3);
      } else{
        lineScan.useDelimiter(",");
        temp=lineScan.next();
        line = line.substring(temp.length()+1);
      }

      //ISBN - either numbers, nothing or ""
      if(line.charAt(0)=='9'){
        lineScan = new Scanner(line);
        lineScan.useDelimiter(",");
        temp = lineScan.next();
        line = line.substring(temp.length()+1);
        isbn = Long.parseLong(temp);
        //System.out.println(isbn);
      } else{
        continue;
      }

      //FORMAT
      lineScan = new Scanner(line);
      lineScan.useDelimiter(",");
      temp = lineScan.next();
      line = line.substring(temp.length()+1);

      //STATUS
      lineScan = new Scanner(line);
      lineScan.useDelimiter(",");
      temp = lineScan.next();
      line = line.substring(temp.length()+1);
      if (temp.equals("to-read")){
        status = 0;
        //System.out.println("to-read");
      } else if (temp.equals("read")){
        status = 1;
        //System.out.println("read");
      } else{
        continue;
      }

      rating = -1;
      if (status == 1){
        //DATE ADDED
        lineScan = new Scanner(line);
        if (line.charAt(0)=='\"'){
          lineScan.useDelimiter("\"");
          temp=lineScan.next();
          line = line.substring(temp.length()+3);
        } else{
          lineScan.useDelimiter(",");
          temp=lineScan.next();
          line = line.substring(temp.length()+1);
        }

        //LAST DATE READ
        lineScan = new Scanner(line);
        if (line.charAt(0)=='\"'){
          lineScan.useDelimiter("\"");
          temp=lineScan.next();
          line = line.substring(temp.length()+3);
        } else{
          lineScan.useDelimiter(",");
          temp=lineScan.next();
          line = line.substring(temp.length()+1);
        }

        //DATES READ
        lineScan = new Scanner(line);
        if (line.charAt(0)=='\"'){
          lineScan.useDelimiter("\"");
          temp=lineScan.next();
          line = line.substring(temp.length()+3);
        } else{
          lineScan.useDelimiter(",");
          temp=lineScan.next();
          line = line.substring(temp.length()+1);
        }


        //READ COUNT
        lineScan = new Scanner(line);
        lineScan.useDelimiter(",");
        temp = lineScan.next();
        line = line.substring(temp.length()+1);

        lineScan = new Scanner(line);
        //System.out.println(line);
        lineScan.useDelimiter(",");
        while(lineScan.hasNext()){
          temp = lineScan.next();
          if(temp.length()>0 && (temp.charAt(0)=='0' ||
             temp.charAt(0)=='1' ||
             temp.charAt(0)=='2' ||
             temp.charAt(0)=='3' ||
             temp.charAt(0)=='4' ||
             temp.charAt(0)=='5')){
               rating = Float.parseFloat(temp);
             }
        }
      }
      //System.out.println(rating);
      tempBook = new Book(title, authors, isbn, status, rating);
      //System.out.println(tempBook);
      b.addBook(tempBook);
    }
    return b;
  }
}
