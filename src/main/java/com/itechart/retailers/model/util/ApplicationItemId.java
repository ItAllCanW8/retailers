package com.itechart.retailers.model.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ApplicationItemId implements Serializable {
    private Long applicationId;
    private Long itemId;
}
