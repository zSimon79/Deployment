package edu.bbte.idde.szim2182.desktop;

import edu.bbte.idde.szim2182.backend.CrudRepository;
import edu.bbte.idde.szim2182.backend.Hike;
import edu.bbte.idde.szim2182.backend.MemoryCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Példányosítjuk a backend szolgáltatást
        CrudRepository repository = new MemoryCrudRepository();

        repository.create(new Hike(1, "Mountain Trail", "A challenging mountain trail.", 5,
                "Base Camp", "Peak", 20.5));
        repository.create(new Hike(2, "Forest Path", "A scenic route through the forest.", 2,
                "Forest Entrance", "Lake View", 10.0));

        Hike hike1 = repository.read(1);
        logger.info("Read Hike 1: {}", hike1);

        // Update
        Hike updatedHike = new Hike(1, "Mountain Trail", "An updated challenging mountain trail.", 6,
                "Base Camp", "Peak", 22.0);
        repository.update(1, updatedHike);
        Hike hike1Updated = repository.read(1);
        logger.info("Updated Hike 1: {}", hike1Updated);

        // Delete
        repository.delete(1);
        List<Hike> remainingHikes = repository.readAll();
        logger.info("Remaining Hikes after deletion:");
        for (Hike hike : remainingHikes) {
            logger.info(hike.toString());
        }
    }
}
