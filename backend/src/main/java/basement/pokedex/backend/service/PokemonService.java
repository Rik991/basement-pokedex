package basement.pokedex.backend.service;


import basement.pokedex.backend.model.PokemonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PokemonService {

    private final RestTemplate restTemplate;
    private final String pokeApiUrl = "https://pokeapi.co/api/v2/pokemon/";

    @Autowired
    public PokemonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //task1, Creare una funzionalità che permetta all’utente di inserire il _nome_ di un Pokémon e visualizzarne l’immagine.

    public PokemonDTO getPokemonByName(String name){

        String pokemonName = name.toLowerCase().trim();
//effettuo chiamata all'API fornita e mappo il JSON che mi torna con il nome cercato dall'utente
        ResponseEntity<Map> response = restTemplate.getForEntity(
                pokeApiUrl + pokemonName,
                Map.class
        );
        //recupero i dati che mi servono dalla response, li casto in base al tipo che mi serve
        Map<String, Object> pokemonData = response.getBody();
        int id = (Integer) pokemonData.get("id");
        String pokemonNameSearched = (String) pokemonData.get("name");
        //Mappo anche l'oggetto sprites per ricavarne la copertina
        Map<String, Object> sprites = (Map<String, Object>) pokemonData.get("sprites");
        String imageUrl = (String) sprites.get("front_default");

        return new PokemonDTO(id, pokemonNameSearched, imageUrl);

    }

}
