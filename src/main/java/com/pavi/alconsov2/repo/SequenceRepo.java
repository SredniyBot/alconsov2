package com.pavi.alconsov2.repo;

import com.pavi.alconsov2.entity.LightSequence;
import com.pavi.alconsov2.entity.Sequence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface SequenceRepo extends CrudRepository<LightSequence, String> {

    @Query("select s from LightSequence as s where s.quantity>= :minQuantity")
    List<LightSequence> bestSequences(int minQuantity);
    @Query ("select max(s.quantity) from LightSequence as s")
    Integer bestConservSequence();
    @Query("select s from LightSequence as s where s.sequence=:sequence")
    Optional<LightSequence> findBySequence(String sequence);
}
