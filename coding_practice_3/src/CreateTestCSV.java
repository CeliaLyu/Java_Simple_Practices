import java.io.*;
import java.nio.file.*;

public class CreateTestCSV {
    public static void main(String[] args) {
        String[] lines = {
                "User Id,First Name,Last Name,Version,Insurance Company",
                "1,John,Doe,1,CompanyA",
                "2,Jane,Smith,1,CompanyB",
                "3,Bob,Johnson,2,CompanyA",
                "1,John,Doe,2,CompanyA",
                "4,Alice,Williams,1,CompanyB",
                "5,Chris,Brown,1,CompanyA",
                "3,Bob,Johnson,1,CompanyA",
                "2,Jane,Smith,2,CompanyB",
                "4,Alice,Williams,2,CompanyB",
                "6,Eve,White,1,CompanyC",
                "7,John,Doe,1,CompanyA", // Same First and Last Name, different UserId
                "8,Jane,Doe,1,CompanyA", // Different Last Name, same First Name
                "9,Bob,Smith,1,CompanyB", // Common First and Last Name combination
                "10,John,Doe,3,CompanyA", // Same UserId, higher Version
                "11,Emily,Davis,1,CompanyD", // Unique case with new Company
                "12,Tom,Jones,1,CompanyD",
                "13,Emma,Jones,1,CompanyD",
                "14,Michael,Smith,1,CompanyC",
                "15,Sophia,Johnson,1,CompanyC",
                "16,John,Smith,1,CompanyB",
                "17,Liam,Williams,1,CompanyB",
                "18,Olivia,Brown,1,CompanyA",
                "19,Noah,Miller,1,CompanyC",
                "20,Ava,Davis,1,CompanyD",
                "21,John,Doe,4,CompanyA", // Same UserId, highest Version
                "22,Mia,Davis,2,CompanyD" // Same Last Name, different First Name
        };

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("enrollment.csv"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
