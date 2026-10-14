import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Team data
        int teamCount = 0;
        String[] teamNames = new String[0];
        int[] wins = new int[0];
        int[] losses = new int[0];
        int[] pointsScored = new int[0];
        int[] pointsAllowed = new int[0];

        boolean running = true;

        // Menu loop
        while (running) {

            // Total games
            int totalGames = 0;
            int maxWins = 0;
            for (int i = 0; i < teamCount; i++) {
                totalGames = totalGames + wins[i];
                if (wins[i] > maxWins) {
                    maxWins = wins[i];
                }
            }

            // Find leaders
            int leaderCount = 0;
            int leaderIndex = 0;
            String leaderText = "";
            for (int i = 0; i < teamCount; i++) {
                if (wins[i] == maxWins) {
                    if (leaderCount > 0) {
                        leaderText = leaderText + ", ";
                    }
                    leaderText = leaderText + teamNames[i];
                    leaderIndex = i;
                    leaderCount++;
                }
            }

            // Build menu
            String[] options = new String[6];
            int optionCount = 0;
            if (teamCount == 0) {
                options[optionCount] = "Register Teams";
                optionCount++;
            } else {
                options[optionCount] = "Record Game Result";
                optionCount++;
                if (totalGames > 0) {
                    options[optionCount] = "View Team Statistics";
                    optionCount++;
                    options[optionCount] = "View League Standings";
                    optionCount++;
                    options[optionCount] = "View League Summary";
                    optionCount++;
                }
                options[optionCount] = "Start Over";
                optionCount++;
            }
            options[optionCount] = "Exit";
            optionCount++;

            // Show menu
            System.out.println();
            System.out.println("--Basketball League Statistics--");
            for (int i = 0; i < optionCount; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            if (teamCount > 0 && totalGames == 0) {
                System.out.println("(Record a game to unlock more options.)");
            }
            System.out.print("Select an option: ");

            // Read choice
            int choice = 0;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else {
                sc.next();
            }
            sc.nextLine();

            // Match choice
            String picked = "";
            if (choice >= 1 && choice <= optionCount) {
                picked = options[choice - 1];
            }

            switch (picked) {

                case "Register Teams": {
                    // Team count
                    int num = 0;
                    while (num < 4 || num > 10) {
                        System.out.print("How many teams will participate? (4-10): ");
                        if (sc.hasNextInt()) {
                            num = sc.nextInt();
                        } else {
                            sc.next();
                        }
                        if (num < 4 || num > 10) {
                            System.out.println("Enter a number from 4 to 10.");
                        }
                    }
                    sc.nextLine();

                    // New arrays
                    teamCount = num;
                    teamNames = new String[num];
                    wins = new int[num];
                    losses = new int[num];
                    pointsScored = new int[num];
                    pointsAllowed = new int[num];

                    // Team names
                    for (int i = 0; i < teamCount; i++) {
                        String name = "";
                        boolean validName = false;
                        while (!validName) {
                            System.out.print("Enter Team " + (i + 1) + ": ");
                            name = sc.nextLine().trim();
                            validName = true;

                            if (name.length() == 0) {
                                System.out.println("Name cannot be empty.");
                                validName = false;
                            } else if (name.length() > 15) {
                                System.out.println("Max 15 characters.");
                                validName = false;
                            } else {
                                // Check duplicate
                                for (int j = 0; j < i; j++) {
                                    if (teamNames[j].equalsIgnoreCase(name)) {
                                        System.out.println("Name already used.");
                                        validName = false;
                                        break;
                                    }
                                }
                            }
                        }
                        teamNames[i] = name;
                    }
                    System.out.println("Teams registered!");
                    break;
                }

                case "Record Game Result": {
                    // Show teams
                    System.out.println();
                    System.out.println("--Teams--");
                    for (int i = 0; i < teamCount; i++) {
                        System.out.println((i + 1) + ". " + teamNames[i]);
                    }

                    // Home team
                    int homePick = 0;
                    while (homePick < 1 || homePick > teamCount) {
                        System.out.print("Select Home Team: ");
                        if (sc.hasNextInt()) {
                            homePick = sc.nextInt();
                        } else {
                            sc.next();
                            homePick = 0;
                        }
                        if (homePick < 1 || homePick > teamCount) {
                            System.out.println("Invalid team number.");
                        }
                    }

                    // Away team
                    int awayPick = 0;
                    while (awayPick < 1 || awayPick > teamCount || awayPick == homePick) {
                        System.out.print("Select Away Team: ");
                        if (sc.hasNextInt()) {
                            awayPick = sc.nextInt();
                        } else {
                            sc.next();
                            awayPick = 0;
                        }
                        if (awayPick == homePick) {
                            System.out.println("Invalid matchup.");
                            System.out.println("A team cannot play against itself.");
                        } else if (awayPick < 1 || awayPick > teamCount) {
                            System.out.println("Invalid team number.");
                        }
                    }

                    // Array positions
                    int home = homePick - 1;
                    int away = awayPick - 1;

                    // Read scores
                    int homeScore = 0;
                    int awayScore = 0;
                    boolean validScore = false;
                    while (!validScore) {
                        System.out.print(teamNames[home] + " Score: ");
                        while (!sc.hasNextInt()) {
                            sc.next();
                            System.out.print("Numbers only. " + teamNames[home] + " Score: ");
                        }
                        homeScore = sc.nextInt();

                        System.out.print(teamNames[away] + " Score: ");
                        while (!sc.hasNextInt()) {
                            sc.next();
                            System.out.print("Numbers only. " + teamNames[away] + " Score: ");
                        }
                        awayScore = sc.nextInt();
                        sc.nextLine();

                        // Check scores
                        if (homeScore < 0 || awayScore < 0) {
                            System.out.println("Invalid score. Scores cannot be negative.");
                        } else if (homeScore == awayScore) {
                            System.out.println("Invalid final score.");
                            System.out.println("Basketball games cannot end in a tie.");
                            System.out.println("Please enter the final score after overtime.");
                        } else {
                            validScore = true;
                        }
                    }

                    // Update points
                    pointsScored[home] = pointsScored[home] + homeScore;
                    pointsAllowed[home] = pointsAllowed[home] + awayScore;
                    pointsScored[away] = pointsScored[away] + awayScore;
                    pointsAllowed[away] = pointsAllowed[away] + homeScore;

                    // Find winner
                    if (homeScore > awayScore) {
                        wins[home]++;
                        losses[away]++;
                        System.out.println(teamNames[home] + " defeated " + teamNames[away]
                                + ", " + homeScore + "-" + awayScore + ".");
                    } else {
                        wins[away]++;
                        losses[home]++;
                        System.out.println(teamNames[away] + " defeated " + teamNames[home]
                                + ", " + awayScore + "-" + homeScore + ".");
                    }
                    System.out.println("Game successfully recorded.");
                    break;
                }

                case "View Team Statistics": {
                    // Show teams
                    System.out.println();
                    System.out.println("--Teams--");
                    for (int i = 0; i < teamCount; i++) {
                        System.out.println((i + 1) + ". " + teamNames[i]);
                    }

                    // Pick team
                    int pick = 0;
                    while (pick < 1 || pick > teamCount) {
                        System.out.print("Select Team: ");
                        if (sc.hasNextInt()) {
                            pick = sc.nextInt();
                        } else {
                            sc.next();
                            pick = 0;
                        }
                        if (pick < 1 || pick > teamCount) {
                            System.out.println("Invalid team number.");
                        }
                    }
                    int t = pick - 1;

                    // Calculations
                    int gp = wins[t] + losses[t];
                    double avg = 0.0;
                    double pct = 0.0;
                    if (gp > 0) {
                        avg = (double) pointsScored[t] / gp;
                        pct = (double) wins[t] / gp * 100;
                    }

                    // Point difference
                    int diff = pointsScored[t] - pointsAllowed[t];
                    String diffText = "" + diff;
                    if (diff > 0) {
                        diffText = "+" + diff;
                    }

                    // Show stats
                    System.out.println();
                    System.out.println("--Team Statistics--");
                    System.out.println("Team: " + teamNames[t]);
                    System.out.println("Games Played: " + gp);
                    System.out.println("Wins: " + wins[t]);
                    System.out.println("Losses: " + losses[t]);
                    System.out.println("Points Scored: " + pointsScored[t]);
                    System.out.println("Points Allowed: " + pointsAllowed[t]);
                    System.out.printf("Average Points: %.2f%n", avg);
                    System.out.println("Point Difference: " + diffText);
                    System.out.printf("Win Percentage: %.2f%%%n", pct);
                    break;
                }

                case "View League Standings": {
                    // Rank order
                    int[] order = new int[teamCount];
                    for (int i = 0; i < teamCount; i++) {
                        order[i] = i;
                    }

                    // Bubble sort
                    for (int i = 0; i < teamCount - 1; i++) {
                        for (int j = 0; j < teamCount - 1 - i; j++) {
                            int a = order[j];
                            int b = order[j + 1];
                            int diffA = pointsScored[a] - pointsAllowed[a];
                            int diffB = pointsScored[b] - pointsAllowed[b];

                            boolean swap = false;
                            if (wins[b] > wins[a]) {
                                swap = true;
                            } else if (wins[b] == wins[a] && diffB > diffA) {
                                swap = true;
                            }

                            if (swap) {
                                order[j] = b;
                                order[j + 1] = a;
                            }
                        }
                    }

                    // Print table
                    System.out.println();
                    System.out.println("--League Standings--");
                    System.out.printf("%-16s%5s%5s%5s%7s%7s%7s%n",
                            "Team", "GP", "W", "L", "PF", "PA", "DIFF");
                    for (int i = 0; i < teamCount; i++) {
                        int t = order[i];
                        int gp = wins[t] + losses[t];
                        int diff = pointsScored[t] - pointsAllowed[t];
                        String diffText = "" + diff;
                        if (diff > 0) {
                            diffText = "+" + diff;
                        }
                        System.out.printf("%-16s%5d%5d%5d%7d%7d%7s%n",
                                teamNames[t], gp, wins[t], losses[t],
                                pointsScored[t], pointsAllowed[t], diffText);
                    }

                    // Show leader
                    System.out.println();
                    String winWord = "Wins";
                    if (maxWins == 1) {
                        winWord = "Win";
                    }

                    if (leaderCount == 1) {
                        int leaderLosses = losses[leaderIndex];
                        String lossWord = "Losses";
                        if (leaderLosses == 1) {
                            lossWord = "Loss";
                        }
                        System.out.println("Current League Leader: " + leaderText);
                        System.out.println("Record: " + maxWins + " " + winWord
                                + " - " + leaderLosses + " " + lossWord);
                    } else {
                        // Tie for first
                        System.out.println("League Leaders (tied):");
                        for (int i = 0; i < teamCount; i++) {
                            if (wins[i] == maxWins) {
                                System.out.println(teamNames[i] + " - " + maxWins + " " + winWord);
                            }
                        }
                    }
                    break;
                }

                case "View League Summary": {
                    // Totals and bests
                    int totalPoints = 0;
                    int bestOffense = 0;
                    int bestDefense = -1;
                    for (int i = 0; i < teamCount; i++) {
                        totalPoints = totalPoints + pointsScored[i];

                        if (pointsScored[i] > pointsScored[bestOffense]) {
                            bestOffense = i;
                        }

                        // Skip unplayed
                        if (wins[i] + losses[i] == 0) {
                            continue;
                        }
                        if (bestDefense == -1 || pointsAllowed[i] < pointsAllowed[bestDefense]) {
                            bestDefense = i;
                        }
                    }

                    // Show summary
                    System.out.println();
                    System.out.println("--League Summary--");
                    System.out.println("Number of Teams: " + teamCount);
                    System.out.println("Games Recorded: " + totalGames);
                    System.out.printf("Total Points Scored: %,d%n", totalPoints);
                    System.out.println("Highest Scoring Team: " + teamNames[bestOffense]);
                    System.out.println("Points Scored: " + pointsScored[bestOffense]);
                    System.out.println("Best Defensive Team: " + teamNames[bestDefense]);
                    System.out.println("Points Allowed: " + pointsAllowed[bestDefense]);
                    if (leaderCount == 1) {
                        System.out.println("League Leader: " + leaderText);
                    } else {
                        System.out.println("League Leaders (tied): " + leaderText);
                    }
                    break;
                }

                case "Start Over": {
                    System.out.print("This erases everything. Continue? (y/n): ");
                    String answer = sc.nextLine().trim();
                    if (answer.equalsIgnoreCase("y")) {
                        // Reset data
                        teamCount = 0;
                        teamNames = new String[0];
                        wins = new int[0];
                        losses = new int[0];
                        pointsScored = new int[0];
                        pointsAllowed = new int[0];
                        System.out.println("All data erased.");
                    } else {
                        System.out.println("Cancelled.");
                    }
                    break;
                }

                case "Exit":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Enter 1-" + optionCount + ".");
            }
        }

        sc.close();
    }
}
