package com.pavi.alconsov2.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class LightSequence {

    @Id
    private String sequence;
    private int quantity;
    public Sequence toSequence(){
        Sequence s = new Sequence(sequence);
        s.setQuantity(quantity);
        s.setMaxQuantity(quantity);
        s.setMinQuantity(quantity);
        return s;
    }

}
