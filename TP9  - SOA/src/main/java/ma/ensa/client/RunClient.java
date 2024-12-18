package ma.ensa.client;
import ma.ensa.client.config.CountryClientConfig;
import
        org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class RunClient {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(CountryClientConfig.class);
        CountryClient client = context.getBean(CountryClient.class);
        GetCountryRequest request = new GetCountryRequest();
        request.setName("France");
        GetCountryResponse resp = client.getCountry(request);
        System.out.println("response: " + resp.getCountry().getCapital());
    }
}