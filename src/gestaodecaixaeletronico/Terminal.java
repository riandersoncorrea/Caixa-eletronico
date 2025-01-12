package gestaodecaixaeletronico;

import java.util.Scanner;

public class Terminal {
    private Caixa meuCaixa;
    private int modoAtual;
    

    public Terminal(CadastroContas bd){
        this.meuCaixa = new Caixa(this, bd);
    }
    
    public void iniciaOperacao(){
        int opcao;
        opcao = this.getOpcao();
        while (opcao != 4){
            switch (opcao) {
                case 1:
                    double saldo = this.meuCaixa.consultaSaldo(getInt("Número da Conta"), getInt("Senha"));
                    if(saldo != -1){
                        System.out.println("Saldo Atual: " + saldo);
                    } else {
                        System.out.println("Conta/Senha inválida");
                    }
                    break;

                case 2: 
                    boolean b = this.meuCaixa.efetuaSaque(getInt("Número da Conta"), (double)getInt("Valor"), getInt("Senha"));
                    if (b){
                        System.out.println("Retire o dinheiro");
                    } else {
                        System.out.println("Pedido de saque recusado");
                    }
                    break;
                
                case 3:
                    this.meuCaixa.recarrega();
                    break;
                case 5:
                    boolean depositaDinheiro = this.meuCaixa.efetuaDepositoDinheiro(getInt("Número da Conta"), (double)getInt("Valor"), getInt("Senha"));
                    if (depositaDinheiro) {
                        System.out.println("Depósito em dinheiro efetuado com sucesso!");
                    } else {
                        System.out.println("Pedido de depósito recusado");
                    }
                    break;
                case 6:
                    boolean depositaCheque = this.meuCaixa.efetuaDepositoCheque(getInt("Número da Conta"), (double)getInt("Valor"), getInt("Senha"));
                    if (depositaCheque) {
                        System.out.println("Depósito em cheque efetuado com sucesso!");
                    } else {
                        System.out.println("Pedido de depósito recusado");
                    }
                    break;
                case 7:
                    boolean transfere = this.meuCaixa.transferencia(getInt("Conta de Origem"), getInt("Conta de Destino"), (double)getInt("Valor"), getInt("Senha"));
                    if (transfere){
                        System.out.println("Transferencia realizada com sucesso!");
                    } else {
                        System.out.println("Pedido de transferencia recusado");
                    }
                    break;
                case 8:
                    this.meuCaixa.consultaExtrato(getInt("Numero da Conta"), getInt("Senha"));
                    break;
            }
            opcao = getOpcao();
        }

    }

    public void setModo(int modo){
        if (modo == 0 || modo == 1){
            this.modoAtual = modo;
        }
    }

    int getOpcao(){
        int opcao = 0;
        do {
            if (this.modoAtual == 1){
                opcao = getInt("Opção: \n\n" + "1 - Consulta Saldo\n" + "2 - Saque\n"  + "5 - Depósito em Dinheiro\n" + "6 - Depósito em Cheque\n" + "7 - Transferência\n" + "8 - Consultar Extrato\n" + "4 - Sair\n");
                if (opcao != 1 & opcao != 2 & opcao != 4 & opcao != 5 & opcao != 6 & opcao != 7 & opcao != 8){
                    opcao = 0;
                }
            } else {
                opcao = getInt("Opção: \n\n" + "3 - Recarrega\n" + "4 - Sair\n");
                if (opcao != 3 & opcao != 4){
                    opcao = 0;
                }
            }
        } while (opcao == 0);
        return opcao;
    }

    private int getInt(String string){

        @SuppressWarnings("resource")
        Scanner r = new Scanner(System.in);
        System.out.println("\nEntre com " + string);
        if (r.hasNextInt()){
            return r.nextInt();
        }

        @SuppressWarnings("unused")
        String st = r.next();
        System.out.println("Erro na Leitura de Dados ");
        return 0;
        
    }
}
