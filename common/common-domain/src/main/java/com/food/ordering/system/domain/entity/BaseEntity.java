package com.food.ordering.system.domain.entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity<ID> {
    private ID id;
}
