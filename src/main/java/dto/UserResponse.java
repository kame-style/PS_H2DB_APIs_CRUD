package dto;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private List<UserDTO> users;
    private Integer total;
    private Integer skip;
    private Integer limit;
}