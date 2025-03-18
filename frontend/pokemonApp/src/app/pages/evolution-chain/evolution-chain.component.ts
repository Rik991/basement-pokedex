import { Component } from '@angular/core';
import { iEvolutionChain } from '../../interfaces/i-evolution-chain';
import { PokemonService } from '../../services/pokemon.service';
import { ActivatedRoute } from '@angular/router';
import { iPokemon } from '../../interfaces/i-pokemon';

@Component({
  selector: 'app-evolution-chain',
  templateUrl: './evolution-chain.component.html',
  styleUrl: './evolution-chain.component.scss',
})
export class EvolutionChainComponent {
  species: string = ''; //o name
  evolutionChain: iPokemon[] = [];
  errorMessage: string = '';

  constructor(
    private pokemonSvc: PokemonService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.species = params['name'];
      this.getEvolutionChain(this.species);
    });
  }

  getEvolutionChain(species: string) {
    this.pokemonSvc.getEvolutionChain(species).subscribe({
      next: (evolutionChain) => {
        this.evolutionChain = evolutionChain;
        this.errorMessage = '';
      },
      error: (error) => {
        console.error('Nessuna evoluzione trovata', error);
        this.errorMessage = 'Nessuna evoluzione trovata';
        this.evolutionChain = [];
      },
    });
  }
}
