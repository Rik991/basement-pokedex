import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { iPokemon } from '../interfaces/i-pokemon';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class PokemonService {
  private pokemonUrl = environment.pokemonUrl;

  constructor(private http: HttpClient) {}

  getPokemonByName(name: String): Observable<iPokemon> {
    return this.http.get<iPokemon>(`${this.pokemonUrl}/${name}`);
  }
}
