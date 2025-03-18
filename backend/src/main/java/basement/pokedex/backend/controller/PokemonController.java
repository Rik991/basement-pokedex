package basement.pokedex.backend.controller;


import basement.pokedex.backend.model.EvolutionChainDTO;
import basement.pokedex.backend.model.PokemonDTO;
import basement.pokedex.backend.model.PokemonDetailsDTO;
import basement.pokedex.backend.model.PokemonTypeDTO;
import basement.pokedex.backend.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pokemon")
@CrossOrigin(origins = "*")
public class PokemonController {

    @Autowired
    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    //espongo l'API per la prima task, mi arriva il nome dalla ricerca e restituisco il pokemon scelto
    @GetMapping("/{name}")
    public ResponseEntity<PokemonDTO> getPokemonByName(@PathVariable String name) {
      PokemonDTO pokemon = pokemonService.getPokemonByName(name);
      return new ResponseEntity<>(pokemon, HttpStatus.OK);
    }

    //per la task 2 ripetiamo la chiamata get ma stavolta al metodo aggiornato con i dettagli
    @GetMapping("/details/{name}")
    public ResponseEntity<PokemonDetailsDTO> getPokemonWithDetails(@PathVariable String name){
        PokemonDetailsDTO pokemon = pokemonService.getPokemonWithDetails(name);
        return new ResponseEntity<>(pokemon, HttpStatus.OK);
    }

    //creiamo anche l'endpoint per la ricerca sul tipo di pokemon
    @GetMapping("/type/{type}")
    public ResponseEntity<PokemonTypeDTO> getPokemonByType (@PathVariable String type){
        PokemonTypeDTO pokemonTypeList = pokemonService.getPokemonByTipe(type);
        return new ResponseEntity<>(pokemonTypeList, HttpStatus.OK);
    }

    //task 3, endpoind per la evolutionChain
    @GetMapping("/evolution/{name}")
    public ResponseEntity<EvolutionChainDTO> getEvolutionChain(@PathVariable String name){
        EvolutionChainDTO evolutionChain = pokemonService.getEvolutionChain(name);
        return new ResponseEntity<>(evolutionChain, HttpStatus.OK);
    }

}



