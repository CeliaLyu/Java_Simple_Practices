import java.util.Arrays;
import java.util.List;

public class Solution {

    /**
     * This method calculates the number of buildings that are served by at least one router
     * within their specified range. It iterates over each router, marking the buildings
     * within its range as covered. After marking, it checks how many buildings have enough
     * coverage as required.
     *
     * Reference:
     * https://leetcode.com/discuss/interview-experience/1470686/amazon-online-assessment
     *
     * Time Complexity Analysis:
     * - The time complexity of this method is O(m * n), where 'm' is the number of routers
     *   and 'n' is the number of buildings. This is because for each router, the inner loop
     *   potentially covers 'n' buildings.
     * - This could be optimized further depending on the constraints and requirements.
     *
     * Space Complexity Analysis:
     * - The space complexity of this method is O(n), where 'n' is the number of buildings.
     *   This is due to the additional space used by the `coverage` array.
     *
     * @param buildingDemand List containing the coverage demand for each building.
     * @param routerPositions List containing the positions of the routers.
     * @param routerRanges List containing the range of each router.
     * @return The number of buildings that are sufficiently covered by routers.
     */
    public static int countServedBuildings(List<Integer> buildingDemand, List<Integer> routerPositions, List<Integer> routerRanges) {
        int servedBuildingCount = 0;

        int numberOfBuildings = buildingDemand.size();  // Number of buildings
        int numberOfRouters = routerPositions.size();  // Number of routers
        int[] buildingCoverage = new int[numberOfBuildings];  // Array to keep track of coverage for each building

        // Iterate over each router
        for (int routerIndex = 0; routerIndex < numberOfRouters; routerIndex++) {
            int currentRange = routerRanges.get(routerIndex);  // Get the range of the current router
            int leftLimit = Math.max(0, routerPositions.get(routerIndex) - currentRange);  // Calculate the left bound of coverage
            int rightLimit = Math.min(numberOfBuildings - 1, routerPositions.get(routerIndex) + currentRange);  // Calculate the right bound of coverage

            // Mark coverage for buildings within the router's range
            for (int buildingIndex = leftLimit; buildingIndex <= rightLimit; buildingIndex++) {
                buildingCoverage[buildingIndex]++;
            }
        }

        // Count buildings that are sufficiently covered
        for (int buildingIndex = 0; buildingIndex < numberOfBuildings; buildingIndex++) {
            if (buildingCoverage[buildingIndex] >= buildingDemand.get(buildingIndex)) {
                servedBuildingCount++;
            }
        }

        return servedBuildingCount;
    }

    public static void main(String[] args) {
        // Test Case 1
        List<Integer> buildingDemand1 = Arrays.asList(1, 2, 3);
        List<Integer> routerPositions1 = Arrays.asList(0, 1, 2);
        List<Integer> routerRanges1 = Arrays.asList(1, 1, 1);
        System.out.println("Test Case 1: " + countServedBuildings(buildingDemand1, routerPositions1, routerRanges1)); // Expected output: 2

        // Test Case 2
        List<Integer> buildingDemand2 = Arrays.asList(2, 1, 2);
        List<Integer> routerPositions2 = Arrays.asList(0, 1);
        List<Integer> routerRanges2 = Arrays.asList(1, 2);
        System.out.println("Test Case 2: " + countServedBuildings(buildingDemand2, routerPositions2, routerRanges2)); // Expected output: 2

        // Test Case 3
        List<Integer> buildingDemand3 = Arrays.asList(1, 2, 1, 2);
        List<Integer> routerPositions3 = Arrays.asList(1, 3);
        List<Integer> routerRanges3 = Arrays.asList(1, 1);
        System.out.println("Test Case 3: " + countServedBuildings(buildingDemand3, routerPositions3, routerRanges3)); // Expected output: 3

        // Test Case 4
        List<Integer> buildingDemand4 = Arrays.asList(1, 1, 1, 1);
        List<Integer> routerPositions4 = Arrays.asList(1);
        List<Integer> routerRanges4 = Arrays.asList(1);
        System.out.println("Test Case 4: " + countServedBuildings(buildingDemand4, routerPositions4, routerRanges4)); // Expected output: 2

        // Test Case 5
        List<Integer> buildingDemand5 = Arrays.asList(1, 2, 3, 4);
        List<Integer> routerPositions5 = Arrays.asList(0, 2);
        List<Integer> routerRanges5 = Arrays.asList(1, 2);
        System.out.println("Test Case 5: " + countServedBuildings(buildingDemand5, routerPositions5, routerRanges5)); // Expected output: 2
    }
}
