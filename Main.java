import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String[][] MENUS = { {
            // Main Menu
            "Store to ASEAN phonebook", "Edit entry in ASEAN phonebook", "Delete entry from ASEAN phonebook",
            "View Phonebook", "Exit" },
            {
                    // Edit Contact Menu
                    "Student Number", "First Name", "Last Name", "Occupation", "Country Code",
                    "Area Code", "Phone Number", "None - Go back to Main Menu" },
            {
                    // Menu for View Phonebook
                    "View Contact through ID", "View Contacts through Country Code",
                    "Go back to Main Menu" }, };
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Phonebook pb = new Phonebook();
        pb.insert(new Person("1", "Neil John", "Jomaya", "Male", "IT", "1232", 63, 122));
        pb.insert(new Person("2", "Charles Hanz", "Bautista", "Male", "IT", "12323", 62, 2323));
        while (true) {
            showMenu(1, 1);
            // System.out.print("Select an option: ");
            // int opt = input.nextInt();
            int opt = Integer.parseInt(prompt("Select an option: "));
            switch case 1:
                    while (true) {
                        pb.insert(createNewPerson()); // Insert new contact

                        // Ask user if they want to add another entry
                        String choice = prompt("Do you want to enter another entry? [Y/N]: ").trim().toUpperCase();

                        // Exit the loop if user enters anything other than 'Y'
                        if (!choice.equals("Y")) {
                            break;
                        }
                    }
                    break;
                case 2:
                    String studentNumber = prompt("Enter student number: ");
                    Person personToEdit = pb.getContact(studentNumber);

                    if (personToEdit != null) {
                        System.out.println("Here is the existing information about " + studentNumber + ":");
                        System.out.println(personToEdit);

                        boolean continueEditing = true;
                        while (continueEditing) {
                            System.out.println("\nWhich information do you wish to change?");
                            System.out.println("[1] Student Number   [2] Surname   [3] Gender   [4] Occupation");
                            System.out.println("[5] Country Code   [6] Area Code   [7] Phone Number");
                            System.out.println("[8] None - Go back to main menu");

                            int choice;
                            choice = Integer.parseInt(prompt("Enter choice: "));

                            switch (choice) {
                                case 1:
                                    personToEdit.setId(prompt("Enter new student number: "));
                                    break;
                                case 2:
                                    personToEdit.setLName(prompt("Enter new surname: "));
                                    break;
                                case 3:
                                    personToEdit.setSex(prompt("Enter new gender: "));
                                    break;
                                case 4:
                                    personToEdit.setOccupation(prompt("Enter new occupation: "));
                                    break;
                                case 5:
                                    personToEdit.setCountryCode(Integer.parseInt(prompt("Enter new country code: ")));
                                    break;
                                case 6:
                                    personToEdit.setAreaCode(Integer.parseInt(prompt("Enter new area code: ")));
                                    break;
                                case 7:
                                    personToEdit.setContactNum(prompt("Enter new phone number: "));
                                    break;
                                case 8:
                                    continueEditing = false;
                                    System.out.println("Returning to the main menu...");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    continue;
                            }

                            // Show updated info after every edit (except when exiting)
                            if (continueEditing) {
                                System.out.println("\nUpdated information:");
                                System.out.println(personToEdit);
                            }
                        }
                    } else {
                        System.out.println("Error: No contact found with student number " + studentNumber);
                    }
                    break;
                case 3:
                    String id = prompt("Enter contact ID to delete: ");
                    Person p = pb.getContact(id);
                    if (p != null) {
                        Person deletedContact = pb.deleteContact(id);
                        if (deletedContact != null) {
                            System.out.println("Contact has been successfully deleted!");
                        }
                    } else {
                        System.out.println("This contact does not exist!");
                    }
                    break;
                case 4:
                    while (true) {
                        // Display the search menu
                        System.out.println("\nSEARCH MENU:");
                        System.out.println("[1] Search by Country");
                        System.out.println("[2] Search by Surname");
                        System.out.println("[3] Go back to Main Menu");

                        int showOpt = Integer.parseInt(prompt("Enter option: "));

                        if (showOpt == 1) {
                            // Search by Country
                            int[] countryCodes = new int[12];
                            int ccCount = 0;

                            while (true) {
                                System.out.println("\nFrom which country:");
                                System.out.println(
                                        "[1] Burma   [2] Cambodia   [3] Thailand   [4] Vietnam   [5] Malaysia");
                                System.out.println(
                                        "[6] Philippines   [7] Indonesia   [8] Timor Leste   [9] Laos   [10] Brunei");
                                System.out.println("[11] Singapore   [12] ALL   [0] No More");

                                int countryCode = Integer.parseInt(prompt("Enter Country Code: "));

                                if (countryCode == 0) {
                                    break; // Stop selecting countries
                                } else if (countryCode < 0 || countryCode > 12) {
                                    System.out.println("Invalid choice! Please select a valid number.");
                                    continue;
                                }
                                if (countryCode == 12) { // If "ALL" is selected
                                    System.out.println(pb.printContactsFromCountryCodes(12));
                                    break; // 
                                } else {
                                    int actualCountryCode = convertChoice(countryCode); // Convert menu choice to actual
                                                                                        // country code
                                    boolean exists = false;

                                    for (int a : countryCodes) {
                                        if (a == actualCountryCode) {
                                            System.out.println("This country code has already been selected!");
                                            exists = true;
                                            break;
                                        }
                                    }

                                    if (!exists && ccCount < countryCodes.length) {
                                        countryCodes[ccCount] = actualCountryCode; // Store actual country code
                                        ccCount++;
                                    }
                                }
                            }
                            // Display contacts from selected countries
                            System.out.println(pb.printContactsFromCountryCodes(countryCodes));

                        } else if (showOpt == 2) {
                            // Search by Surname
                            String surname = prompt("Enter surname: ").trim();
                            List<Person> matchingContacts = new ArrayList<>();

                            // Manually search for matching surname
                            for (int i = 0; i < pb.getSize(); i++) {
                                Person person = pb.getContactAtIndex(i);
                                if (person != null && person.getLName().equalsIgnoreCase(surname)) {
                                    matchingContacts.add(person);
                                }
                            }

                            if (matchingContacts.isEmpty()) {
                                System.out.println("No contact exists with that surname!");
                            } else {
                                matchingContacts.sort(Comparator.comparing(Person::getLName));
                                System.out.println("\nMatching students:");
                                for (Person contact : matchingContacts) {
                                    System.out.println(contact);
                                }
                            }

                        } else if (showOpt == 3) {
                            break; // Go back to Main Menu
                        } else {
                            System.out.println("Invalid option! Please try again.");
                        }
                    }
                    break; // Ensures exit to Main Menu
                case 5:
                    System.out.println("Exiting program...");
                    System.exit(0); // Terminate the program
                    break;

            }
        }
    }

    /**
     * Show menu based on given index. <br>
     * <br>
     * 1 for Main Menu. <br>
     * <br>
     * 2 for Edit Contact Menu. <br>
     * <br>
     * 3 for View Phonebook Menu. <br>
     * <br>
     * 4 for Country Code Menu.
     * 
     * @param menuIdx     Index of the menu to be shown.
     * @param inlineTexts Number of menu options to be printed in a single line. Set
     *                    to 1 if you
     *                    want every line to only have one menu option.
     */
    private static void showMenu(int menuIdx, int inlineTexts) {
        String[] menu = MENUS[menuIdx - 1];
        int count = 0;
        String space = inlineTexts == 0 ? "" : "%-12s";
        String fmt = "[%d] " + space;
        for (int i = 0; i < menu.length; i++) {
            System.out.printf(fmt, i + 1, menu[i]);
            if (inlineTexts != 0) {
                count += 1;
            }
            if (count % inlineTexts == 0) {
                System.out.print("\n");
            }
        }
    }

    /**
     * Convert choices from the menu into their appropriate country code values.
     * 
     * @return Country code value of the menu choice.
     */
    private static int convertChoice(int choice) {
        switch (choice) {
            case 1:
                return 95; // Republic of the Union of Myanmar (Burma)
            case 2:
                return 855; // Kingdom of Cambodia
            case 3:
                return 66; // Kingdom of Thailand
            case 4:
                return 84; // Socialist Republic of Vietnam
            case 5:
                return 60; // Federation of Malaysia
            case 6:
                return 63; // Republic of the Philippines
            case 7:
                return 62; // Republic of Indonesia
            case 8:
                return 670; // Democratic Republic of Timor Leste
            case 9:
                return 856; // Lao People's Democratic Republic
            case 10:
                return 673; // Brunei Darussalam
            case 11:
                return 65; // Republic of Singapore
            default:
                return 0; // Invalid or exit
        }
    }

    /**
     * Create a new person object using a slightly complicated setup.
     * 
     * @return Newly created person object.
     */
    private static Person createNewPerson() {
        String id, fname, lname, sex, occupation, contactNum;
        int countryCode, areaCode;
        id = prompt("Enter Contact ID: ");
        fname = prompt("Enter First Name: ");
        lname = prompt("Enter Last Name: ");
        occupation = prompt("Enter Occupation: ");
        sex = prompt("Enter sex/gender: ");
        countryCode = Integer.parseInt(prompt("Enter Country Code: "));
        areaCode = Integer.parseInt(prompt("Enter Area Code: "));
        contactNum = prompt("Enter Contact Number: ");
        return new Person(id, fname, lname, sex, occupation, contactNum, countryCode, areaCode);
    }

    /**
     * Receive prompt and return the inputted value back to the variable or process
     * that requires
     * it. Data type is String. Do not forget to type cast if possible.
     * 
     * @param phrase Phrase to be given to user when requiring input.
     * @return Returns the data needed.
     */
    private static String prompt(String phrase) {
        System.out.print(phrase);
        return input.nextLine();
    }
}
