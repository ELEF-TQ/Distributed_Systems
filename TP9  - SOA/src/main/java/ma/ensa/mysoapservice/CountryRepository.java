package ma.ensa.mysoapservice;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

@Component
public class CountryRepository {

    private static final Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        // Initialize countries map with sample data
        Country morocco = new Country();
        morocco.setName("Morocco");
        morocco.setPopulation(36000000);
        morocco.setCapital("Rabat");
        morocco.setCurrency(Currency.DH);
        countries.put(morocco.getName(), morocco);

        Country france = new Country();
        france.setName("France");
        france.setPopulation(67000000);
        france.setCapital("Paris");
        france.setCurrency(Currency.EUR);
        countries.put(france.getName(), france);

        Country poland = new Country();
        poland.setName("Poland");
        poland.setPopulation(38000000);
        poland.setCapital("Warsaw");
        poland.setCurrency(Currency.PLN);
        countries.put(poland.getName(), poland);
    }

    // Find country by name
    public Country findCountry(String name) {
        try {
            // Simulate network access
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException("Network simulation interrupted", e);
        }
        return countries.get(name);
    }

    // Add a new country
    public void save(Country country) {
        countries.put(country.getName(), country);
    }

    // Update an existing country
    public void update(Country country) {
        countries.put(country.getName(), country);
    }

    // Delete a country by name
    public void delete(Country country) {
        countries.remove(country.getName());
    }

    // Get all countries
    public Collection<Country> findAll() {
        return countries.values();
    }
}
