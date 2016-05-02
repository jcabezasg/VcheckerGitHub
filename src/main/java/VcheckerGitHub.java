	import java.io.File;
	import java.io.IOException;
	import java.net.URI;
	import java.net.URISyntaxException;
	import java.util.*;
	
	import Model.Tag;
	import Model.ArtifactList;
	import Model.Art;
	
	import org.springframework.web.client.RestTemplate;
	import com.fasterxml.jackson.core.JsonParseException;
	import com.fasterxml.jackson.databind.JsonMappingException;
	import com.fasterxml.jackson.databind.ObjectMapper;
	
	/**
	 * 
	 * @author 
	 * Eugenio F. González (eugeniofidel@gmail.com)
	 * Javier Cabezas (jcabezasgivica@gmail.com)
	 * 
	 */
	
	public class VcheckerGitHub {
	
	    public static void main(String[] args) {
	        
	       	String file="./"+args[0];
	    	CheckVersions(file);
	    	  	
	    }
	    
	    /** Convert the contents of config.json file into Java objects and compare the versions
	     to the version list gathered from GitHub **/
	    
	    private static void CheckVersions(String file) {
	   
	        //Artifacts and versions from the config.json file are mapped to an object
	      
			ObjectMapper mapper = new ObjectMapper();			
			ArtifactList al=null;
			
			try {
				al = mapper.readValue(new File(file), ArtifactList.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//We construct an iterator with "al" variable to process all the artifacts stored in it
			
			Iterator<Art> it=al.getArtifacts().iterator();
			ArrayList<String> versions = createVersionList();
					
			while(it.hasNext()){
				
				Art artifact=it.next();
				if (versions.contains(artifact.getVersion())){
					System.out.println("The artifact " + artifact.getArtifact() + " with version " + artifact.getVersion() + " is present in GitHub under Kurento/Kurento-java");
				}else{
					System.out.println("The artifact " + artifact.getArtifact() + " with version " + artifact.getVersion() + " is not present in GitHub under Kurento/Kurento-java");
				}
			}
		
		}
	    
		/**
		 * Method addressed to query GitHub via REST services in order to get current tags of Kurento-java project
		 * @return	List with all the versions gathered from Github
		 */
		private static ArrayList<String> createVersionList() {
			
			//Building the url to be used by the REST client to get the desired information
			
			String url="https://api.github.com/repos/kurento/kurento-java/git/refs/tags/";
			ArrayList<String> tags = new ArrayList<String>();
			
			try {
				
				String reference;
				URI uri=new URI(url);
				RestTemplate resttemplate=new RestTemplate();
				
				/** The following sentence does not work as per information on https://jira.spring.io/browse/SPR-8263
				List<Tag> milista=resttemplate.getForObject(uri, List.class);
				**/
				
				String res=resttemplate.getForObject(uri, String.class);
				
				// Disable comment if the string gathered from the uri has to be shown in console
				// System.out.println(res);
				
				ObjectMapper mapper = new ObjectMapper();		
								
				try {
					
					List<Tag> myObjects = mapper.readValue(res, mapper.getTypeFactory().constructCollectionType(List.class, Tag.class));
					
				//############################################################################################################################
				// At this point myObjects contains a perfect mapping of the json file returned by the REST service (list of Tag objects) 
				//############################################################################################################################
					
				
					for (Tag t : myObjects ) {
						
						reference=t.getRef();
						String ref2=reference.substring(10,reference.length()-1);
						// Disable comment if each artifact has to be shown in console
						// System.out.println(ref2);
						tags.add(ref2);
					}	
				
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			
			} catch (URISyntaxException e) {
				e.printStackTrace();			
			}		
			
			return tags;
		}
		
		}
	