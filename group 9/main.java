package CmPrg_PETA;

import java.util.Scanner;

public class main {

    static Scanner input = new Scanner(System.in);

    // 1. INITIAL COLLECTION OF 10 BOOKS
    static int[] bookIDs = {
            1001, 1002, 1003, 1004, 1005,
            1006, 1007, 1008, 1009, 1010
    };

    // 2. PARALLEL ARRAYS
    static String[] titles = {
            "Noli Me Tangere",
            "El Filibusterismo",
            "Florante at Laura",
            "Ibong Adarna",
            "Harry Potter",
            "The Little Prince",
            "Atomic Habits",
            "The Alchemist",
            "To Kill a Mockingbird",
            "Introduction to Java"
    };

    static String[] categories = {
            "Literature", "Literature", "Poetry", "Epic",
            "Fantasy", "Fiction", "Self-Help", "Fiction",
            "Classic", "Technology"
    };

    // true = Available, false = Borrowed
    static boolean[] availability = {
            true, true, true, true, true,
            true, true, true, true, true
    };

    // Number of successful borrowing transactions per book
    static int[] borrowingCounts = new int[10];

    public static void main(String[] args) {

        // 3. MAIN MENU AND LOOP
        int choice = 0;

        do {
            System.out.println("\n===== LIBRARY CIRCULATION ANALYTICS =====");
            System.out.println("1. View All Books");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. View Available Books");
            System.out.println("6. View Borrowed Books");
            System.out.println("7. View Circulation Analytics");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            if (!input.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                input.nextLine();
                continue;
            }

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                // case 1 (Valerie)
                case 1:
                    viewAllBooks();
                    break;

                // case 2 (Zyrah)
                case 2:
                    searchBook();
                    break;

                // case 3 (Zyrah)
                case 3:
                    borrowBook();
                    break;

                // case 4 (Lester and Aldrin)
                case 4:
                    returnBook();
                    break;

                // case 5 (Lester and Aldrin)
                case 5:
                    viewAvailableBooks();
                    break;

                // case 6 (Lester and Aldrin)
                case 6:
                    viewBorrowedBooks();
                    break;

                // case 7 (Caden)
                case 7:
                    viewAnalytics();
                    break;

                // case 8 (Valerie)
                case 8:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Select 1-8.");
            }

        } while (choice != 8);

