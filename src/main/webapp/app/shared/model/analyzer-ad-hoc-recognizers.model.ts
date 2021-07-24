export interface IAnalyzerAdHocRecognizers {
  id?: number;
  name?: string;
  detail?: string | null;
  recognizerName?: string | null;
  supportedLang?: string | null;
  patternName?: string | null;
  regex?: string | null;
  score?: number | null;
  context?: string | null;
  denyList?: string | null;
  supportedEntity?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
}

export class AnalyzerAdHocRecognizers implements IAnalyzerAdHocRecognizers {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public recognizerName?: string | null,
    public supportedLang?: string | null,
    public patternName?: string | null,
    public regex?: string | null,
    public score?: number | null,
    public context?: string | null,
    public denyList?: string | null,
    public supportedEntity?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null
  ) {}
}
