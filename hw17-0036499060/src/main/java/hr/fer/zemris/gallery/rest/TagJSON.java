package hr.fer.zemris.gallery.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.gallery.PictureDBProvider;

/**
 * Communicates with the database. Fetches all available tags.
 * 
 * @author MarinoK
 */
@Path("/tags")
public class TagJSON {

	/**
	 * Fetches all the available tags in the database.
	 * 
	 * @return list of tags wrapped in a response
	 */
	@GET
	@Produces("application/json")
	public Response getTags() {
		List<String> tags = PictureDBProvider.getDB().getTags();
		JSONObject result = new JSONObject();
		result.put("tags", tags);
		return Response.status(Status.OK).entity(result.toString()).build();
	}
}
