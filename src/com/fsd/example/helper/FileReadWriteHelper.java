package com.fsd.example.helper;

import com.fsd.example.model.Book;
import com.fsd.example.model.Subject;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileReadWriteHelper {

    public static boolean writeToFile(Object obj, String writeBook, String writeSubject) {
        try {
            File fileName =null;
            if(writeBook!=null && writeBook !="") {
                fileName = new File("javaAssignment1_book.txt");
            }else if(writeSubject!=null && writeSubject!=""){
                fileName = new File("javaAssignment1_subject.txt");
            }
            fileName.createNewFile();
            Files.write(fileName.toPath(),objectTobyteStream(obj));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static byte[] objectTobyteStream(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] objectInBytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            objectInBytes = bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return objectInBytes;
    }
    public static List<Book> readBooksFromFile() throws IOException, ClassNotFoundException {
        List<Book> objectList  = new ArrayList<Book>();
        File file = new File("javaAssignment1_book.txt");
        if(file.exists()) {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            objectList = (ArrayList) readByteStream(fileContent);
        }
        return objectList;
    }

    public static List<Subject> readSubjectsFromFile() throws IOException, ClassNotFoundException {
        File file = new File("javaAssignment1_subject.txt");
        List<Subject> objectList  = new ArrayList<Subject>();
        if(file.exists()) {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            objectList = (ArrayList) readByteStream(fileContent);
        }
        return objectList;
    }

    public static Object readByteStream(byte[] fileContent) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(fileContent);
        ObjectInputStream ois = new ObjectInputStream(bis);
        ArrayList<Object>  objFromFile = null;
        try {
            objFromFile = (ArrayList<Object>) ois.readObject();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return objFromFile;
    }
}
