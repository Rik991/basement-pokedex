package basement.pokedex.backend.service;


import basement.pokedex.backend.exceptions.PokemonNotFoundException;
import basement.pokedex.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private final RestTemplate restTemplate;
    private final String pokeApiUrl = "https://pokeapi.co/api/v2/pokemon/";
    private final String pokeTypeUrl = "https://pokeapi.co/api/v2/type/";
    private final String pokeSpeciesUrl = "https://pokeapi.co/api/v2/pokemon-species/";

    @Autowired
    public PokemonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    //task1, Creare una funzionalità che permetta all’utente di inserire il _nome_ di un Pokémon e visualizzarne l’immagine.
    public PokemonDTO getPokemonByName(String name) {
        String pokemonName = name.toLowerCase().trim();
        try {
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
        } catch (Exception ex) {
            throw new PokemonNotFoundException("Il Pokemon " + pokemonName + " non è stato trovato");
        }
    }

    //task 2, come per la prima task andiamo a mappare il json ma stavolta aggiungiamo il tipo e le statistiche sfruttando l'apposito DTO
    public PokemonDetailsDTO getPokemonWithDetails(String name) {
        String pokemonName = name.toLowerCase().trim();
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    pokeApiUrl + pokemonName,
                    Map.class
            );
            Map<String, Object> pokemonData = response.getBody();
            int id = ((Number) pokemonData.get("id")).intValue();
            String pokemonSearched = (String) pokemonData.get("name");
            Map<String, Object> sprites = (Map<String, Object>) pokemonData.get("sprites");
            String imageUrl = (String) sprites.get("front_default");

            //ora estraiamo i tipi annidati nell'oggetto pokemon scelto e ci ricaviamo i nomi
            List<String> types = ((List<Map<String, Object>>) pokemonData.get("types"))
                    .stream()
                    .map(typeInfo -> ((Map<String, Object>) typeInfo.get("type")).get("name").toString())
                    .collect(Collectors.toList());

            // quindi andiamo a estrarre anche le statistiche e i loro valori
            List<StatDTO> stats = ((List<Map<String, Object>>) pokemonData.get("stats"))
                    .stream()
                    .map(statMap -> {
                        String statName = ((Map<String, Object>) statMap.get("stat")).get("name").toString();
                        int baseStat = ((Number) statMap.get("base_stat")).intValue();
                        return new StatDTO(statName, baseStat);
                    })
                    .collect(Collectors.toList());

            PokemonDetailsDTO details = new PokemonDetailsDTO();
            // setto tutti i campi nel DTO
            details.setId(id);
            details.setName(pokemonSearched);
            details.setImage(imageUrl);
            details.setTypes(types);
            details.setStats(stats);
            return details;
        } catch (Exception ex) {
            throw new PokemonNotFoundException("Il Pokemon " + pokemonName + " non è stato trovato");
        }
    }

    //task 2, metodo per ottenere la lista dei pokemon dal tipo cliccato
    public PokemonTypeDTO getPokemonByTipe(String type) {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                pokeTypeUrl + type,
                Map.class
        );
        Map<String, Object> typeData = response.getBody();
        List<PokemonDTO> pokemonDTOList = ((List<Map<String, Object>>) typeData.get("pokemon"))
                .stream()
                .limit(10)
                .map(pokeObj -> {
                    String pokemonName = ((Map<String, Object>) pokeObj.get("pokemon")).get("name").toString();
                    return getPokemonWithDetails(pokemonName);
                }).collect(Collectors.toList());
        return new PokemonTypeDTO(type, pokemonDTOList);
    }

    //task 3, metodo che mappa le evoluzioni contenute nella specie (data dal nome) sfruttando una funzione ricorsiva.
    public EvolutionChainDTO getEvolutionChain(String name) {
        String pokemonName = name.toLowerCase().trim();
        try {
            //mappiamo il JSON per ottenere la specie
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    pokeSpeciesUrl + pokemonName,
                    Map.class
            );
            Map<String, Object> species = response.getBody();
            //ora possiamo estrarre dall'oggetto species l'url della catena evolutiva
            Map<String, Object> evolutionChain = (Map<String, Object>) species.get("evolution_chain");
            String evolutionUrl = (String) evolutionChain.get("url");
            //ripetiamo sull'endpoint evolutionUrl per ottenere la catena
            ResponseEntity<Map> evolutionUrlResponse = restTemplate.getForEntity(
                    evolutionUrl,
                    Map.class
            );
            Map<String, Object> evolutions = evolutionUrlResponse.getBody();
            Map<String, Object> chain = (Map<String, Object>) evolutions.get("chain");
            //utilizziamo la funzione ricorsiva nameOfEvolutions per estrarre i nomi delle evoluzioni
            List<String> speciesNames = nameOfEvolutions(chain);
            //per ogni nome otteniamo il pokemon giusto (userò la funzione getByName per limitarmi a nome e foto)
            List<PokemonDTO> evolutionChainList = speciesNames.stream().map(this::getPokemonByName).collect(Collectors.toList());
            return new EvolutionChainDTO(evolutionChainList);
        } catch (Exception ex) {
            throw new PokemonNotFoundException(pokemonName + " non ha evoluzioni!");
        }
    }

    //funzione ricorsiva per scorrere la catena evolutiva
    private List<String> nameOfEvolutions(Map<String, Object> chain) {
        List<String> result = new ArrayList<>();
        if (chain == null) {
            return result;
        }
        //aggiungiamo il nome della specie corrente
        Map<String, Object> species = (Map<String, Object>) chain.get("species");
        if (species != null && species.get("name") != null) {
            result.add(species.get("name").toString());
        }
        //eventuali evoluzioni successive
        List<Map<String, Object>> evolvesTo = (List<Map<String, Object>>) chain.get("evolves_to");
        if (evolvesTo != null && !evolvesTo.isEmpty()) {
            for (int i = 0; i < evolvesTo.size(); i++) {
                Map<String, Object> evo = evolvesTo.get(i);
                List<String> evolutions = nameOfEvolutions(evo);
                result.addAll(evolutions);
            }
        }
        return result;
    }
}



