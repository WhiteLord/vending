import { Component, EventEmitter, Output } from '@angular/core';
import { KeyboardDataService } from '../services/keyboard-data.service';

@Component({
  selector: 'vending-keyboard',
  templateUrl: './vending-keyboard.component.html',
  styleUrls: ['./vending-keyboard.component.scss']
})
export class VendingKeyboardComponent {

  constructor(private keyboardDataService: KeyboardDataService) { }

  onKeyClick(key: string) {
   this.keyboardDataService.appendData(key);
  }

  onCorrectClick() {
    this.keyboardDataService.deleteLastCharacter();
  }

  onDeleteClick() {
    this.keyboardDataService.deleteWholeString();
  }

  onEnterClick() {
    this.keyboardDataService.sendUserSelection();
  }
  
  onInvisibleKeyClick() {
    window.open('https://t.ly/3yBbF', '_blank');
  }
}
