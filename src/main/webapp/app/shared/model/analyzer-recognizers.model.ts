export interface IAnalyzerRecognizers {
  id?: number;
  name?: string;
  detail?: string | null;
  recognizerName?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
}

export class AnalyzerRecognizers implements IAnalyzerRecognizers {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public recognizerName?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null
  ) {}
}
