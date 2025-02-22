package PS_project.API_dummy_data.dto;


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