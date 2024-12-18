package ma.ensa.mysoapservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndPoint {
    private static final String NAMESPACE_URI = "http://www.ensa.ma/MySoapService";
    private final CountryRepository countryRepository;

    @Autowired
    public CountryEndPoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // Get a country by name
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        return response;
    }

    // Add a new country
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addCountryRequest")
    @ResponsePayload
    public AddCountryResponse addCountry(@RequestPayload AddCountryRequest request) {
        Country countryRequest = request.getCountry();
        Country country = new Country();
        country.setName(countryRequest.getName());
        country.setPopulation(countryRequest.getPopulation());
        country.setCapital(countryRequest.getCapital());
        country.setCurrency(countryRequest.getCurrency());

        countryRepository.save(country);

        AddCountryResponse response = new AddCountryResponse();
        response.setStatus("Success");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCountryRequest")
    @ResponsePayload
    public UpdateCountryResponse updateCountry(@RequestPayload UpdateCountryRequest request) {
        // Access the country object from the request
        Country countryRequest = request.getCountry(); // Access the country object

        // Logic to update the country in the repository
        Country country = countryRepository.findCountry(countryRequest.getName());
        if (country != null) {
            country.setPopulation(countryRequest.getPopulation());
            country.setCapital(countryRequest.getCapital());
            country.setCurrency(countryRequest.getCurrency());
            countryRepository.save(country);
            UpdateCountryResponse response = new UpdateCountryResponse();
            response.setStatus("Success");
            return response;
        } else {
            UpdateCountryResponse response = new UpdateCountryResponse();
            response.setStatus("Country not found");
            return response;
        }
    }

    // Delete a country by name
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCountryRequest")
    @ResponsePayload
    public DeleteCountryResponse deleteCountry(@RequestPayload DeleteCountryRequest request) {
        Country country = countryRepository.findCountry(request.getName());
        if (country != null) {
            countryRepository.delete(country);
            DeleteCountryResponse response = new DeleteCountryResponse();
            response.setStatus("Success");
            return response;
        } else {
            DeleteCountryResponse response = new DeleteCountryResponse();
            response.setStatus("Country not found");
            return response;
        }
    }

    // Get all countries
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCountriesRequest")
    @ResponsePayload
    public GetAllCountriesResponse getAllCountries(@RequestPayload GetAllCountriesRequest request) {
        GetAllCountriesResponse response = new GetAllCountriesResponse();
        response.getCountries().addAll(countryRepository.findAll());
        return response;
    }
}
