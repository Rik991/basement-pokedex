package basement.pokedex.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonDetailsDTO extends PokemonDTO {
    private List<String> types;
    private List<StatDTO> stats;

    public PokemonDetailsDTO(int id, String pokemonSearched, String imageUrl, List<String> types, List<StatDTO> stats) {
    }
}
