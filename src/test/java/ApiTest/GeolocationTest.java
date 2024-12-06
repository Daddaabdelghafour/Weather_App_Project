package ApiTest;

import static org.junit.jupiter.api.Assertions.*;

import Services.Apis.GeolocationApi;
import org.junit.jupiter.api.Test;

public class GeolocationTest {

    @Test
    void testFetchIpAddressNotNull() {
        GeolocationApi geoApi = new GeolocationApi();
        geoApi.fetchIpAddress();  // Récupère l'adresse IP
        String ipAddress = geoApi.getIpAddress();
        assertNotNull(ipAddress, "L'adresse IP ne doit pas être nulle.");
        System.out.println("IP Address: " + ipAddress);
    }

    @Test
    void testGetGeolocationInfoNotNull() {
        GeolocationApi geoApi = new GeolocationApi();
        String geolocationInfo = geoApi.getGeolocationInfo();
        assertNotNull(geolocationInfo, "Les informations de géolocalisation ne doivent pas être nulles.");
        System.out.println("Geolocation Info: " + geolocationInfo);
    }
}
