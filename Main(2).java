import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Movie titles
        String[] movies = {
            "Interstellar",
            "Inception",
            "The Martian",
            "Gladiator",
            "Jurassic Park"
        };

        // Ratings (row = movie)
        int[][] ratings = {
            {9, 8, 10, 7, 9},
            {8, 9, 9, 8, 10},
            {7, 8, 9, 8, 7},
            {9, 10, 9, 8, 9},
            {7, 6, 8, 7, 8}
        };

        // Rating scale
        int scale = 10;

        // Results per movie
        double[] averages = new double[movies.length];
        String[] classes = new String[movies.length];

        // Overall results
        int totalReviews = 0;
        int totalPoints = 0;
        int best = 0;
        int worst = 0;
        int outstanding = 0;
        int excellent = 0;
        int good = 0;
        int average = 0;
        int poor = 0;

        // Calculate everything once
        for (int m = 0; m < movies.length; m++) {
            // Add ratings
            int total = 0;
            for (int r = 0; r < ratings[m].length; r++) {
                total = total + ratings[m][r];
            }

            // Divide as double
            averages[m] = (double) total / ratings[m].length;

            // Overall totals
            totalPoints = totalPoints + total;
            totalReviews = totalReviews + ratings[m].length;

            // Best and worst
            if (averages[m] > averages[best]) {
                best = m;
            }
            if (averages[m] < averages[worst]) {
                worst = m;
            }

            // Classify and count
            if (averages[m] >= 9.0) {
                classes[m] = "Outstanding";
                outstanding++;
            } else if (averages[m] >= 8.0) {
                classes[m] = "Excellent";
                excellent++;
            } else if (averages[m] >= 7.0) {
                classes[m] = "Good";
                good++;
            } else if (averages[m] >= 5.0) {
                classes[m] = "Average";
                average++;
            } else {
                classes[m] = "Poor";
                poor++;
            }
        }

        double overallAvg = (double) totalPoints / totalReviews;

        boolean running = true;

        // Menu loop
        while (running) {
            System.out.println();
            System.out.println("--Movie Review Analytics--");
            System.out.println("1. View All Movies");
            System.out.println("2. View Movie Reviews");
            System.out.println("3. Search for a Movie");
            System.out.println("4. View Movie Statistics");
            System.out.println("5. View Overall Analytics");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            // Read choice
            int choice = 0;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            }
            sc.nextLine();

            switch (choice) {

                case 1:
                    // Show movies
                    System.out.println();
                    System.out.println("--Available Movies--");
                    for (int m = 0; m < movies.length; m++) {
                        System.out.println((m + 1) + ". " + movies[m]);
                    }
                    break;

                case 2: {
                    // Show movies
                    System.out.println();
                    System.out.println("--Available Movies--");
                    for (int m = 0; m < movies.length; m++) {
                        System.out.println((m + 1) + ". " + movies[m]);
                    }

                    // Read pick
                    System.out.print("Select a movie: ");
                    int pick = 0;
                    if (sc.hasNextInt()) {
                        pick = sc.nextInt();
                    }
                    sc.nextLine();

                    // Check pick
                    if (pick < 1 || pick > movies.length) {
                        System.out.println("Invalid movie selection.");
                        break;
                    }

                    // Show reviews
                    int m = pick - 1;
                    System.out.println();
                    System.out.println("Movie: " + movies[m]);
                    System.out.println();
                    System.out.println("Reviews:");
                    for (int r = 0; r < ratings[m].length; r++) {
                        System.out.println("Reviewer " + (r + 1) + ": " + ratings[m][r] + "/" + scale);
                    }
                    break;
                }

                case 3: {
                    // Read title
                    System.out.print("Enter movie title: ");
                    String title = sc.nextLine().trim();

                    // Search movies
                    boolean found = false;
                    for (int m = 0; m < movies.length; m++) {
                        if (movies[m].equalsIgnoreCase(title)) {
                            System.out.println();
                            System.out.println("Movie Found!");
                            System.out.println();
                            System.out.println("Title: " + movies[m]);
                            System.out.printf("Average Rating: %.1f/%d%n", averages[m], scale);
                            System.out.println("Classification: " + classes[m]);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Movie not found.");
                    }
                    break;
                }

                case 4: {
                    // Show movies
                    System.out.println();
                    System.out.println("--Available Movies--");
                    for (int m = 0; m < movies.length; m++) {
                        System.out.println((m + 1) + ". " + movies[m]);
                    }

                    // Read pick
                    System.out.print("Select a movie: ");
                    int pick = 0;
                    if (sc.hasNextInt()) {
                        pick = sc.nextInt();
                    }
                    sc.nextLine();

                    // Check pick
                    if (pick < 1 || pick > movies.length) {
                        System.out.println("Invalid movie selection.");
                        break;
                    }
                    int m = pick - 1;

                    // Highest and lowest
                    int high = ratings[m][0];
                    int low = ratings[m][0];
                    for (int r = 1; r < ratings[m].length; r++) {
                        if (ratings[m][r] > high) {
                            high = ratings[m][r];
                        }
                        if (ratings[m][r] < low) {
                            low = ratings[m][r];
                        }
                    }

                    // Show report
                    System.out.println();
                    System.out.println("--Movie Report--");
                    System.out.println("Movie: " + movies[m]);
                    System.out.print("Ratings: ");
                    for (int r = 0; r < ratings[m].length; r++) {
                        System.out.print(ratings[m][r] + "  ");
                    }
                    System.out.println();
                    System.out.println("Number of Reviews: " + ratings[m].length);
                    System.out.println("Highest Rating: " + high + "/" + scale);
                    System.out.println("Lowest Rating: " + low + "/" + scale);
                    System.out.printf("Average Rating: %.1f/%d%n", averages[m], scale);
                    System.out.println("Classification: " + classes[m]);
                    break;
                }

                case 5:
                    // Show analytics
                    System.out.println();
                    System.out.println("--Overall Analytics--");
                    System.out.println("Number of Movies: " + movies.length);
                    System.out.println("Number of Reviews: " + totalReviews);

                    System.out.println();
                    System.out.println("Highest Rated Movie:");
                    System.out.printf("%s - %.1f/%d%n", movies[best], averages[best], scale);

                    System.out.println();
                    System.out.println("Lowest Rated Movie:");
                    System.out.printf("%s - %.1f/%d%n", movies[worst], averages[worst], scale);

                    System.out.println();
                    System.out.printf("Overall Average Rating: %.1f/%d%n", overallAvg, scale);

                    System.out.println();
                    System.out.println("Movies by Classification:");
                    System.out.println("Outstanding: " + outstanding);
                    System.out.println("Excellent: " + excellent);
                    System.out.println("Good: " + good);
                    System.out.println("Average: " + average);
                    System.out.println("Poor: " + poor);
                    break;

                case 6:
                    running = false;
                    System.out.println("Thank you for using Movie Review Analytics!");
                    break;

                default:
                    System.out.println("Invalid option.");
                    System.out.println("Please select from 1 to 6.");
            }
        }

        sc.close();
    }
}
