package hr.fer.zemris.gallery.rest;

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
 * Class used for communicating with the database. Fetches the picture under the
 * name given in URL.
 * 
 * @author MarinoK
 */
@Path("/forName")
public class NameJSON {

	/**
	 * Fetches the picture under the name given in URL.
	 * 
	 * @param name
	 *            to search
	 * @return picture with the given name wrapped in the response
	 */
	@Path("{name}")
	@GET
	@Produces("application/json")
	public Response forName(@PathParam("name") String name) {
		Picture pic = PictureDBProvider.getDB().getForName(name);
		JSONObject result = new JSONObject();
		result.put("title", pic.getTitle());
		result.put("pictureTags", pic.getTags());
		result.put("fileName", pic.getFileName());
		return Response.status(Status.OK).entity(result.toString()).build();
	}
}