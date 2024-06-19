import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class EnrollmentProcessor {

    public static void main(String[] args) throws IOException {
        String inputFilePath = "enrollment.csv";  // Path to your CSV file
        List<EnrollmentRecord> records = readCSV(inputFilePath);
        Map<String, List<EnrollmentRecord>> groupedRecords = groupByInsuranceCompany(records);

        // Process each group of records by insurance company
        for (Map.Entry<String, List<EnrollmentRecord>> entry : groupedRecords.entrySet()) {
            String insuranceCompany = entry.getKey();
            List<EnrollmentRecord> companyRecords = entry.getValue();
            List<EnrollmentRecord> uniqueRecords = removeDuplicates(companyRecords);

            // Sort the records by last name and then first name
            uniqueRecords.sort(Comparator.comparing(EnrollmentRecord::getLastName)
                    .thenComparing(EnrollmentRecord::getFirstName));

            // Write the sorted records to a new CSV file named after the insurance company
            writeCSV(insuranceCompany + ".csv", uniqueRecords);
        }
    }

    /**
     * Reads the CSV file and converts each line into an EnrollmentRecord object.
     * Skips the header line.
     *
     * @param filePath the path to the input CSV file
     * @return a list of EnrollmentRecord objects
     * @throws IOException if an I/O error occurs
     *
     * Time Complexity: O(n), where n is the number of lines in the file.
     * Space Complexity: O(n), for storing the list of EnrollmentRecord objects.
     */
    private static List<EnrollmentRecord> readCSV(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .skip(1) // Skip header
                .map(EnrollmentRecord::fromCSV)
                .collect(Collectors.toList());
    }

    /**
     * Groups the records by insurance company.
     *
     * @param records the list of enrollment records
     * @return a map where the key is the insurance company and the value is a list of records for that company
     *
     * Time Complexity: O(n), where n is the number of records.
     * Space Complexity: O(n), for storing the map of grouped records.
     */
    private static Map<String, List<EnrollmentRecord>> groupByInsuranceCompany(List<EnrollmentRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(EnrollmentRecord::getInsuranceCompany));
    }

    /**
     * Removes duplicate records based on User Id, keeping only the record with the highest version.
     *
     * @param records the list of enrollment records
     * @return a list of unique enrollment records
     *
     * Time Complexity: O(n), where n is the number of records.
     * Space Complexity: O(n), for storing the map of unique records.
     */
    private static List<EnrollmentRecord> removeDuplicates(List<EnrollmentRecord> records) {
        Map<String, EnrollmentRecord> uniqueMap = new HashMap<>();
        for (EnrollmentRecord record : records) {
            String userId = record.getUserId();
            // Keep the record with the highest version
            if (!uniqueMap.containsKey(userId) || uniqueMap.get(userId).getVersion() < record.getVersion()) {
                uniqueMap.put(userId, record);
            }
        }
        return new ArrayList<>(uniqueMap.values());
    }

    /**
     * Writes the list of enrollment records to a CSV file.
     *
     * @param filePath the path to the output CSV file
     * @param records the list of enrollment records
     * @throws IOException if an I/O error occurs
     *
     * Time Complexity: O(m), where m is the number of records to write.
     * Space Complexity: O(1), as it uses a constant amount of space to write the file.
     */
    private static void writeCSV(String filePath, List<EnrollmentRecord> records) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write("User Id,First Name,Last Name,Version,Insurance Company");
            writer.newLine();
            for (EnrollmentRecord record : records) {
                writer.write(record.toCSV());
                writer.newLine();
            }
        }
    }

    static class EnrollmentRecord {
        private String userId;
        private String firstName;
        private String lastName;
        private int version;
        private String insuranceCompany;

        public EnrollmentRecord(String userId, String firstName, String lastName, int version, String insuranceCompany) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.version = version;
            this.insuranceCompany = insuranceCompany;
        }

        /**
         * Creates an EnrollmentRecord object from a CSV line.
         *
         * @param line the CSV line
         * @return the EnrollmentRecord object
         *
         * Time Complexity: O(1), as it processes a single line.
         * Space Complexity: O(1), as it uses a constant amount of space to store the parts array.
         */
        public static EnrollmentRecord fromCSV(String line) {
            String[] parts = line.split(",");
            return new EnrollmentRecord(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4]);
        }

        public String getUserId() {
            return userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public int getVersion() {
            return version;
        }

        public String getInsuranceCompany() {
            return insuranceCompany;
        }

        /**
         * Converts the EnrollmentRecord object to a CSV line.
         *
         * @return the CSV line
         *
         * Time Complexity: O(1), as it processes the fields of the record.
         * Space Complexity: O(1), as it uses a constant amount of space to store the CSV string.
         */
        public String toCSV() {
            return String.join(",", userId, firstName, lastName, String.valueOf(version), insuranceCompany);
        }
    }
}

/**
 * Combined Complexity Analysis:
 *
 * 1. readCSV:
 *    - Time Complexity: O(n), where n is the number of lines in the file.
 *    - Space Complexity: O(n), for storing the list of EnrollmentRecord objects.
 *
 * 2. groupByInsuranceCompany:
 *    - Time Complexity: O(n), where n is the number of records.
 *    - Space Complexity: O(n), for storing the map of grouped records.
 *
 * 3. removeDuplicates:
 *    - Time Complexity: O(n), where n is the number of records.
 *    - Space Complexity: O(n), for storing the map of unique records.
 *
 * 4. writeCSV:
 *    - Time Complexity: O(m), where m is the number of records to write.
 *    - Space Complexity: O(1), as it uses a constant amount of space to write the file.
 *
 * 5. Sorting each group of records:
 *    - Time Complexity: O(m log m) for each insurance company.
 *    - Space Complexity: O(1), as it uses a constant amount of space for sorting.
 *
 * Combined Complexity:
 * - Assuming the average number of records per company is roughly equal and denoted as m:
 * - O(n) + O(n) + O(n) + O(m) + O(k * m log m).
 * - As k * m = n (total number of records), the complexity simplifies to O(n log n).
 */
