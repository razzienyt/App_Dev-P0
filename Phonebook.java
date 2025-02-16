import java.util.Scanner;

public class Phonebook {
    // Storage of contacts.
    private Person[] contacts;
    // Number of contacts present in the phonebook.
    private int size;

    /**
     * Create a phonebook of size 50.
     */
    public Phonebook() {
        contacts = new Person[50];
    }

    /**
     * @return Number of contacts stored in this phonebook.
     */
    public int getSize() {
        // Complete this method
        return size;
    }

    /**
     * Get the contact at index.
     * 
     * @param index Index to get contact.
     * @return Person object from index. Null if index is not valid or out of range.
     */
    public Person getContactAtIndex(int index) {
        // Complete this method
        if (index >= 0 && index < size) {
            return contacts[index];
        }
        return null;
    }

    /**
     * Get the person object based on a given id.
     * 
     * @param id Target id.
     * @return Person object that has this id. Null if it does not exist.
     */

    public Person getContact(String id) {
        for (int i = 0; i < size; i++) {
            if (contacts[i] != null && contacts[i].getId().equals(id)) {
                return contacts[i];
            }
        }
        return null;
    }

    /**
     * Checks if this phonebook has contacts or not.
     * 
     * @return True or False.
     */
    public boolean isEmpty() {
        return this.getSize() == 0;
    }

    /**
     * Increase number of contacts present in this phonebook.
     */
    public void incrSize() {
        this.size++;
    }

    /**
     * Decrease number of contacts present in this phonebook.
     */
    public void decrSize() {
        this.size--;
    }

    /**
     * Increases the size of the phonebook whenever it is full.
     */
    private void increasePhonebookMaxSize() {
        // Complete this method
        Person[] newContacts = new Person[contacts.length * 2];

        for (int i = 0; i < contacts.length; i++) {
            newContacts[i] = contacts[i];
        }

        contacts = newContacts;
    }

    /**
     * Inserts a new person object at its appropriate lexicographic location in the
     * phonebook.
     * 
     * @param p Person to be addded to the Phonebook.
     */
    public void insert(Person p) {
        // Complete this method
        if (size >= contacts.length) {
            increasePhonebookMaxSize(); // Double the phonebook size before inserting
        }

        int index = findIndexInsertion(p); // Find the correct insertion point

        // Shift elements to make space for the new contact
        for (int i = size; i > index; i--) {
            contacts[i] = contacts[i - 1];
        }

        // Insert the new contact
        contacts[index] = p;
        size++; // Increase phonebook size
    }

    /**
     * Searches in what index should this person object with the given be inserted.
     * 
     * @param p Person object to be inserted into the phonebook.
     * @return Appropriate index (position).
     */
    private int findIndexInsertion(Person p) {
        // Complete this method
        int left = 0, right = size - 1; // Use size instead of phonebook.size()
        String newName = p.getFullName();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midName = contacts[mid].getFullName(); // Use contacts array properly

            if (midName.compareTo(newName) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left; // Left will be the correct insertion point

    }

    /**
     * Delete a person based on their contact id.
     * 
     * @param id Contact ID of that contact.
     * @return Deleted contact.
     */
    public Person deleteContact(String id) {
        // Complete this method...
        if (id == null) {
            return null;
        }

        for (int i = 0; i < size; i++) {
            if (contacts[i] != null && contacts[i].getId().equals(id)) {
                Person deletedContact = contacts[i];

                // Shift elements to the left to fill the gap
                adjustPhonebook(i, size, "f");

                size--; // Reduce size
                return deletedContact; // Clear the last entry
            }
        }
        return null; // Contact not found
    }

    /**
     * Adjusts the existing contacts in a phonebook from a given starting index to
     * where it ends,
     * following a particular direction.
     * 
     * @param start     Index to start adjustment from.
     * @param end       Index to end adjustment into.
     * @param direction Direction in which the adjustment must be made. direction =
     *                  "f" if element
     *                  at index 0 takes the value of the element next to it (e.g.
     *                  index 1). direction = "b"
     *                  if element at index 1 takes the value of the element behind
     *                  it (e.g. index 0).
     */

    private void adjustPhonebook(int start, int end, String direction) {
        if (contacts == null || start < 0 || end > contacts.length || start >= end) {
            return;
        }

        if (direction.equals("f")) { // Move contacts forward (left shift)
            for (int i = start; i < end - 1; i++) { // Prevent out-of-bounds error
                contacts[i] = contacts[i + 1];
            }
            contacts[end - 1] = null; // Clear last entry after shift
        } else if (direction.equals("b")) { // Move contacts backward (right shift)
            for (int i = end - 1; i > start; i--) {
                contacts[i] = contacts[i - 1];
            }
            contacts[start] = null; // Clear first entry after shift
        }
    }

    /**
     * Uses ellipsis to ambiguously accept as many country codes as possible. <br>
     * <br>
     * For example: <br>
     * <br>
     * If we have: printContactsFromCountryCodes(1, 2, 3) <br>
     * <br>
     * Then we get: countryCodes = { 1, 2, 3 };
     * 
     * @param countryCodes Area codes to be used as a filter.
     * @return Contacts on this phonebook under a particular area code set by the
     *         user.
     */
    public String printContactsFromCountryCodes(int... countryCodes) {
        // Complete this method.
        if (size == 0) {
            return "Error: Phonebook is empty.";
        }

        for (int code : countryCodes) {
            if (code == 0) {
                return ""; // No output needed, just return an empty string
            }
        }

        StringBuilder result = new StringBuilder("Phonebook Contacts:\n");
        boolean found = false;

        for (int i = 0; i < size; i++) {
            Person p = contacts[i];
            int personCode = p.getCountryCode();

            for (int code : countryCodes) {
                if (code == 12 || personCode == code) {
                    result.append(p.getFullName())
                            .append(" is a ").append(p.getOccupation())
                            .append(". His number is ")
                            .append(personCode).append("-")
                            .append(p.getAreaCode()).append("-")
                            .append(p.getContactNum())
                            .append("\n");
                    found = true;
                    break;
                }
            }
        }

        return found ? result.toString() : "No contacts found for the selected country.";
    }

    /**
     * Print the entire phonebook without any filter or so...
     * 
     * @return The entire list of contacts present in this phonebook.
     */
    public String toString() {
        // Complete this method.

        if (size == 0) {
            return "Phonebook is empty.";
        }

        StringBuilder sb = new StringBuilder("Phonebook Contacts:\n");
        for (int i = 0; i < size; i++) {
            if (contacts[i] != null) { // Check if the contact is not null
                sb.append(contacts[i].toString()).append("\n");
            }
        }

        return sb.toString();
    }

}
