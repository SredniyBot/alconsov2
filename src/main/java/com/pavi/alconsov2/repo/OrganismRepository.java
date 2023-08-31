package com.pavi.alconsov2.repo;

import com.pavi.alconsov2.entity.Organism;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class OrganismRepository {

    private final List<Organism> organisms;

    OrganismRepository() {
        organisms=new CopyOnWriteArrayList<>();
    }
    public void save(Organism organism) {
//        organisms.add(organism);
    }

    public boolean contains(String dataset) {
        return organisms.contains(new Organism(dataset));
    }
}
