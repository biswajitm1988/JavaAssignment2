package com.fsd.example.helper;

import com.fsd.example.model.Book;
import com.fsd.example.model.Subject;

import java.io.IOException;
import java.util.*;

public class SubjectHelper {
    public static void addSubject() throws IOException, ClassNotFoundException {
        List<Book> bookList = FileReadWriteHelper.readBooksFromFile();
        if (bookList.isEmpty()) {
            System.out.println("There are no books to add to subject. Please add at least one book first, then try again");
            return;
        }
        System.out.println("\n*******************************"
                + "\n**********ADD A SUBJECT********"
                + "\n*******************************");
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter a Subject Title :");
        String inputSubTitle = input.nextLine();
        int durTime = enterDurationInHours();
        Set<Book> bookSet = selectBooks(bookList);
        List<Subject> subjectList = FileReadWriteHelper.readSubjectsFromFile();
        Long subjectId = null;
        if (subjectList != null && !subjectList.isEmpty()) {
            Collections.sort(subjectList);
            subjectId = subjectList.get(subjectList.size() - 1).getSubjectId() + 1;
        } else {
            subjectList = new ArrayList<Subject>();
            subjectId = 1l;
        }

        Subject newSubject = new Subject(subjectId, inputSubTitle, durTime, bookSet);
        subjectList.add(newSubject);
        boolean status = FileReadWriteHelper.writeToFile(subjectList,null, "writeSubject");
        System.out.println("\nSubject Added "+status+" Id="+newSubject.getSubjectId());
    }

    private static Set<Book> selectBooks(List<Book> bookList) throws IOException, ClassNotFoundException {
        Set<Book> bookSet = new HashSet<Book>();
        if (!bookList.isEmpty()) {
            bookSet = enterBookIds(bookList);
        }
        return bookSet;
    }

    private static Set<Book> enterBookIds(List<Book> bookList) {
        Set<Book> bookSet = new HashSet<Book>();
        try {
            System.out.println("Select books from the following books to add to the subject");
            System.out.println(bookList);
            Scanner input = new Scanner(System.in);
            System.out.println("Enter book ids as comma separated values : ");
            String bookIds = input.nextLine();
            if (bookIds != null && bookIds != "") {
                List<String> bookIdList = Arrays.asList(bookIds.split(","));
                for (Book book : bookList) {
                    for (String bookId : bookIdList) {
                        if (book.getBookId().equals(Long.valueOf(bookId))) {
                            bookSet.add(book);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\nInvalid input " + e.getMessage());
            enterBookIds(bookList);
        }
        return bookSet;
    }

    private static int enterDurationInHours() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter duration in Hours :");
        int durTime = 0;
        try {
            durTime = input.nextInt();
        } catch (Exception e) {
            System.out.println("\nInvalid input " + e.getMessage());
            enterDurationInHours();
        }
        return durTime;
    }

    public static void deleteSubject() throws IOException, ClassNotFoundException {
        List<Subject> subjectList = FileReadWriteHelper.readSubjectsFromFile();
        if (subjectList.isEmpty()) {
            System.out.println("There are no subjects in the system");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter subject by which you want to delete : ");
        String subtitle = input.nextLine();

        ListIterator<Subject> listIterator = subjectList.listIterator();
        int count=0;
        while (listIterator.hasNext()){
            Subject subject = listIterator.next();
            if(subject.getSubtitle().contains(subtitle)) {
                listIterator.remove();
                count++;
            }
        }
        boolean status = FileReadWriteHelper.writeToFile(subjectList,null, "writeSubject");
        System.out.println("Number of records deleted : "+count);
    }


    public static void searchBySubject() throws IOException, ClassNotFoundException {
        List<Subject> subjectList = FileReadWriteHelper.readSubjectsFromFile();
        List<Book> bookDetailsList  = new ArrayList<Book>();
        if (subjectList.isEmpty()) {
            System.out.println("There are no subjects in the system");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("\nEnter subject by which you want to search : ");
        String subtitle = input.nextLine();
        for (Subject subject:subjectList){
            if(subtitle!=null
                    && subject.getSubtitle()!=null
                    && subject.getSubtitle().toLowerCase().contains(subtitle.toLowerCase())) {
                for (Book book : subject.getReferences()) {
                        bookDetailsList.add(book);
                }
            }
        }
        if(bookDetailsList.isEmpty()){
            System.out.println("no books found for your search : "+subtitle);
        }else{
            System.out.println("Matching Books :\n"+bookDetailsList);
        }
    }
}
