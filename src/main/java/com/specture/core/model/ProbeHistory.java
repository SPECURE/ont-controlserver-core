package com.specture.core.model;

import com.specture.core.enums.ProbeType;
import com.specture.core.enums.Status;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ProbeHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String probeId;

    @Enumerated(EnumType.STRING)
    private ProbeType type;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String operator;

    private Integer modemCount;

    private String comment;

}
