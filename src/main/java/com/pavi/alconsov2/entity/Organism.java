package com.pavi.alconsov2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Organism {
    private String sequence;

    private String dataset;

    private String deletedChars;

    public Organism(String dataset){
        this.dataset=dataset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organism organism = (Organism) o;
        return Objects.equals(dataset, organism.dataset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataset);
    }
}
