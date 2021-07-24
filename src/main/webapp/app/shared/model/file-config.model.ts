import { IFileInfo } from '@/shared/model/file-info.model';

export interface IFileConfig {
  id?: number;
  name?: string;
  detail?: string | null;
  columnNameLineNumber?: number | null;
  encoding?: string | null;
  separatorChar?: string | null;
  quoteChar?: string | null;
  escapeChar?: string | null;
  fixedValueWidth?: number | null;
  skipEmptyLines?: boolean | null;
  skipEmptyColumns?: boolean | null;
  failOnInconsistentLineWidth?: boolean | null;
  skipEbcdicHeader?: boolean | null;
  eolPresent?: boolean | null;
  fileType?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  fileInfos?: IFileInfo[] | null;
}

export class FileConfig implements IFileConfig {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public columnNameLineNumber?: number | null,
    public encoding?: string | null,
    public separatorChar?: string | null,
    public quoteChar?: string | null,
    public escapeChar?: string | null,
    public fixedValueWidth?: number | null,
    public skipEmptyLines?: boolean | null,
    public skipEmptyColumns?: boolean | null,
    public failOnInconsistentLineWidth?: boolean | null,
    public skipEbcdicHeader?: boolean | null,
    public eolPresent?: boolean | null,
    public fileType?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public fileInfos?: IFileInfo[] | null
  ) {
    this.skipEmptyLines = this.skipEmptyLines ?? false;
    this.skipEmptyColumns = this.skipEmptyColumns ?? false;
    this.failOnInconsistentLineWidth = this.failOnInconsistentLineWidth ?? false;
    this.skipEbcdicHeader = this.skipEbcdicHeader ?? false;
    this.eolPresent = this.eolPresent ?? false;
  }
}
