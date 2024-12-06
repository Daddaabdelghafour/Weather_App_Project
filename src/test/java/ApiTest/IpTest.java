package ApiTest;

import static org.junit.jupiter.api.Assertions.*;

import Services.Apis.IpApi;
import org.junit.jupiter.api.Test;

public class IpTest {

    @Test
    void testFetchIpAddressNotNull() {
        IpApi ipApi = new IpApi();
        String ipAddress = ipApi.getIpAdress();
        assertNotNull(ipAddress, "L'adresse IP ne doit pas être nulle.");
        System.out.println("Adresse IP : " + ipAddress);
    }

    @Test
    void testFetchIpAddressNotError() {
        IpApi ipApi = new IpApi();
        String ipAddress = ipApi.getIpAdress();
        assertNotEquals("Unable to fetch IP", ipAddress, "L'API doit être capable de récupérer l'adresse IP.");
    }
}
