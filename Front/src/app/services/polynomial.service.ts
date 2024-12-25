import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PolynomialRequest {
  polynomial: string;
  domain: string;
  method: string;
}

export interface PolynomialResult {
  roots: string[];
  factorizedForm: string;
}

@Injectable({
  providedIn: 'root'
})
export class PolynomialService {
  private apiUrl =  'http://localhost:8080/api/v1/polynomial/evaluate';

  constructor(private http: HttpClient) {}

  evaluatePolynomial(request: PolynomialRequest): Observable<PolynomialResult> {
    return this.http.post<PolynomialResult>(this.apiUrl, request);
  }
}
