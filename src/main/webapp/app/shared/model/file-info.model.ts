import { IFileSource } from '@/shared/model/file-source.model';
import { IFileConfig } from '@/shared/model/file-config.model';

import { EdsType } from '@/shared/model/enumerations/eds-type.model';
export interface IFileInfo {
  id?: number;
  name?: string;
  detail?: string | null;
  fileType?: EdsType;
  filePath?: string;
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
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  fileSource?: IFileSource | null;
  fileConfig?: IFileConfig | null;
}

export class FileInfo implements IFileInfo {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public fileType?: EdsType,
    public filePath?: string,
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
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public fileSource?: IFileSource | null,
    public fileConfig?: IFileConfig | null
  ) {
    this.skipEmptyLines = this.skipEmptyLines ?? false;
    this.skipEmptyColumns = this.skipEmptyColumns ?? false;
    this.failOnInconsistentLineWidth = this.failOnInconsistentLineWidth ?? false;
    this.skipEbcdicHeader = this.skipEbcdicHeader ?? false;
    this.eolPresent = this.eolPresent ?? false;
  }
}
