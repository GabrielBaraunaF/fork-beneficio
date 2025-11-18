import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BeneficioService, Beneficio, TransferRequest } from './service/beneficio.service';

@Component({
  selector: 'app-root',
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'frontend';
  beneficios: Beneficio[] = [];

  origem: Beneficio | null = null;
  destinoId: number | null = null;
  valor: string = '';
  showSaldo: Beneficio | null = null;
  menuAberto: number | null = null;
  

  toastMessage: string = '';
  toastType: 'success' | 'error' = 'success';
  showToast: boolean = false;

  constructor(private beneficioService: BeneficioService) { }

  ngOnInit(): void {
    this.carregarBeneficios();
  }

  carregarBeneficios(): void {
    this.beneficioService.listarBeneficios().subscribe({
      next: (data) => {
        this.beneficios = data;
      },
      error: (error) => {
        console.error('Erro ao carregar benefícios:', error);
      }
    });
  }

  toggleMenu(beneficioId: number): void {
    this.menuAberto = this.menuAberto === beneficioId ? null : beneficioId;
  }

  handleTransferir(beneficio: Beneficio): void {
    this.origem = beneficio;
    this.menuAberto = null;
    this.showSaldo = null;
  }

  handleVerSaldo(beneficio: Beneficio): void {
    this.showSaldo = beneficio;
    this.menuAberto = null;
  }

  get destino(): Beneficio | null {
    if (!this.destinoId) return null;
    return this.beneficios.find(b => b.id === this.destinoId) || null;
  }

  get beneficiosFiltrados(): Beneficio[] {
    if (!this.origem) return this.beneficios;
    return this.beneficios.filter(b => b.id !== this.origem!.id);
  }

  confirmarTransferencia(): void {
    if (!this.origem || !this.destinoId || !this.valor || parseFloat(this.valor) <= 0) {
      this.mostrarToast('prenncha todos os campos corretamente', 'error');
      return;
    }


    const transferRequest: TransferRequest = {
      fromId: this.origem.id,
      toId: this.destinoId!,
      amount: parseFloat(this.valor)
    };

    this.beneficioService.transfer(transferRequest).subscribe({
      next: () => {
        this.carregarBeneficios();
        this.limparCampos();
      },
      error: (error) => {
        this.mostrarToast(error.error.message, 'error');     
      }
    });    
  
  }

  limparCampos(): void {
    this.origem = null;
    this.destinoId = null;
    this.valor = '';
    
  }

  mostrarToast(message: string, type: 'success' | 'error'): void {
    this.toastMessage = message;
    this.toastType = type;
    this.showToast = true;
    
    setTimeout(() => {
      this.showToast = false;
    }, 3000);
  }
}
