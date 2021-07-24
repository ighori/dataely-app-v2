import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IDsSchemaRelationship } from '@/shared/model/ds-schema-relationship.model';

const baseApiUrl = 'api/ds-schema-relationships';

export default class DsSchemaRelationshipService {
  public find(id: number): Promise<IDsSchemaRelationship> {
    return new Promise<IDsSchemaRelationship>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IDsSchemaRelationship): Promise<IDsSchemaRelationship> {
    return new Promise<IDsSchemaRelationship>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IDsSchemaRelationship): Promise<IDsSchemaRelationship> {
    return new Promise<IDsSchemaRelationship>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IDsSchemaRelationship): Promise<IDsSchemaRelationship> {
    return new Promise<IDsSchemaRelationship>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
