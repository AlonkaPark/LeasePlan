import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import files.resources;
import files.payLoad;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LeasePlanAPITests {
	Properties property;
	resources res;
	payLoad bodyInfo;
	
	@BeforeTest
	public void SetUp() throws IOException {
		property = new Properties();
		res = new resources();
		bodyInfo = new payLoad();
		String filePath = "F:\\AutomationTesting\\APITesting\\LeaseAPITest\\src\\files\\env.properties";
		FileInputStream file = new FileInputStream(filePath);
		property.load(file);
	}
	
	//Create new pet and then get pet by existing Id. Delete that pet.
	@Test
	public void CreateGetAndDeletePet() {
		RestAssured.baseURI = property.getProperty("HOST");
		Response response = given().
			log().all().
			contentType(ContentType.JSON).
			accept(ContentType.JSON).
			body(bodyInfo.getPostData()).
			when().
			post(res.placePostPet()).
			then().
			log().all().
			assertThat().statusCode(200).contentType(ContentType.JSON).
			and().body("name", equalTo("Sara")).
			and().body("status", equalTo("sold")).
			extract().response();
		
		//Grab response
		String responseString = response.asString();
		JsonPath responseJson = new JsonPath(responseString);
		String petId = responseJson.get("id").toString();
		
		//Get pet by existing Id
		given().
		log().all().
		when().
		get(res.placeGetPetById()+petId).
		then().
		log().all().
		assertThat().statusCode(200).
		and().contentType(ContentType.JSON).
		and().body("name", equalTo("Sara")).
		and().body("status", equalTo("sold"));
		
		//Delete a pet by Id
		given().
		log().all().
		header("key", property.get("KEY")).
		when().
		delete(res.placeGetPetById()+petId).
		then().assertThat().statusCode(200);
		
	}
	
	//Get pet by non existing Id
	@Test
	public void GetPetById() {
		RestAssured.baseURI = property.getProperty("HOST");
		String petId = "91";
		given().
		log().all().
		when().
		get(res.placeGetPetById()+petId).
		then().
		assertThat().statusCode(404).
		and().contentType(ContentType.JSON).
		and().body("type", equalTo("error")).
		and().body("message", equalTo("Pet not found")).log().body();
	}
	
	//Get pets by status
	@Test
	public void GetPetsByStatus() {
		RestAssured.baseURI = property.getProperty("HOST");
		
		//Get pets by status "sold"
		given().
		queryParam("status", property.getProperty("StatusSold")).log().all().
		when().
		get(res.placeGetPetByStatus()).
		then().
		assertThat().statusCode(200).
		and().contentType(ContentType.JSON).
		and().body("status[0]", equalTo("sold")).log().body();
		
		//Get pets by status "available"
		given().
		queryParam("status", property.getProperty("StatusAvailable")).log().all().
		when().
		get(res.placeGetPetByStatus()).
		then().assertThat().statusCode(200).
		and().contentType(ContentType.JSON).
		and().body("status[0]", equalTo("available")).log().body();
		
		//Get pets by status "pending"
		given().
		queryParam("status", property.getProperty("StatusPending")).log().all().
		when().
		get(res.placeGetPetByStatus()).
		then().
		assertThat().statusCode(200).
		and().contentType(ContentType.JSON).
		and().body("status[0]", equalTo("pending")).log().body();
	}
	
	//Place an order for a pet
	@Test
	public void CreateOrderForPet() {
		RestAssured.baseURI = property.getProperty("HOST");
		given().
		contentType(ContentType.JSON).
		accept(ContentType.JSON).
		body(bodyInfo.getOrderData()).
		log().all().
		when().
		post(res.placeOrderForPet()).
		then().
		log().all().
		assertThat().statusCode(200).contentType(ContentType.JSON).
		and().body("id", equalTo(1000)).
		and().body("petId", equalTo(10)).
		and().body("status", equalTo("placed")).
		and().body("quantity", equalTo(3)).log().all();
	}

}

