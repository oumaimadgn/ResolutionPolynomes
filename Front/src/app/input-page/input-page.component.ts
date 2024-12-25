import { Component } from '@angular/core';
import { PolynomialService, PolynomialRequest, PolynomialResult } from '../services/polynomial.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-input-page',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './input-page.component.html',
  styleUrls: ['./input-page.component.css']
})
export class InputPageComponent {
  inputValue: string = '';
  domain: string = 'real';
  selectedAnalyticalMethod: string = '';
  showAnalyticalOptions: boolean = false;
  textAreaValue: string = '';

  constructor(private polynomialService: PolynomialService) {}

  selectMethod(method: string) {
    this.showAnalyticalOptions = method === 'analytical';
  }

  // Method for standard resolution
  onResolutionClick() {
    if (!this.inputValue) {
      this.textAreaValue = 'Veuillez entrer un polynôme valide.';
      return;
    }

    const method = this.showAnalyticalOptions ? this.selectedAnalyticalMethod : 'numerical';

    const request: PolynomialRequest = {
      polynomial: this.inputValue,
      domain: this.domain,
      method: method
    };

    this.polynomialService.evaluatePolynomial(request).subscribe({
      next: (response) => {
        const roots = response.roots.join(', ');
        this.textAreaValue = `Racines: ${roots}\nForme Factorisée: ${response.factorizedForm}`;
      },
      error: (err) => {
        console.error('Erreur API:', err);
        this.textAreaValue = 'Une erreur est survenue lors du traitement.';
      }
    });
  }

  // Method for detailed resolution
  onDetailedResolutionClick() {
    if (!this.inputValue) {
      this.textAreaValue = 'Veuillez entrer un polynôme valide.';
      return;
    }

    const request: PolynomialRequest = {
      polynomial: this.inputValue,
      domain: this.domain,
      method: 'detailed' // Explicitly set method to "detailed"
    };

    this.polynomialService.evaluatePolynomial(request).subscribe({
      next: (response) => {
        const roots = response.roots.join(', ');
        this.textAreaValue = `Racines: ${roots}\nForme Factorisée: ${response.factorizedForm}`;
        if (response.detailedSteps) {
          this.textAreaValue += `\nÉtapes détaillées: ${response.detailedSteps}`;
        }
      },
      error: (err) => {
        console.error('Erreur API:', err);
        this.textAreaValue = 'Une erreur est survenue lors du traitement.';
      }
    });
  }
}
