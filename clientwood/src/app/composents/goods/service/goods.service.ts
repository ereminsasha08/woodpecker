import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Good } from '../model/goods-model';
import {environment} from "../../../../environments/environment.development";
@Injectable({
  providedIn: 'root'
})
export class GoodsService {

  private apiUrl = environment.backendUrl + '/goods';

  constructor(private http: HttpClient) { }

  getAllGoods(): Observable<Good[]> {
    return this.http.get<Good[]>(this.apiUrl);
  }

  getGoodById(id: number): Observable<Good> {
    return this.http.get<Good>(`${this.apiUrl}/${id}`);
  }

  createGood(good: Good): Observable<Good> {
    return this.http.post<Good>(`${this.apiUrl}/save`, good);
  }

  updateGood(id: number, good: Good): Observable<Good> {
    return this.http.put<Good>(`${this.apiUrl}/save/${id}`, good);
  }

  deleteGood(id: number ): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

}
