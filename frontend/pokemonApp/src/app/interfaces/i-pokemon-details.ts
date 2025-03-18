import { iPokemon } from './i-pokemon';
import { iStat } from './i-stat';

export interface iPokemonDetails extends iPokemon {
  types: string[];
  stats: iStat[];
}
