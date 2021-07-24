import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IAnalyzerAdHocRecognizers } from '@/shared/model/analyzer-ad-hoc-recognizers.model';

const baseApiUrl = 'api/analyzer-ad-hoc-recognizers';

export default class AnalyzerAdHocRecognizersService {
  public find(id: number): Promise<IAnalyzerAdHocRecognizers> {
    return new Promise<IAnalyzerAdHocRecognizers>((resolve, reject) => {
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

  public create(entity: IAnalyzerAdHocRecognizers): Promise<IAnalyzerAdHocRecognizers> {
    return new Promise<IAnalyzerAdHocRecognizers>((resolve, reject) => {
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

  public update(entity: IAnalyzerAdHocRecognizers): Promise<IAnalyzerAdHocRecognizers> {
    return new Promise<IAnalyzerAdHocRecognizers>((resolve, reject) => {
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

  public partialUpdate(entity: IAnalyzerAdHocRecognizers): Promise<IAnalyzerAdHocRecognizers> {
    return new Promise<IAnalyzerAdHocRecognizers>((resolve, reject) => {
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
