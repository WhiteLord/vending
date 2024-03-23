import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MoneyService } from './money.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class KeyboardDataService {
  private dataSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');

  constructor(private httpClient: HttpClient, private moneyService: MoneyService) { }

  setData(data: string) {
    this.dataSubject.next(data);
  }

  getData() {
    return this.dataSubject.asObservable();
  }

  deleteLastCharacter() {
    let currentData = this.dataSubject.getValue();
    currentData = currentData.slice(0, -1);
    this.dataSubject.next(currentData);
  }

  deleteWholeString() {
    this.dataSubject.next('');
  }

  appendData(key: string) {
    let currentData = this.dataSubject.getValue();
    currentData += key;
    this.dataSubject.next(currentData);
  }

  sendUserSelection() {
    console.log(this.dataSubject.getValue());
    const endpoint = environment.apiEndpoint;
    this.httpClient.post(endpoint + '/vending/purchase', {item: this.dataSubject.getValue()}).subscribe(result => {
      console.log(result);
      this.moneyService.shouldRefetchSessionCoins.next(true);
    });
  }
}