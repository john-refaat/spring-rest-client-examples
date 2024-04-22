package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author john
 * @since 21/04/2024
 */
@Slf4j
public class RestTemplateExamples {

    private static final String API_URL = "https://fruitshop2-predic8.azurewebsites.net/shop/v2/";

    @Test
    public void getProducts() throws Exception {
        String url = API_URL + "products";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class);
        log.info(jsonNode.toString());
    }

    @Test
    public void getCustomers() throws Exception {
        String url = API_URL + "customers";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class);
        log.info(jsonNode.toString());
    }

    @Test
    public void createProduct() throws Exception {
        String url = API_URL + "products";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("name", "Green-Apples");
        params.put("price", "3.39");
        JsonNode jsonNode = restTemplate.postForObject(url, params, JsonNode.class);
        log.info(jsonNode.toString());
    }

    @Test
    public void getProduct() {
        String url = API_URL + "products/20";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class);
        log.info(jsonNode.toString());
        Assert.assertEquals(20, jsonNode.get("id").asInt());
        Assert.assertEquals("Green-Apples", jsonNode.get("name").asText());
        Assert.assertEquals(3.39, jsonNode.get("price").asDouble(), 0.001);
    }

    @Test
    public void updateProduct() {
        String urlCreate = API_URL + "products";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("name", "Cantaloupe");
        params.put("price", "2.58");
        JsonNode jsonNode = restTemplate.postForObject(urlCreate, params, JsonNode.class);
        log.info(jsonNode.toString());
        Assert.assertEquals("Cantaloupe", jsonNode.get("name").asText());
        Assert.assertEquals(2.58, jsonNode.get("price").asDouble(), 0.001);
        Integer id = jsonNode.get("id").asInt();

        Map<String, String> updateParams = new HashMap<>();

        String urlUpdate = API_URL + "products/"+id;
        updateParams.put("name", "New Cantaloupe");
        updateParams.put("price", "3.22");
        restTemplate.put(urlUpdate, updateParams, JsonNode.class);

        JsonNode jsonNodeUpdate = restTemplate.getForObject(urlUpdate, JsonNode.class);
        log.info(jsonNodeUpdate.toString());
        Assert.assertEquals("New Cantaloupe", jsonNodeUpdate.get("name").asText());
        Assert.assertEquals(3.22, jsonNodeUpdate.get("price").asDouble(), 0.001);
    }

    @Test(expected= ResourceAccessException.class)
    public void updateProductPatchException() {
        String urlCreate = API_URL + "products";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("name", "Kiwi");
        params.put("price", "4.55");
        JsonNode jsonNode = restTemplate.postForObject(urlCreate, params, JsonNode.class);
        log.info(jsonNode.toString());
        Assert.assertEquals("Kiwi", jsonNode.get("name").asText());
        Assert.assertEquals(4.55, jsonNode.get("price").asDouble(), 0.001);
        Integer id = jsonNode.get("id").asInt();

        String urlUpdate = API_URL + "products/"+id;
        params.put("price", "7.21");
        JsonNode updatedJsonNode = restTemplate.patchForObject(urlUpdate, params, JsonNode.class);
        log.info(updatedJsonNode.toString());
    }

    @Test
    public void updateProductPatch() {
        String urlCreate = API_URL + "products";
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        Map<String, String> params = new HashMap<>();
        params.put("name", "Dragon-fruit");
        params.put("price", "4.48");
        JsonNode jsonNode = restTemplate.postForObject(urlCreate, params, JsonNode.class);
        log.info(jsonNode.toString());
        Assert.assertEquals("Dragon-fruit", jsonNode.get("name").asText());
        Assert.assertEquals(4.48, jsonNode.get("price").asDouble(), 0.001);
        Integer id = jsonNode.get("id").asInt();

        Map<String, String> patchParams = new HashMap<>();
        String urlUpdate = API_URL + "products/"+id;
        patchParams.put("price", "7.00");
        JsonNode updatedJsonNode = restTemplate.patchForObject(urlUpdate, patchParams, JsonNode.class);
        log.info(updatedJsonNode.toString());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void deleteProduct() {
        String url = API_URL + "products";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("name", "Green-Apples");
        params.put("price", "3.39");
        JsonNode jsonNode = restTemplate.postForObject(url, params, JsonNode.class);
        log.info(jsonNode.toString());

        String urlDelete = API_URL + "products/"+jsonNode.get("id").asInt();
        restTemplate.delete(urlDelete);

        JsonNode jsonNodeDelete = restTemplate.getForObject(urlDelete, JsonNode.class);
    }
}

