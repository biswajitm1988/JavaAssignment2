package com.fsd.example.helper;

import com.fsd.example.model.Book;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class BookHelper {

    public static void searchByBook() throws IOException, ClassNotFoundException {
        List<Book> bookList  = FileReadWriteHelper.readBooksFromFile();
        List<Book> bookDetailsList  = new ArrayList<Book>();
        if (bookList.isEmpty()) {
            System.out.println("There are no books in the system");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter title by which you want to search : ");
        String bookTitle = input.nextLine();
        for (Book book:bookList){
            if(bookTitle!=null && book.getTitle()!=null
                && book.getTitle().toLowerCase().contains(bookTitle.toLowerCase())){
                bookDetailsList.add(book);
            }
        }
        if(bookDetailsList.isEmpty()){
            System.out.println("no books found for your search : "+bookTitle);
        }else{
            System.out.println("Matching Books :\n"+bookDetailsList);
        }

    }

    public static void deleteBook() throws IOException, ClassNotFoundException {
        List<Book> bookList  = FileReadWriteHelper.readBooksFromFile();
        if (bookList.isEmpty()) {
            System.out.println("There are no books in the system");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter title by which you want to delete : ");
        String bookTitle = input.nextLine();

        int count=0;
        ListIterator<Book> listIterator = bookList.listIterator();
        while (listIterator.hasNext()){
            Book book = listIterator.next();
            if(book.getTitle().contains(bookTitle)){
                listIterator.remove();
                count++;
            }
        }
        boolean status = FileReadWriteHelper.writeToFile(bookList,"writeBook", null);
        System.out.println("Number of records deleted : "+count);

    }

    public static void addBook() throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("\n****************************"
                + "\n**********ADD A BOOK********"
                + "\n****************************");
        String inputText;
        System.out.println("\nEnter Book Title :");
        String bookTitle = input.nextLine();
        double price = enterBookPrice();
        int volume = enterBookVolume();
        LocalDate publishDate = parsePublishDate();
        List<Book> bookList  = FileReadWriteHelper.readBooksFromFile();
        Long bookId = null;
        if(bookList!=null && !bookList.isEmpty()) {
            Collections.sort(bookList);
            bookId = bookList.get(bookList.size() - 1).getBookId()+1;
        }else{
            bookList = new ArrayList<Book>();
            bookId=1l;
        }
        Book newBook = new Book(bookId,bookTitle,price,volume, publishDate);
        bookList.add(newBook);
        boolean status = FileReadWriteHelper.writeToFile(bookList,"writeBook", null);
        System.out.println("\nBook Added "+status+" Id="+newBook.getBookId());
    }

    private static int enterBookVolume() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter volume of the book :");
        int inputPrice = 0;
        try {
            inputPrice = input.nextInt();
        }catch(Exception e){
            System.out.println("\nInvalid input "+e.getMessage());
            enterBookVolume();
        }
        return inputPrice;
    }

    private static double enterBookPrice() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter price of the book :");
        double inputPrice = 0;
        try {
            inputPrice = input.nextDouble();
        }catch(Exception e){
            System.out.println("\nInvalid input "+e.getMessage());
            enterBookPrice();
        }
        return inputPrice;
    }

    private static LocalDate parsePublishDate() {
        Scanner input = new Scanner(System.in);
        LocalDate publishDate=null;
        boolean validDate=true;
        System.out.println("\nAdd published date in (mm/dd/yy) format : ");
        String inputDate = input.nextLine();
        try {
            publishDate = LocalDate.parse(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("mm/dd/yy").parse(inputDate)));
        }catch(Exception e){
            System.out.println("\nInvalid input format "+e.getMessage());
            parsePublishDate();
        }
        return publishDate;
    }
}
