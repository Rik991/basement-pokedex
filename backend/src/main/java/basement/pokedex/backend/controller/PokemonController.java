package basement.pokedex.backend.controller;


import basement.pokedex.backend.model.PokemonDTO;
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

}
