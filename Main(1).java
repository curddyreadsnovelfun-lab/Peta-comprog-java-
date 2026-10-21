import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Team data
        int teamCount = 0;
        int totalGames = 0;
        String[] teamNames = new String[0];
        int[] wins = new int[0];
        int[] losses = new int[0];
        int[] pointsScored = new int[0];
        int[] pointsAllowed = new int[0];

        boolean running = true;

        // Menu loop
        while (running) {

            // Find leaders
            int maxWins = 0;
            for (int i = 0; i < teamCount; i++) {
                if (wins[i] > maxWins) {
                    maxWins = wins[i];
                }
            }

            int leaderCount = 0;
            int leaderIndex = 0;
            String leaders = "";
            for (int i = 0; i < teamCount; i++) {
                if (wins[i] == maxWins) {
                    if (leaderCount > 0) {
                        leaders = leaders + ", ";
                    }
                    leaders = leaders + teamNames[i];
                    leaderIndex = i;
                    leaderCount++;
                }
            }

            // Show menu
            System.out.println();
            System.out.println("--Basketball League Statistics--");
            System.out.println("1. Register Teams");
            System.out.println("2. Record Game Result");
            System.out.println("3. View Team Statistics");
            System.out.println("4. View League Standings");
            System.out.println("5. View League Summary");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int choice = sc.nextInt();

            // Need teams
            if (choice >= 2 && choice <= 5 && teamCount == 0) {
                System.out.println("Register teams first.");
                continue;
            }

            // Need games
            if (choice >= 4 && choice <= 5 && totalGames == 0) {
                System.out.println("Record a game first.");
                continue;
            }

            switch (choice) {

                case 1: {
                    // Already done
                    if (teamCount > 0) {
                        System.out.println("Teams already registered.");
                        break;
                    }

                    // Team count
                    int num = 0;
                    while (num < 4) {
                        System.out.print("How many teams will participate? (min 4): ");
                        num = sc.nextInt();
                        if (num < 4) {
                            System.out.println("Enter at least 4.");
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
                        System.out.print("Enter Team " + (i + 1) + ": ");
                        teamNames[i] = sc.nextLine();
                    }
                    System.out.println("Teams registered!");
                    break;
                }

                case 2: {
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
                        homePick = sc.nextInt();
                        if (homePick < 1 || homePick > teamCount) {
                            System.out.println("Invalid team number.");
                        }
                    }

                    // Away team
                    int awayPick = 0;
                    while (awayPick < 1 || awayPick > teamCount || awayPick == homePick) {
                        System.out.print("Select Away Team: ");
                        awayPick = sc.nextInt();
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
                        homeScore = sc.nextInt();
                        System.out.print(teamNames[away] + " Score: ");
                        awayScore = sc.nextInt();

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
                    totalGames++;
                    System.out.println("Game successfully recorded.");
                    break;
                }

                case 3: {
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
                        pick = sc.nextInt();
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
                    int diff = pointsScored[t] - pointsAllowed[t];

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
                    System.out.printf("Point Difference: %+d%n", diff);
                    System.out.printf("Win Percentage: %.2f%%%n", pct);
                    break;
                }

                case 4: {
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

                            // Wins, then diff
                            if (wins[b] > wins[a] || (wins[b] == wins[a] && diffB > diffA)) {
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
                        System.out.printf("%-16s%5d%5d%5d%7d%7d%+7d%n",
                                teamNames[t], gp, wins[t], losses[t],
                                pointsScored[t], pointsAllowed[t], diff);
                    }

                    // Show leader
                    System.out.println();
                    if (leaderCount == 1) {
                        System.out.println("Current League Leader: " + leaders);
                        System.out.println("Record: " + maxWins + "W - " + losses[leaderIndex] + "L");
                    } else {
                        System.out.println("League Leaders (tied):");
                        for (int i = 0; i < teamCount; i++) {
                            if (wins[i] == maxWins) {
                                System.out.println(teamNames[i] + " - " + maxWins + "W");
                            }
                        }
                    }
                    break;
                }

                case 5: {
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
                        System.out.println("League Leader: " + leaders);
                    } else {
                        System.out.println("League Leaders (tied): " + leaders);
                    }
                    break;
                }

                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Enter 1-6.");
            }
        }

        sc.close();
    }
}
