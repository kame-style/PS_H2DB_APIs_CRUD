package Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coordinates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double lat;
    private Double lng;
}