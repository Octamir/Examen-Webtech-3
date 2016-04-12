package edu.ap.timcocx.quotes;

import javax.ws.rs.*;
import redis.clients.*;
import redis.clients.jedis.Jedis;

@Path("/quotes")
public class QuotesResource {
	
	@GET
	@Produces({"text/html"})
	public String getAllQuotes() {
		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();
		
		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Quotes</title>");
		builder.append("</head>");
		
		builder.append("<body>");
		builder.append("<ul>");
		for (String key : jedis.keys("quotes:*")) {
			for (String quote : jedis.smembers(key)) {
				builder.append("<li>" + quote);
			}
		}
		builder.append("</ul>");
		builder.append("</body>");
		builder.append("</html>");
		
		return builder.toString();
	}
}
