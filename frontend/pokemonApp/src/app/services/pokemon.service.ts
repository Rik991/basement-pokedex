import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { iPokemon } from '../interfaces/i-pokemon';
import { environment } from '../../environments/environment.development';
import { iPokemonDetails } from '../interfaces/i-pokemon-details';
import { IPokemonType } from '../interfaces/i-pokemon-type';

@Injectable({
  providedIn: 'root',
})
export class PokemonService {
  private pokemonUrl = environment.pokemonUrl;
  private pokemonDetailsUrl = environment.pokemonDetailsUrl;
  private pokemonTypeUrl = environment.pokemonTypeUrl;

  constructor(private http: HttpClient) {}

  // task 1, chiamata get al backend che mi restituisce nome e immagine del pokemon
  getPokemonByName(name: String): Observable<iPokemon> {
    return this.http.get<iPokemon>(`${this.pokemonUrl}/${name}`);
  }

  //task 2, stessa chiamata ma restituisce anche i dettagli richiesti
  getPokemonWithDetails(name: string): Observable<iPokemonDetails> {
    return this.http.get<iPokemonDetails>(`${this.pokemonDetailsUrl}/${name}`);
  }
  //chiamata per la lista dei pokemon di un determinato tipo
  getPokemonByType(type: string): Observable<IPokemonType> {
    return this.http.get<IPokemonType>(`${this.pokemonTypeUrl}/${type}`);
  }
}
