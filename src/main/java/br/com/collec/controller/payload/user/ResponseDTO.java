package br.com.collec.controller.payload.user;


import br.com.collec.entity.User;

import java.util.List;

public record ResponseDTO(
        List<User> users,
        int pageNo,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean last
) {

}
