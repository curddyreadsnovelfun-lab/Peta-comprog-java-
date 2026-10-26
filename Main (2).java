import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int teamCount = 0;
        int totalGames = 0;
        String[] teamNames = new String[0];
        int[] wins = new int[0];
        int[] losses = new int[0];
        int[] pointsScored = new int[0];
        int[] pointsAllowed = new int[0];

        boolean running = true;

        while (running) {
            // Find current leaders dynamically
            int maxWins = 0;
            for (int i = 0; i < teamCount; i++) {
                if (wins[i] > maxWins) maxWins = wins[i];
            }

            int leaderCount = 0;
            int leaderIndex = 0;
            String leaders = "";
            for (int i = 0; i < teamCount; i++) {
                if (wins[i] == maxWins) {
                    if (leaderCount > 0) leaders += ", ";
                    leaders += teamNames[i];
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

            // Validation guards
            if (choice >= 2 && choice <= 5 && teamCount == 0) {
                System.out.println("Register teams first.");
                continue;
            }
            if (choice >= 4 && choice <= 5 && totalGames == 0) {
                System.out.println("Record a game first.");
                continue;
            }

            switch (choice) {
                case 1: {
                    if (teamCount > 0) {
                        System.out.println("Teams already registered.");
                        break;
                    }

                    int num = 0;
                    while (num < 4) {
                        System.out.print("How many teams will participate? (min 4): ");
                        num = sc.nextInt();
                        if (num < 4) System.out.println("Enter at least 4.");
                    }
                    sc.nextLine();

                    teamCount = num;
                    teamNames = new String[num];
                    wins = new int[num];
                    losses = new int[num];
                    pointsScored = new int[num];
                    pointsAllowed = new int[num];

                    for (int i = 0; i < teamCount; i++) {
                        System.out.print("Enter Team " + (i + 1) + ": ");
                        teamNames[i] = sc.nextLine();
                    }
                    System.out.println("Teams registered!");
                    break;
                }

                case 2: {
                    printTeams(teamNames, teamCount);

                    int homePick = selectTeam(sc, teamCount, "Select Home Team: ");
                    
                    int awayPick = 0;
                    while (true) {
                        awayPick = selectTeam(sc, teamCount, "Select Away Team: ");
                        if (awayPick == homePick) {
                            System.out.println("Invalid matchup.");
                            System.out.println("A team cannot play against itself.");
                        } else {
                            break;
                        }
                    }

                    int home = homePick - 1;
                    int away = awayPick - 1;

                    int homeScore = -1, awayScore = -1;
                    while (true) {
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
                            break;
                        }
                    }

                    pointsScored[home] += homeScore;
                    pointsAllowed[home] += awayScore;
                    pointsScored[away] += awayScore;
                    pointsAllowed[away] += homeScore;

                    if (homeScore > awayScore) {
                        wins[home]++;
                        losses[away]++;
                        System.out.println(teamNames[home] + " defeated " + teamNames[away] + ", " + homeScore + "-" + awayScore + ".");
                    } else {
                        wins[away]++;
                        losses[home]++;
                        System.out.println(teamNames[away] + " defeated " + teamNames[home] + ", " + awayScore + "-" + homeScore + ".");
                    }
                    totalGames++;
                    System.out.println("Game successfully recorded.");
                    break;
                }

                case 3: {
                    printTeams(teamNames, teamCount);
                    int t = selectTeam(sc, teamCount, "Select Team: ") - 1;

                    int gp = wins[t] + losses[t];
                    double avg = gp > 0 ? (double) pointsScored[t] / gp : 0.0;
                    double pct = gp > 0 ? (double) wins[t] / gp * 100 : 0.0;
                    int diff = pointsScored[t] - pointsAllowed[t];

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
                    int[] order = new int[teamCount];
                    for (int i = 0; i < teamCount; i++) order[i] = i;

                    for (int i = 0; i < teamCount - 1; i++) {
                        for (int j = 0; j < teamCount - 1 - i; j++) {
                            int a = order[j], b = order[j + 1];
                            int diffA = pointsScored[a] - pointsAllowed[a];
                            int diffB = pointsScored[b] - pointsAllowed[b];

                            if (wins[b] > wins[a] || (wins[b] == wins[a] && diffB > diffA)) {
                                order[j] = b;
                                order[j + 1] = a;
                            }
                        }
                    }

                    System.out.println();
                    System.out.println("--League Standings--");
                    System.out.printf("%-16s%5s%5s%5s%7s%7s%7s%n", "Team", "GP", "W", "L", "PF", "PA", "DIFF");
                    for (int i = 0; i < teamCount; i++) {
                        int t = order[i];
                        int gp = wins[t] + losses[t];
                        int diff = pointsScored[t] - pointsAllowed[t];
                        System.out.printf("%-16s%5d%5d%5d%7d%7d%+7d%n", teamNames[t], gp, wins[t], losses[t], pointsScored[t], pointsAllowed[t], diff);
                    }

                    System.out.println();
                    if (leaderCount == 1) {
                        System.out.println("Current League Leader: " + leaders);
                        System.out.println("Record: " + maxWins + "W - " + losses[leaderIndex] + "L");
                    } else {
                        System.out.println("League Leaders (tied):");
                        for (int i = 0; i < teamCount; i++) {
                            if (wins[i] == maxWins) System.out.println(teamNames[i] + " - " + maxWins + "W");
                        }
                    }
                    break;
                }

                case 5: {
                    int totalPoints = 0, bestOffense = 0, bestDefense = -1;
                    for (int i = 0; i < teamCount; i++) {
                        totalPoints += pointsScored[i];
                        if (pointsScored[i] > pointsScored[bestOffense]) bestOffense = i;

                        if (wins[i] + losses[i] == 0) continue;
                        if (bestDefense == -1 || pointsAllowed[i] < pointsAllowed[bestDefense]) bestDefense = i;
                    }

                    System.out.println();
                    System.out.println("--League Summary--");
                    System.out.println("Number of Teams: " + teamCount);
                    System.out.println("Games Recorded: " + totalGames);
                    System.out.printf("Total Points Scored: %,d%n", totalPoints);
                    System.out.println("Highest Scoring Team: " + teamNames[bestOffense]);
                    System.out.println("Points Scored: " + pointsScored[bestOffense]);
                    System.out.println("Best Defensive Team: " + teamNames[bestDefense]);
                    System.out.println("Points Allowed: " + pointsAllowed[bestDefense]);
                    System.out.println((leaderCount == 1 ? "League Leader: " : "League Leaders (tied): ") + leaders);
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

    static void printTeams(String[] teamNames, int teamCount) {
        System.out.println();
        System.out.println("--Teams--");
        for (int i = 0; i < teamCount; i++) {
            System.out.println((i + 1) + ". " + teamNames[i]);
        }
    }

    static int selectTeam(Scanner sc, int teamCount, String prompt) {
        int pick = 0;
        while (pick < 1 || pick > teamCount) {
            System.out.print(prompt);
            pick = sc.nextInt();
            if (pick < 1 || pick > teamCount) {
                System.out.println("Invalid team number.");
            }
        }
        return pick;
    }
}