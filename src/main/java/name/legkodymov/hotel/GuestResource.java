package name.legkodymov.hotel;

import name.legkodymov.hotel.model.Guest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/api/guests")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuestResource {

    private final Set<Guest> guests = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    private Long counter = 0L;

    @POST
    public Set<Guest> createGuest(Guest guest) {
        if (guest.getId() == null || guest.getId() < 0) {
            guest.setId(counter++);
        }
        guests.add(guest);
        return guests;
    }

    @GET
    public Set<Guest> list() {
        return guests;
    }

    @DELETE
    public Set<Guest> delete(Guest guest) {
        guests.removeIf(existingGuest -> existingGuest.getId().equals(guest.getId()));
        return guests;
    }
}