        input.close();
    }

    // 4. VIEW ALL BOOKS
    static void viewAllBooks() {

        System.out.println("\n========== ALL BOOKS ==========");

        System.out.printf(
                "%-8s %-28s %-15s %-15s %-10s%n",
                "ID", "TITLE", "CATEGORY", "AVAILABILITY", "BORROWS"
        );

        for (int i = 0; i < bookIDs.length; i++) {

            String status;

            if (availability[i]) {
                status = "Available";
            } else {
                status = "Borrowed";
            }

            System.out.printf(
                    "%-8d %-28s %-15s %-15s %-10d%n",
                    bookIDs[i],
                    titles[i],
                    categories[i],
                    status,
                    borrowingCounts[i]
            );
        }
    }

    // 5. SEARCH BOOK BY ID OR TITLE
    static void searchBook() {

        System.out.println("\n========== SEARCH BOOK ==========");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Title");
        System.out.print("Choose an option: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid input.");
            input.nextLine();
            return;
        }

        int option = input.nextInt();
        input.nextLine();

        if (option == 1) {

            System.out.print("Enter Book ID: ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid Book ID.");
                input.nextLine();
                return;
            }

            int id = input.nextInt();
            input.nextLine();

            int index = findBookByID(id);

            if (index != -1) {
                displayBook(index);
            } else {
                System.out.println("Book ID not found.");
            }

        } else if (option == 2) {

            System.out.print("Enter book title or keyword: ");
            String keyword = input.nextLine().trim();

            if (keyword.isEmpty()) {
                System.out.println("Please enter a title.");
                return;
            }

            boolean found = false;

            for (int i = 0; i < titles.length; i++) {

                if (titles[i].toLowerCase()
                        .contains(keyword.toLowerCase())) {

                    displayBook(i);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matching book found.");
            }

        } else {
            System.out.println("Invalid search option.");
        }
    }

    // 6. BORROW A BOOK
    static void borrowBook() {

        System.out.println("\n========== BORROW A BOOK ==========");
        System.out.print("Enter Book ID to borrow: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid Book ID.");
            input.nextLine();
            return;
        }

        int id = input.nextInt();
        input.nextLine();

        // Check if the ID exists
        int index = findBookByID(id);

        if (index == -1) {
            System.out.println(
                    "Borrowing failed: Book ID does not exist."
            );
            return;
        }

        // Check if the book is available
        if (!availability[index]) {
            System.out.println(
                    "Borrowing failed: The book is already borrowed."
            );
            return;
        }

        // Update the arrays after successful borrowing
        availability[index] = false;
        borrowingCounts[index]++;

        System.out.println("\nBorrowing successful!");
        System.out.println("Book ID: " + bookIDs[index]);
        System.out.println("Title: " + titles[index]);
        System.out.println("Status: Borrowed");
        System.out.println(
                "Total successful borrows: " + borrowingCounts[index]
        );
    }

    // 7. FIND A BOOK USING ITS ID
    static int findBookByID(int id) {

        for (int i = 0; i < bookIDs.length; i++) {

            if (bookIDs[i] == id) {
                return i;
            }
        }

        return -1;
    }

    // 8. DISPLAY ONE BOOK'S INFORMATION
    static void displayBook(int index) {

        System.out.println("\n---------- BOOK DETAILS ----------");
        System.out.println("ID: " + bookIDs[index]);
        System.out.println("Title: " + titles[index]);
        System.out.println("Category: " + categories[index]);
        System.out.println(
                "Availability: "
                        + (availability[index] ? "Available" : "Borrowed")
        );
        System.out.println(
                "Borrowing Count: " + borrowingCounts[index]
        );
    }

    // 9. PLACEHOLDERS
    static void returnBook() {
        System.out.println("\n========== RETURN A BOOK ==========");
        System.out.print("Enter Book ID: ");

        if (!input.hasNextInt()) {
            System.out.println("Invalid Book ID.");
            input.nextLine();
            return;
        }

        int id = input.nextInt();
        input.nextLine();

        int index = findBookByID(id);

        if (index == -1) {
            System.out.println("Book ID not found.");
        } else if (availability[index]) {
            System.out.println("This book is already available.");
        } else {
            availability[index] = true;

            // returning will not increase it counts
            System.out.println("Book returned successfully!");
            System.out.println("Title: " + titles[index]);
        }
    }


    static void viewAvailableBooks() {
        int count = 0;

        System.out.println("\n===== AVAILABLE BOOKS =====");

        for (int i = 0; i < bookIDs.length; i++) {
            if (availability[i]) {
                System.out.println(bookIDs[i] + " - " + titles[i]);
                count++;
            }
        }

        System.out.println("Total available: " + count);
    }

    // view borrowed books
    static void viewBorrowedBooks() {
        int count = 0;

        System.out.println("\n===== BORROWED BOOKS =====");

        for (int i = 0; i < bookIDs.length; i++) {
            if (!availability[i]) {
                System.out.println(bookIDs[i] + " - " + titles[i]);
                count++;
            }
        }

        System.out.println("Total borrowed: " + count);
    };

    static void viewAnalytics() {
        System.out.println("\n========== LIBRARY ANALYTICS ==========");

        int totalBooks = bookIDs.length;
        int totalTransactions = 0;
        int borrowedBooks = 0;
        int mostBorrows = 0;
        int leastBorrows = Integer.MAX_VALUE;

        // calculate lahat ng transactions
        for (int i = 0; i < totalBooks; i++) {

            totalTransactions += borrowingCounts[i];

            if (!availability[i]) {
                borrowedBooks++;
            }

            if (borrowingCounts[i] > mostBorrows) {
                mostBorrows = borrowingCounts[i];
            }

            if (borrowingCounts[i] > 0
                    && borrowingCounts[i] < leastBorrows) {
                leastBorrows = borrowingCounts[i];
            }
        }

        // type casting para sa percentage pwede rin dito double
        double borrowedPercentage =
                ((double) borrowedBooks / totalBooks) * 100;

        // Display total statistics
        System.out.println("\n--- GENERAL STATISTICS ---");
        System.out.println("Total Number of Books: " + totalBooks);
        System.out.println(
                "Total Borrowing Transactions: " + totalTransactions
        );
        System.out.println("Currently Borrowed Books: " + borrowedBooks);
        System.out.println(
                "Currently Available Books: " + (totalBooks - borrowedBooks)
        );
        System.out.printf(
                "Percentage of Books Currently Borrowed: %.2f%%%n",
                borrowedPercentage
        );

        // Identify most borrowed book
        System.out.println("\n--- MOST-BORROWED BOOK ---");

        if (totalTransactions == 0) {
            System.out.println("No borrowing transactions recorded yet.");
        } else {
            for (int i = 0; i < totalBooks; i++) {
                if (borrowingCounts[i] == mostBorrows) {
                    System.out.println(
                            titles[i] + " - " + mostBorrows + " borrow(s)"
                    );
                }
            }
        }

        // Identify least borrowed and never borrowed books
        System.out.println("\n--- LEAST-BORROWED BOOKS ---");

        if (leastBorrows == Integer.MAX_VALUE) {
            System.out.println("No books have been borrowed yet.");
        } else {
            for (int i = 0; i < totalBooks; i++) {
                if (borrowingCounts[i] == leastBorrows) {
                    System.out.println(
                            titles[i] + " - " + leastBorrows + " borrow(s)"
                    );
                }
            }
        }

        System.out.println("\n--- NEVER-BORROWED BOOKS ---");

        boolean neverBorrowedFound = false;

        for (int i = 0; i < totalBooks; i++) {
            if (borrowingCounts[i] == 0) {
                System.out.println(
                        bookIDs[i] + " - " + titles[i]
                );
                neverBorrowedFound = true;
            }
        }

        if (!neverBorrowedFound) {
            System.out.println("All books have been borrowed at least once.");
        }

        // borrowing activity by category
        System.out.println("\n--- BORROWING ACTIVITY BY CATEGORY ---");

        String[] uniqueCategories = new String[categories.length];
        int[] categoryBorrowCounts = new int[categories.length];
        int categoryCount = 0;

        // Cunique categories
        for (int i = 0; i < categories.length; i++) {

            boolean categoryExists = false;

            for (int j = 0; j < categoryCount; j++) {
                if (uniqueCategories[j].equals(categories[i])) {
                    categoryExists = true;
                    break;
                }
            }

            if (!categoryExists) {
                uniqueCategories[categoryCount] = categories[i];
                categoryCount++;
            }
        }

        // Count transactions for categories
        int mostPopularCount = 0;

        for (int i = 0; i < categoryCount; i++) {

            for (int j = 0; j < categories.length; j++) {

                if (uniqueCategories[i].equals(categories[j])) {
                    categoryBorrowCounts[i] += borrowingCounts[j];
                }
            }

            System.out.println(
                    uniqueCategories[i] + ": "
                            + categoryBorrowCounts[i] + " borrowing transaction(s)"
            );

            if (categoryBorrowCounts[i] > mostPopularCount) {
                mostPopularCount = categoryBorrowCounts[i];
            }
        }

        // most popular category
        System.out.println("\n--- MOST POPULAR CATEGORY ---");

        if (totalTransactions == 0) {
            System.out.println("No borrowing activity recorded yet.");
        } else {
            for (int i = 0; i < categoryCount; i++) {

                if (categoryBorrowCounts[i] == mostPopularCount) ;
            }
            ;

        }
    }
}