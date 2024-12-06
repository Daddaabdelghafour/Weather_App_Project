package ApiTest;

import static org.junit.jupiter.api.Assertions.*;

import Services.Apis.CityValidationApi;
import org.junit.jupiter.api.Test;

public class CityValidationTest {

    @Test
    void testIsCityValidNotNull() {
        CityValidationApi cityApi = new CityValidationApi();
        boolean result = cityApi.isCityValid("Marrakech"); // Tester une ville existante
        assertNotNull(result, "Le résultat de la validation de la ville ne doit pas être null.");
        System.out.println("Test Marrakech: " + result);
    }

    @Test
    void testIsCityValidForKnownCity() {
        CityValidationApi cityApi = new CityValidationApi();
        assertTrue(cityApi.isCityValid("Marrakech"), "Marrakech devrait être une ville valide.");
    }

    @Test
    void testIsCityValidForUnknownCity() {
        CityValidationApi cityApi = new CityValidationApi();
        assertFalse(cityApi.isCityValid("UnknownCity123456"), "UnknownCity123456 ne devrait pas être une ville valide.");
    }
}
