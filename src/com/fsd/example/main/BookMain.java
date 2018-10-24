package com.fsd.example.main;

import com.fsd.example.helper.BookHelper;
import com.fsd.example.helper.SubjectHelper;

import java.io.IOException;
import java.util.Scanner;

public class BookMain {

    public static void main(String[] args) throws Exception {


        SubjectHelper subjectHelper = new SubjectHelper();
        BookHelper bookHelper = new BookHelper();

        bookHelper.createBookTableIfNotExists();
        subjectHelper.createSubjectTableIfNotExists();

        Scanner input = new Scanner(System.in);
        int menuChoice = 0;
        String pause;
        try {
            do {

                System.out.println("\n***********************"
                        + "\n**********MENU*********"
                        + "\n***********************");
                System.out.println("\n1. Add a Subject"
                        + "\n2. Add a Book"
                        + "\n3. Delete a Subject"
                        + "\n4. Delete a book"
                        + "\n5. Search for a book"
                        + "\n6. Search for a subject"
                        + "\n7. Exit"
                        + "\n\nPlease enter your selection and then press enter : ");

                menuChoice = input.nextInt();
                switch (menuChoice) {
                    case 1:
                        SubjectHelper.addSubject();
                        System.out.println("Press enter to continue");
                        pause = new Scanner(System.in).nextLine();
                        break;
                    case 2:
                        BookHelper.addBook();
                        System.out.println("Press enter to continue");
                        pause = new Scanner(System.in).nextLine();
                        break;
                    case 3:
                        subjectHelper.deleteSubject();
                        System.out.println("Press enter to continue");
                        pause = new Scanner(System.in).nextLine();
                        break;
                    case 4:
                        bookHelper.deleteBook();
                        System.out.println("Press enter to continue");
                        pause = new Scanner(System.in).nextLine();
                        break;
                    case 5:
                        bookHelper.searchByBook();
                        System.out.println("Press enter to continue");
                        pause = new Scanner(System.in).nextLine();
                        break;
                    case 6:
                        subjectHelper.searchBySubject();
                        System.out.println("Press enter to continue");
                        pause = new Scanner(System.in).nextLine();
                        break;
                    default:
                        break;
                }
            } while (menuChoice != 7);
        } catch (Exception e) {
            System.out.println("\nInvalid input " + e.getMessage());
            main(args);
        }
    }

}
