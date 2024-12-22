import java.util.*;
import java.io.*;

// Library Management System
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        library.loadBooksFromFile("books.txt");
        library.run();
        library.saveBooksToFile("books.txt");
    }
}

class Library {
    private List<Book> books;
    private Scanner scanner;

    public Library() {
        books = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            printMenu();
            int choice = getChoice();
            switch (choice) {
                case 1 -> viewBooks();
                case 2 -> addBook();
                case 3 -> deleteBook();
                case 4 -> searchBook();
                case 5 -> issueBook();
                case 6 -> returnBook();
                case 7 -> {
                    System.out.println("Exiting... Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Library Management System ---");
        System.out.println("1. View All Books");
        System.out.println("2. Add New Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Search Book");
        System.out.println("5. Issue Book");
        System.out.println("6. Return Book");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        System.out.println("\nAvailable Books:");
        books.forEach(System.out::println);
    }

    private void addBook() {
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        books.add(new Book(title, author, isbn));
        System.out.println("Book added successfully.");
    }

    private void deleteBook() {
        System.out.print("Enter ISBN of the book to delete: ");
        String isbn = scanner.nextLine();
        books.removeIf(book -> book.getIsbn().equals(isbn));
        System.out.println("Book deleted if it existed.");
    }

    private void searchBook() {
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();
        books.stream()
                .filter(book -> book.getTitle().contains(keyword) || book.getAuthor().contains(keyword))
                .forEach(System.out::println);
    }

    private void issueBook() {
        System.out.print("Enter ISBN of the book to issue: ");
        String isbn = scanner.nextLine();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && !book.isIssued()) {
                book.setIssued(true);
                System.out.println("Book issued successfully.");
                return;
            }
        }
        System.out.println("Book is not available or already issued.");
    }

    private void returnBook() {
        System.out.print("Enter ISBN of the book to return: ");
        String isbn = scanner.nextLine();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && book.isIssued()) {
                book.setIssued(false);
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Book is not issued or does not exist.");
    }

    public void loadBooksFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                books.add(new Book(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3])));
            }
        } catch (IOException e) {
            System.out.println("No previous data found, starting fresh.");
        }
    }

    public void saveBooksToFile(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Book book : books) {
                bw.write(book.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
}

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean issued;

    public Book(String title, String author, String isbn) {
        this(title, author, isbn, false);
    }

    public Book(String title, String author, String isbn, boolean issued) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.issued = issued;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    @Override
    public String toString() {
        return String.format("Title: %s, Author: %s, ISBN: %s, Issued: %s", title, author, isbn, issued ? "Yes" : "No");
    }

    public String toCsv() {
        return String.join(",", title, author, isbn, String.valueOf(issued));
    }
}