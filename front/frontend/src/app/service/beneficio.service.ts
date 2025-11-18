import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Beneficio {
  id: number;
  nome: string;
  descricao: string
  valor: number;
  ativo: boolean
}

export interface TransferRequest{
  fromId:number;
  toId:number;
  amount:number
}

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {
  private apiUrl = 'http://localhost:8090/beneficio';

  constructor(private http: HttpClient) { }

  listarBeneficios(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(`${this.apiUrl}/list`);
  }
  transfer(trans: TransferRequest): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/transfer`, trans);
  }
}
