package edu.bbte.idde.szim2182.desktop;

import edu.bbte.idde.szim2182.backend.crud.CrudRepository;
import edu.bbte.idde.szim2182.backend.models.Hike;
import edu.bbte.idde.szim2182.backend.crud.MemoryCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Initialize the repository
        CrudRepository repository = new MemoryCrudRepository();

        // Create new hikes
        Hike hike1 = repository.create(new Hike("Mountain Trail", "A challenging mountain trail.", 5,
                "Base Camp", "Peak", 20.5));
        Hike hike2 = repository.create(new Hike("Forest Path", "A scenic route through the forest.", 2,
                "Forest Entrance", "Lake View", 10.0));

        // Log creation
        logger.info("Created Hike 1: {}", hike1);
        logger.info("Created Hike 2: {}", hike2);

        // Read
        Hike readHike = repository.read(1L);
        logger.info("Read Hike: {}", readHike);

        // Update
        Hike updatedHike = new Hike("Mountain Trail Updated", "An updated challenging mountain trail.", 6,
                "Base Camp", "Peak", 22.0);
        Hike hike1Updated = repository.update(1L, updatedHike);
        logger.info("Updated Hike: {}", hike1Updated);

        // Delete
        repository.delete(1L);
        List<Hike> remainingHikes = repository.readAll();
        logger.info("Remaining Hikes after deletion:");
        for (Hike hike : remainingHikes) {
            logger.info("{}", hike);
        }
    }
}
