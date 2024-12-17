package gatling_exec.utils.wiremock;

import java.util.Scanner;

public class WireMockTest {
    public static void main(String[] args) {

        WireMockSetup.startWireMock();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 'stop' to stop WireMock:");
            String input = scanner.nextLine();
            if ("stop".equalsIgnoreCase(input)) {
                break;
            }
        }

        WireMockSetup.stopWireMock();

        scanner.close();
    }
}
