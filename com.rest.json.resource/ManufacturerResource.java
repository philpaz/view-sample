package com.rest.json.resource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.rest.json.bean.ManufacturerBean;
import com.rest.json.dao.ManufacturerDAO;
import com.rest.json.exception.UnauthorizedException;
import com.rest.json.filter.ResourceHandler;

@Path("manufacturer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ManufacturerResource {
	
	@Context private HttpServletRequest hSr;
	@Context private ApplicationContext ctxt;
	private static final Logger log = Logger.getLogger(ManufacturerResource.class.getName());
	
	@GET
    public List<ManufacturerBean> getAll() {
		
		/**Start here on security check**/
        String jzonToken= hSr.getHeader("JzonToken");
        boolean goForward = false;
    	ResourceHandler resourceHandler = new ResourceHandler();
    	
        if(jzonToken != null){
        	goForward = resourceHandler.validateResourceExecution(jzonToken);
        }
        //can throw either a 404 or a 401 will be trapped by intercepter on angular side
        if(goForward == false){
        	throw new UnauthorizedException();
        }
    	/**end here security check**/ 
    	    	
    	List<ManufacturerBean> manufacturerBean = null;
    	
    	if(goForward){
    		try {
				ManufacturerDAO manufacturerDAO = (ManufacturerDAO)ctxt.getBean("manufacturerdao"); 
				//get the UUID for the client to query on
				String nonce = resourceHandler.returnUserNonceFromToken(jzonToken);
				manufacturerBean = manufacturerDAO.getAllManufacturersRowMapper(nonce);
			} catch (BeansException e) {
				log.log(Level.SEVERE, "Just happened List<ManufacturerBean> getAll() " + e.getStackTrace());
			}
    	}
    	hSr.getSession().invalidate();
    	return manufacturerBean;
    }
    
    @GET
    @Path("{id: \\d+}")
    public ManufacturerBean get(@PathParam("id") final int id) {
    	
		/**Start here on security check**/
        String jzonToken= hSr.getHeader("JzonToken");
        boolean goForward = false;
    	ResourceHandler resourceHandler = new ResourceHandler();
    	
        if(jzonToken != null){
        	goForward = resourceHandler.validateResourceExecution(jzonToken);
        }
        //can throw either a 404 or a 401 will be trapped by intercepter on angular side
        if(goForward == false){
        	throw new UnauthorizedException();
        }
    	/**end here security check**/ 
        
    	ManufacturerBean manufacturerBean = null;
    	
    	if(goForward){
    		try {
				ManufacturerDAO manufacturerDAO = (ManufacturerDAO)ctxt.getBean("manufacturerdao");
				//get the UUID for the client to query on
				String nonce = resourceHandler.returnUserNonceFromToken(jzonToken);
				manufacturerBean = manufacturerDAO.getManufacturerByIdRowMapper(id, nonce);
			} catch (BeansException e) {
				log.log(Level.SEVERE, "Just happened ManufacturerBean get(@PathParam.. " + e.getStackTrace());
			}
    	}
    	hSr.getSession().invalidate();
    	return manufacturerBean;
    }
    
    @POST
    public ManufacturerBean post(ManufacturerBean manufacturerBean) {
    	
		/**Start here on security check**/
        String jzonToken= hSr.getHeader("JzonToken");
        boolean goForward = false;
    	ResourceHandler resourceHandler = new ResourceHandler();
    	
        if(jzonToken != null){
        	goForward = resourceHandler.validateResourceExecution(jzonToken);
        }
        //can throw either a 404 or a 401 will be trapped by intercepter on angular side
        if(goForward == false){
        	throw new UnauthorizedException();
        }
    	/**end here security check**/ 
       
    	if(goForward){
    		try {
				ManufacturerDAO manufacturerDAO = (ManufacturerDAO) ctxt.getBean("manufacturerdao");
				//get the UUID for the client to query on
				String nonce = resourceHandler.returnUserNonceFromToken(jzonToken);
				manufacturerBean.setNonce(nonce);
				manufacturerBean = manufacturerDAO.insertManufacturerByPreparedStatement(manufacturerBean);
			} catch (BeansException e) {
				log.log(Level.SEVERE, "Just happened ManufacturerBean post " + e.getStackTrace());
			}
    	} 	
    	hSr.getSession().invalidate();
    	return manufacturerBean;
    }
        
    @PUT
    public ManufacturerBean update(ManufacturerBean manufacturerBean) {
    	
		/**Start here on security check**/
        String jzonToken= hSr.getHeader("JzonToken");
        boolean goForward = false;
    	ResourceHandler resourceHandler = new ResourceHandler();
    	
        if(jzonToken != null){
        	goForward = resourceHandler.validateResourceExecution(jzonToken);
        }
        //can throw either a 404 or a 401 will be trapped by intercepter on angular side
        if(goForward == false){
        	throw new UnauthorizedException();
        }
    	/**end here security check**/ 
    	
        if(goForward){
        	try {
				ManufacturerDAO manufacturerDAO = (ManufacturerDAO) ctxt.getBean("manufacturerdao");
				//get the UUID for the client to query on
				String nonce = resourceHandler.returnUserNonceFromToken(jzonToken);
				manufacturerBean.setNonce(nonce);
				manufacturerBean = manufacturerDAO.updateManufacturerByPreparedStatement(manufacturerBean);
			} catch (BeansException e) {
				log.log(Level.SEVERE, "Just happened ManufacturerBean update " + e.getStackTrace());
			}
        }
    	hSr.getSession().invalidate();
    	return manufacturerBean;
    }
}
