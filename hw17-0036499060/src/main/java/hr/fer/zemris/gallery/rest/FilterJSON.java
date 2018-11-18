package hr.fer.zemris.gallery.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.gallery.Picture;
import hr.fer.zemris.gallery.PictureDBProvider;

/**
 * Filter class used to fetch all the pictures associated with the given tag.
 * 
 * @author MarinoK
 */
@Path("/filter")
public class FilterJSON {

	/**
	 * Communicates with the database, fetches filtered pictures.
	 * 
	 * @param tag
	 *            to search for
	 * @return response containing all the pictures associated with the given tag
	 */
	@Path("{tag}")
	@GET
	@Produces("application/json")
	public Response filter(@PathParam("tag") String tag) {
		List<Picture> filtered = PictureDBProvider.getDB().getForTag(tag);
		JSONObject result = new JSONObject();
		result.put("filtered", filtered);
		return Response.status(Status.OK).entity(result.toString()).build();
	}
}
