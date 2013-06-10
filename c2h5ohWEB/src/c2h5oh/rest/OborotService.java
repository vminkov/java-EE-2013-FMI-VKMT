package c2h5oh.rest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import c2h5oh.controller.daos.OrdersDao;
import c2h5oh.jpa.Order;
import c2h5oh.jpa.OrderItem;
import c2h5oh.jpa.Product;
import c2h5oh.util.Constants;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@Stateless
@Path("/oborot")
public class OborotService {
	@EJB
	private OrdersDao ordersDao;
	
	public OborotService(){
	    ClientConfig clientConfig = new DefaultClientConfig();
	    clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
	    Client client = Client.create(clientConfig);

	}
	
	
	@SuppressWarnings("deprecation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public  Map<String, Map<String, Integer>> products(@FormParam(Constants.USERNAME_REQUEST_PARAM_NAME) String username,
													  @FormParam(Constants.PASSWORD_REQUEST_PARAM_NAME) String password,
													  @FormParam("fromDate") String fromDate,
													  @FormParam("toDate") String toDate){
		System.out.println(fromDate + " " + toDate);
		
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date from = new Date();
	    Date to = new Date();
	    try {
	        from = dateFormat.parse(fromDate);
	        to = dateFormat.parse(toDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    
	    Map<Product, Integer> productsAndCount = new HashMap<Product, Integer>();
	    
	    System.out.println(from +  "  " + to);
	    for(Order o : ordersDao.filterByDate(from, to)) {
	    	for(OrderItem oi : o.getOrderItem()){
	    		Integer countTillNow = productsAndCount.get(oi.getProduct());
	    		if(countTillNow == null){
	    			countTillNow = new Integer(0);
	    		}
	    		
	    		countTillNow += oi.getQuantity();
	    		
	    		productsAndCount.put(oi.getProduct(), countTillNow);
	    	}
	    }
	    
	    Map<String, Map<String, Integer>> result = new HashMap<String, Map<String,Integer>>();
	    for(Product p : productsAndCount.keySet()){
	    	Map<String, Integer> countAndPrice = new HashMap<String, Integer>();
	    	countAndPrice.put("count", productsAndCount.get(p));
	    	countAndPrice.put("price", p.getPrice().intValue());
	    	
	    	result.put(p.getName(),	countAndPrice);
	    }
		return result;
		
	}
	
	class OrderInfoBean {
		
	}
}
