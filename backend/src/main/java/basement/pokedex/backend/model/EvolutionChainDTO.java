package basement.pokedex.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvolutionChainDTO {

    private List<PokemonDTO> evolutionChain;
}
