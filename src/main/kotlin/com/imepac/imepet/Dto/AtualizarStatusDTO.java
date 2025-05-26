package com.imepac.imepet.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusDTO {
    private String status;

    public AtualizarStatusDTO() {
    }

    public AtualizarStatusDTO(String status) {
        this.status = status;
    }
}
