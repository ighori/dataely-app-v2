import { IEnvironment } from '@/shared/model/environment.model';
import { IFileInfo } from '@/shared/model/file-info.model';
import { IAnalyzerJob } from '@/shared/model/analyzer-job.model';
import { IAnalyzerResult } from '@/shared/model/analyzer-result.model';

import { EdsType } from '@/shared/model/enumerations/eds-type.model';
export interface IFileSource {
  id?: number;
  name?: string;
  detail?: string | null;
  hostname?: string;
  port?: number;
  username?: string;
  password?: string;
  icon?: string | null;
  connectionType?: string | null;
  type?: EdsType | null;
  region?: string | null;
  ignoreDottedFiles?: boolean | null;
  recurse?: boolean | null;
  pathFilterRegex?: string | null;
  remotePath?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  environment?: IEnvironment | null;
  fileInfos?: IFileInfo[] | null;
  analyzerJobs?: IAnalyzerJob[] | null;
  analyzerResults?: IAnalyzerResult[] | null;
}

export class FileSource implements IFileSource {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public hostname?: string,
    public port?: number,
    public username?: string,
    public password?: string,
    public icon?: string | null,
    public connectionType?: string | null,
    public type?: EdsType | null,
    public region?: string | null,
    public ignoreDottedFiles?: boolean | null,
    public recurse?: boolean | null,
    public pathFilterRegex?: string | null,
    public remotePath?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public environment?: IEnvironment | null,
    public fileInfos?: IFileInfo[] | null,
    public analyzerJobs?: IAnalyzerJob[] | null,
    public analyzerResults?: IAnalyzerResult[] | null
  ) {
    this.ignoreDottedFiles = this.ignoreDottedFiles ?? false;
    this.recurse = this.recurse ?? false;
  }
}
