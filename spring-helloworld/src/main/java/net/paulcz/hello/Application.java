package net.paulcz.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
import java.util.LinkedHashMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.core.ParameterizedTypeReference;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private MyConfig config;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    String home() {
        return config.getMessage();
    }

    @GetMapping("/java-request/{id}")
	@ResponseBody
    public Map<String, Integer> getRequest(@PathVariable int id) throws java.net.URISyntaxException {
		if (id >= 10) {
			
			Map<String, Integer> rtn = new LinkedHashMap<>();

			rtn.put("id", id);

			return rtn;
		}
		id = id + 1;
		RestTemplate restTemplate = new RestTemplate();

		String uriString = "http://localhost:3002/java-request/" + String.valueOf(id);
		
		URI uri = new URI(uriString);
		
		ParameterizedTypeReference<Map<String, Integer>> responseType =
        new ParameterizedTypeReference<Map<String, Integer>>() {};
		
		
		RequestEntity<Void> request = RequestEntity.get(uri)
                 .accept(MediaType.APPLICATION_JSON).build();
				 
Map<String, Integer> jsonDictionary = restTemplate.exchange(request, responseType).getBody();

		return jsonDictionary;
    }
}