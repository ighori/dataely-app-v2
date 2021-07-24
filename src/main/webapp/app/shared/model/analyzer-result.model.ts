import { IDataSource } from '@/shared/model/data-source.model';
import { IFileSource } from '@/shared/model/file-source.model';

import { EAnalyzerObjectType } from '@/shared/model/enumerations/e-analyzer-object-type.model';
export interface IAnalyzerResult {
  id?: number;
  name?: string;
  detail?: string | null;
  objectId?: number | null;
  objectType?: EAnalyzerObjectType | null;
  fieldId?: number | null;
  fieldType?: string | null;
  entityType?: string | null;
  start?: number | null;
  end?: number | null;
  score?: number | null;
  recognizer?: string | null;
  patternName?: string | null;
  patternExpr?: string | null;
  originalScore?: string | null;
  textualExplanation?: string | null;
  supportiveContextWord?: string | null;
  validationResult?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  dataSource?: IDataSource | null;
  fileSource?: IFileSource | null;
}

export class AnalyzerResult implements IAnalyzerResult {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public objectId?: number | null,
    public objectType?: EAnalyzerObjectType | null,
    public fieldId?: number | null,
    public fieldType?: string | null,
    public entityType?: string | null,
    public start?: number | null,
    public end?: number | null,
    public score?: number | null,
    public recognizer?: string | null,
    public patternName?: string | null,
    public patternExpr?: string | null,
    public originalScore?: string | null,
    public textualExplanation?: string | null,
    public supportiveContextWord?: string | null,
    public validationResult?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public dataSource?: IDataSource | null,
    public fileSource?: IFileSource | null
  ) {}
}
