package files;

public class resources {

	public String placeGetPetById() {
		String resource = "/pet/";
		return resource;
	}
	
	public String placeGetPetByStatus() {
		String resource = "/pet/findByStatus";
		return resource;
	}
	
	public String placePostPet() {
		String resource = "/pet";
		return resource;
	}
	
	public String placeOrderForPet() {
		String resource = "/store/order";
		return resource;
	}
}
