package edu.ap.timcocx.quotes;

import javax.ws.rs.*;
import redis.clients.*;
import redis.clients.jedis.Jedis;

@Path("/quotes")
public class QuotesResource {

	// Elke quote die er is
	@GET
	@Produces({ "text/html" })
	public String getAllQuotes() {
		// Uncomment voor opvullen
		//this.fillDb(true);

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

	// Specifieke auteur
	// Data => de data die wordt gepost
	@POST
	public String getAuthorQuotes(String data) {
		// Uncomment voor opvullen
		//this.fillDb(true);

		Jedis jedis = JedisConnection.getInstance().getConnection();
		StringBuilder builder = new StringBuilder();

		builder.append("<html>");
		builder.append("<head>");
		builder.append("<title>Quotes</title>");
		builder.append("</head>");

		builder.append("<body>");
		// Selecteren van correcte author
		for (String author : jedis.keys("author:*")) {
			String tmpAuthor = jedis.get(author);
			if (tmpAuthor.equals(data)) {
				builder.append("testink");
				int authorId = Integer.parseInt(author.split(":")[1]);

				builder.append("<h1>" + tmpAuthor + "</h1>");
				builder.append("<ul>");
				for (String quote : jedis.smembers("quotes:" + authorId)) {
					builder.append("<li>" + quote);
				}
				builder.append("</ul>");

				break; // Breaken is normaal nooit een goed idee, maar het haalt
						// performantie omhoog als je het doet in een foreach
						// loop
			}
		}

		builder.append("</body>");
		builder.append("</html>");

		return builder.toString();
	}

	private void fillDb(boolean flush) {
		Jedis jedis = JedisConnection.getInstance().getConnection();

		if (flush) {
			jedis.flushDB();
		}

		jedis.set("author:1", "Einstein");
		jedis.set("author:2", "Stephen Hawking");

		jedis.sadd("quotes:1", "A person who never made a mistake never tried anything new");
		jedis.sadd("quotes:1", "If you want to live a happy life, tie it to a goal, not to people or objects");
		jedis.sadd("quotes:2", "People who boast about their IQ are losers");
	}
}
