import java.util.Scanner;
public class HumanAgent extends Agent{

    public HumanAgent(int id){
        super(id);
    }

    @Override
    public boolean yesNoMove(Scanner scanner){
        while(true){
            System.out.print("Do you want to build something? (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")){
                return true;
            }
            else if (input.equals("no")){
                return false;
            }
            System.out.println("Invalid input. Please type yes or no.");
        }
    }

    @Override
    public int chooseOption(Scanner scanner) {
        while (true) {
            System.out.println("Choose what to build:");
            System.out.println("1: Road, 2: Settlement, 3: City, 4: Nothing");
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Enter a number between 1 and 4.");
            }
        }
    }
}
