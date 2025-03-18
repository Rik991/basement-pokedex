package basement.pokedex.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//questo DTO serve per mappare le singole statistiche, per poi ritrovarle nell'array dei dettagli del pokemon,
// il value sarà il campo base_stat che troviamo nel json
public class StatDTO {
    private String name;
    private int value;
}
