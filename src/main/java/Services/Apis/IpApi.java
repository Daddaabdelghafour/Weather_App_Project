package Services.Apis;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class IpApi {

	public String IpAddress ;
	
	
	
	public String getIpAdress() {
		if(this.IpAddress == null) {
			fetchIpAddress();
		}
		
		return this.IpAddress;
	}
	
	public void fetchIpAddress() {
			String apiUrl = "https://api.ipify.org?format=json";
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder().uri(java.net.URI.create(apiUrl)).GET().build();
	        
	        
	     try {
	    	 
	    	 	HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	    	 	this.IpAddress = parseIpFromJson(response.body());
	    	  
	     }catch(IOException | InterruptedException e) {
	    	 e.printStackTrace();
	    	 this.IpAddress = "Unable to fetch IP";
	     }
	        
	        
	
	}
	
	
	  public String parseIpFromJson(String jsonResponse) {
    	  int startIndex = jsonResponse.indexOf(":\"") + 2;
    	  int endIndex = jsonResponse.indexOf("\"" , startIndex);
    	  return jsonResponse.substring(startIndex, endIndex);
      }
	
	
	  
	  public static void main(String []args) {
		  IpApi ipApi = new IpApi();
		  System.out.println("Adresse IP publique : " + ipApi.getIpAdress() );
		  
	  }
	  
	  
	  
	  
	  
	  

	
}
