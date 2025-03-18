import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PokemonTypeComponent } from './pokemon-type/pokemon-type.component';
import { EvolutionChainComponent } from './evolution-chain/evolution-chain.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'pokemon-type/:type', component: PokemonTypeComponent },
  { path: 'evolution-chain/:name', component: EvolutionChainComponent },
];

@NgModule({
  declarations: [HomeComponent, PokemonTypeComponent, EvolutionChainComponent],
  imports: [CommonModule, FormsModule, RouterModule.forChild(routes)],
})
export class PagesModule {}
