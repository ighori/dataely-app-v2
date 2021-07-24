import { IContact } from '@/shared/model/contact.model';
import { IApplication } from '@/shared/model/application.model';
import { IDataSource } from '@/shared/model/data-source.model';
import { IFileSource } from '@/shared/model/file-source.model';
import { IAnalyzerJob } from '@/shared/model/analyzer-job.model';

import { EEnvType } from '@/shared/model/enumerations/e-env-type.model';
import { EEnvPurpose } from '@/shared/model/enumerations/e-env-purpose.model';
export interface IEnvironment {
  id?: number;
  name?: string;
  detail?: string | null;
  type?: EEnvType;
  purpose?: EEnvPurpose;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  contact?: IContact | null;
  application?: IApplication | null;
  dataSources?: IDataSource[] | null;
  fileSources?: IFileSource[] | null;
  analyzerJobs?: IAnalyzerJob[] | null;
}

export class Environment implements IEnvironment {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public type?: EEnvType,
    public purpose?: EEnvPurpose,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public contact?: IContact | null,
    public application?: IApplication | null,
    public dataSources?: IDataSource[] | null,
    public fileSources?: IFileSource[] | null,
    public analyzerJobs?: IAnalyzerJob[] | null
  ) {}
}
