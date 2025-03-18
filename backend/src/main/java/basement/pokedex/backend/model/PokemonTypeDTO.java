package basement.pokedex.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonTypeDTO {
    private String type;
    private List<PokemonDTO> pokemon;
}
//questo dto rappresenta la lista dei pokemon che ci tornano in seguito al click sul tipo del pokemon cercato