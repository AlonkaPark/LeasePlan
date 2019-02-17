package files;

public class payLoad {

	public String getPostData() {
		String bodyParam = "{" + 
				"  \"id\": 0," + 
				"  \"category\": {" + 
				"    \"id\": 0," + 
				"    \"name\": \"string\"" + 
				"  }," + 
				"  \"name\": \"Sara\"," + 
				"  \"photoUrls\": [" + 
				"    \"string\"" + 
				"  ]," + 
				"  \"tags\": [" + 
				"    {" + 
				"      \"id\": 0," + 
				"      \"name\": \"string\"" + 
				"    }" + 
				"  ]," + 
				"  \"status\": \"sold\"" + 
				"}";
		return bodyParam;
	}
	
	public String getOrderData() {
		String orderParam = "{" + 
				"  \"id\": 1000," + 
				"  \"petId\": 10," + 
				"  \"quantity\": 3," + 
				"  \"shipDate\": \"2019-02-17T15:00:00\"," + 
				"  \"status\": \"placed\"," + 
				"  \"complete\": true" + 
				"}";
		return orderParam;
	}
}
