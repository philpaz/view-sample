package com.rest.json.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
///import java.util.logging.Logger;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

public class PoweredByResponseFilter implements ContainerResponseFilter {
	
	private static final Logger log = Logger.getLogger(PoweredByResponseFilter.class.getName());
	
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
        throws IOException {

    	    final String cookie = "cookie";
    	    final String accept = "accept";
			final String contentType = "Content-Type";
    		
			Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
			MultivaluedMap<String,Object> responseHeaderMap;
    		MultivaluedMap<String, String> requestHeaderMap;

    		cookieMap = requestContext.getCookies();
    		
    		for (Entry<String, Cookie> entry : cookieMap.entrySet()) {
    			if(entry.getKey().equalsIgnoreCase("JSESSIONID")){

    				//entry.getValue() = null;
    			}
    			//System.out.println("PBR cookieMap Cookie Key:: " + entry.getKey() + " ::Cookie Value:: "
    				//+ entry.getValue());
    		} 
    		
    		responseHeaderMap = responseContext.getHeaders();
    		requestHeaderMap = requestContext.getHeaders();
    		
    		responseContext.getHeaders().add("X-Powered-By", "Some Dude.");
    		
    		responseContext.getHeaders().add("cache-control", "private, no-store, no-cache, must-revalidate");
    		responseContext.getHeaders().add("pragma", "no-cache"); 
    		
    		for (Entry<String, List<Object>> entry : responseHeaderMap.entrySet()) {    
    			if(entry.getKey().equalsIgnoreCase(contentType)){
    				//then changes these values accordingly
    				@SuppressWarnings("unused")
					List<Object> contentTypeList = entry.getValue();
    				//contentTypeList.clear();
    				//contentTypeList.add("application/x-javascript");
    				//entry.setValue(contentTypeList);
    			}
    	    	//log.log(Level.INFO, "PBR responseHeaderMap Header : " + entry.getKey() + " Header Value : "
    			//	+ entry.getValue());
    		}
    	
    		//System.out.println("  ");
    		
    		for (Entry<String, List<String>> entry : requestHeaderMap.entrySet()) {
    			
    			//Setting the cookie to null
    			//Need to evaluate, is Response Filter thrown during 403,408, etc exceptions
    			if(entry.getKey().equalsIgnoreCase(cookie)){
    				List<String> cookieList = entry.getValue();
    				//Clear the cookie list of cookie object references
    				cookieList.clear();
    				cookieList.add("JSESSIONID=COOKED");
    			}else if(entry.getKey().equalsIgnoreCase(accept)){
    				//then changes these values accordingly
    				List<String> acceptList = entry.getValue();
    				acceptList.clear();
    				acceptList.add("application/x-javascript");
    				acceptList.add("application/json");
    				entry.setValue(acceptList);
    			}
    	    	//log.log(Level.INFO, "PBR requestHeaderMap Header : " + entry.getKey() + " Header Value : "
    			//	+ entry.getValue());
    		}     
    		
    		//System.out.println("at the end of PoweredByResponse");
    		
    	   
    }

}