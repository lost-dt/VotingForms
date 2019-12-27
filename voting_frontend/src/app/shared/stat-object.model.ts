import { StatData } from './stat-data.model';

export class StatObject {
  constructor(public name: string, public stats: StatData[]) {}
}
