export interface IAnalyzerEntities {
  id?: number;
  name?: string;
  detail?: string | null;
  entityName?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
}

export class AnalyzerEntities implements IAnalyzerEntities {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public entityName?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null
  ) {}
}
