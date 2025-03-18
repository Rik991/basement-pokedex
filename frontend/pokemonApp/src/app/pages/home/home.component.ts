import { Component } from '@angular/core';
import { PokemonService } from '../../services/pokemon.service';
import { iPokemon } from '../../interfaces/i-pokemon';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  pokemon: iPokemon | null = null;
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
}
