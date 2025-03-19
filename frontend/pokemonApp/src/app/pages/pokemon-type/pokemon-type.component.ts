import { Component } from '@angular/core';
import { IPokemonType } from '../../interfaces/i-pokemon-type';
import { PokemonService } from '../../services/pokemon.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-pokemon-type',
  templateUrl: './pokemon-type.component.html',
  styleUrl: './pokemon-type.component.scss',
})
export class PokemonTypeComponent {
  type: string = '';
  pokemonTypeList: IPokemonType | null = null;
  errorMessage: string = '';
  toggleStates: boolean[] = [];

  constructor(
    private pokemonSvc: PokemonService,
    private route: ActivatedRoute
  ) {}

  //nell'ngonInit recupero il tipo scelto dal parametro url e lo do alla funzione dedicata
  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.type = params['type'];
      this.getPokemonByType(this.type);
    });
  }

  //task 2, metodo per ricavare la lista dei pokemon di un determinato tipo, da backend ci tornano 10 item per tipo
  getPokemonByType(type: string): void {
    this.pokemonSvc.getPokemonByType(type).subscribe({
      next: (pokemonList) => {
        this.pokemonTypeList = pokemonList;
      },
      error: (error) => {
        console.error('Errore nel recupero dei Pokémon per tipo', error);
        this.errorMessage =
          'Errore nel recupero dei Pokémon per il tipo selezionato.';
      },
    });
  }

  toggleStats(index: number): void {
    this.toggleStates[index] = !this.toggleStates[index];
  }
}
