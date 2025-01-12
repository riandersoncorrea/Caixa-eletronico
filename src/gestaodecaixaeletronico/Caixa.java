package gestaodecaixaeletronico;

import gestaodecontas.Conta;
import gestaodecontas.HistoricoDeLancamentos;

public class Caixa {
    private Terminal meuTerminal;
    private CadastroContas bdContas;
    private double saldo;
    

    public Caixa(Terminal terminal, CadastroContas bd) {
        this.meuTerminal = terminal;
        this.bdContas = bd;
    }

    public double consultaSaldo(int numeroDaConta,int senha){
        Conta conta;
        conta =  this.bdContas.buscaConta(numeroDaConta);

        if (conta != null){
            return conta.verificaSaldo(senha);
        } else {
            return -1;
        }
    }

    public boolean efetuaSaque(int numeroDaConta, double valor, int senha){
        if (valor < 0 || (valor%50) != 0 || valor > 500 || valor > this.saldo){
            return false;
        }
        Conta conta = bdContas.buscaConta(numeroDaConta);
        if(conta == null || !conta.debitaValor(valor, senha, "Saque Automático")){
            return false;
        }
        this.liberaCedulas((int)(valor/50));
        this.saldo -= valor;
        if (this.saldo < 500){
            this.meuTerminal.setModo(0);
        }
        return true;
    }

    public void recarrega(){
        this.saldo = 2000;
        this.meuTerminal.setModo(1);
    }

    public boolean efetuaDepositoDinheiro (int numeroDaConta, double valor, int senha){
        Conta conta = bdContas.buscaConta(numeroDaConta);

        if (conta == null){
            return false;
        }
        return conta.creditaValor(valor, "Depósito em Dinheiro");
    }

    public boolean efetuaDepositoCheque (int numeroDaConta, double valor, int senha){
        Conta conta = bdContas.buscaConta(numeroDaConta);

        if (conta == null){
            return false;
        }
        return conta.creditaValor(valor, "Depósito em Cheque");
    }

    public boolean transferencia (int contaOrigem, int contaDestino, double valor, int senha){
        Conta contaOri = bdContas.buscaConta(contaOrigem);
        Conta contaDes = bdContas.buscaConta(contaDestino);
        if (contaOri == null || contaDes == null) {
            return false;
        }

        contaOri.debitaValor(valor, senha, "Transferencia");
        contaDes.creditaValor(valor, "Transferencia");
        return true;
    }

    public String consultaExtrato(int numeroDaConta, int senha) {
        Conta conta = bdContas.buscaConta(numeroDaConta);
        if (conta == null) {
            return "Conta não encontrada.";
        }
        HistoricoDeLancamentos historico = conta.getHistorico();
        String extrato = historico.geraHistoricoDeLancamentos();
        if (extrato == null) {
            return "Nenhum histórico encontrado.";
        }
        return extrato;
    }

    private void liberaCedulas (int quantidade){
        while (quantidade -- > 0){
            System.out.println("===/ R$ 50,00 /===");
        }
    }

    
}
