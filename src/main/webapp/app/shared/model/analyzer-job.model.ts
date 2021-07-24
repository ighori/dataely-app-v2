import { IEnvironment } from '@/shared/model/environment.model';
import { IDataSource } from '@/shared/model/data-source.model';
import { IFileSource } from '@/shared/model/file-source.model';
import { IUser } from '@/shared/model/user.model';

import { EJobStatus } from '@/shared/model/enumerations/e-job-status.model';
export interface IAnalyzerJob {
  id?: number;
  name?: string;
  detail?: string | null;
  endTime?: Date | null;
  startTime?: Date | null;
  status?: EJobStatus | null;
  previousRunTime?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  environment?: IEnvironment | null;
  dataSource?: IDataSource | null;
  fileSource?: IFileSource | null;
  user?: IUser;
}

export class AnalyzerJob implements IAnalyzerJob {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public endTime?: Date | null,
    public startTime?: Date | null,
    public status?: EJobStatus | null,
    public previousRunTime?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public environment?: IEnvironment | null,
    public dataSource?: IDataSource | null,
    public fileSource?: IFileSource | null,
    public user?: IUser
  ) {}
}
