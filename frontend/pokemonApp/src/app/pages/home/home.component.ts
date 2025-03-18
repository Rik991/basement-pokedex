import { Component } from '@angular/core';
import { PokemonService } from '../../services/pokemon.service';
import { iPokemon } from '../../interfaces/i-pokemon';
import { iPokemonDetails } from '../../interfaces/i-pokemon-details';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  pokemon: iPokemon | null = null;
  pokemonTwo: iPokemonDetails | null = null;
  pokemonName: string = '';
  errorMessage: string = '';

  constructor(private pokemonSvc: PokemonService) {}

  // ngOnInit() {
  //   this.searchPokemon();
  // }

  searchPokemon(): void {
    // console.log(this.pokemonName);
    this.pokemonSvc.getPokemonByName(this.pokemonName.trim()).subscribe({
      next: (p) => {
        this.pokemon = p;
      },
      error: (error) => {
        console.error('Pokemon non trovato');
        this.errorMessage = 'Pokemon non trovato, inserisci un nome valido ';
      },
    });
  }

  //task 2, stesso identico metodo con diversa chiamata al back che mi torna il pokemon cercato con i dettagli (pokemonTwo)
  searchPokemonTaskTwo(): void {
    this.pokemonSvc.getPokemonWithDetails(this.pokemonName.trim()).subscribe({
      next: (p) => {
        this.errorMessage = '';
        this.pokemonTwo = p;
      },
      error: (error) => {
        console.error('Pokemon non trovato');
        this.errorMessage = 'Pokemon non trovato, inserisci un nome valido ';
        this.pokemonTwo = null;
      },
    });
  }
}
