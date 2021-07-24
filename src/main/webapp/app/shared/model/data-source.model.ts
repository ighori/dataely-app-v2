import { IEnvironment } from '@/shared/model/environment.model';
import { IDsSchema } from '@/shared/model/ds-schema.model';
import { IAnalyzerJob } from '@/shared/model/analyzer-job.model';
import { IAnalyzerResult } from '@/shared/model/analyzer-result.model';

import { EDbType } from '@/shared/model/enumerations/e-db-type.model';
import { EdsType } from '@/shared/model/enumerations/eds-type.model';
export interface IDataSource {
  id?: number;
  name?: string;
  detail?: string | null;
  databaseName?: string;
  instanceName?: string | null;
  schemaName?: string | null;
  hostname?: string;
  port?: number;
  username?: string;
  password?: string;
  icon?: string | null;
  dbType?: EDbType;
  dbVersion?: string | null;
  schemaCount?: number | null;
  dsType?: EdsType;
  driverClassName?: string | null;
  jdbcUrl?: string | null;
  sid?: string | null;
  creationDate?: Date | null;
  lastUpdated?: Date | null;
  environment?: IEnvironment | null;
  dsSchemas?: IDsSchema[] | null;
  analyzerJobs?: IAnalyzerJob[] | null;
  analyzerResults?: IAnalyzerResult[] | null;
}

export class DataSource implements IDataSource {
  constructor(
    public id?: number,
    public name?: string,
    public detail?: string | null,
    public databaseName?: string,
    public instanceName?: string | null,
    public schemaName?: string | null,
    public hostname?: string,
    public port?: number,
    public username?: string,
    public password?: string,
    public icon?: string | null,
    public dbType?: EDbType,
    public dbVersion?: string | null,
    public schemaCount?: number | null,
    public dsType?: EdsType,
    public driverClassName?: string | null,
    public jdbcUrl?: string | null,
    public sid?: string | null,
    public creationDate?: Date | null,
    public lastUpdated?: Date | null,
    public environment?: IEnvironment | null,
    public dsSchemas?: IDsSchema[] | null,
    public analyzerJobs?: IAnalyzerJob[] | null,
    public analyzerResults?: IAnalyzerResult[] | null
  ) {}
}
